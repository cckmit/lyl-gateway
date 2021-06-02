package com.lyl.gateway.springboot.starter.sync.data.websocket;

import com.lyl.gateway.plugin.base.cache.CommonPluginDataSubscriber;
import com.lyl.gateway.plugin.base.handler.PluginDataHandler;
import com.lyl.gateway.sync.data.api.AuthDataSubscriber;
import com.lyl.gateway.sync.data.api.MetaDataSubscriber;
import com.lyl.gateway.sync.data.api.PluginDataSubscriber;
import com.lyl.gateway.sync.data.api.SyncDataService;
import com.lyl.gateway.sync.data.websocket.WebsocketSyncDataService;
import com.lyl.gateway.sync.data.websocket.config.WebsocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName WebsocketSyncDataConfiguration
 * @Description
 * @Author lyl
 * @Date 2021/5/31 17:32
 **/
@Slf4j
@Configuration
@ConditionalOnClass(WebsocketSyncDataService.class)
@ConditionalOnProperty(prefix = "lyl.sync.websocket", name = "urls")
public class WebsocketSyncDataConfiguration {

    @Bean
    public SyncDataService websocketSyncDataService(final ObjectProvider<WebsocketConfig> websocketConfig,
                                                    final ObjectProvider<PluginDataSubscriber> pluginSubscriber,
                                                    final ObjectProvider<List<MetaDataSubscriber>> metaSubscribers,
                                                    final ObjectProvider<List<AuthDataSubscriber>> authSubscribers){
        log.info("you use websocket sync shenyu data.......");
        return new WebsocketSyncDataService(websocketConfig.getIfAvailable(WebsocketConfig::new), pluginSubscriber.getIfAvailable(),
                metaSubscribers.getIfAvailable(Collections::emptyList), authSubscribers.getIfAvailable(Collections::emptyList));
    }

    @Bean
    @ConfigurationProperties(prefix = "lyl.sync.websocket")
    public WebsocketConfig websocketConfig(){
        return new WebsocketConfig();
    }
    @Bean
    public PluginDataSubscriber pluginDataSubscriber(final ObjectProvider<List<PluginDataHandler>> pluginDataHandlerList) {
        return new CommonPluginDataSubscriber(pluginDataHandlerList.getIfAvailable(Collections::emptyList));
    }


}

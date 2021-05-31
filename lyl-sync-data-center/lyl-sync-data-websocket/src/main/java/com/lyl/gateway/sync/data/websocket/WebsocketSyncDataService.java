package com.lyl.gateway.sync.data.websocket;

import com.lyl.gateway.common.LylThreadFactory;
import com.lyl.gateway.common.dto.PluginData;
import com.lyl.gateway.sync.data.api.AuthDataSubscriber;
import com.lyl.gateway.sync.data.api.MetaDataSubscriber;
import com.lyl.gateway.sync.data.api.PluginDataSubscriber;
import com.lyl.gateway.sync.data.api.SyncDataService;
import com.lyl.gateway.sync.data.websocket.client.LylWebsocketClient;
import com.lyl.gateway.sync.data.websocket.config.WebsocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName WebsocketSyncDataService
 * @Description
 * @Author Administrator
 * @Date 2021/5/30 23:07
 **/
@Slf4j
public class WebsocketSyncDataService implements SyncDataService, AutoCloseable {

    private final List<WebSocketClient> clients = new ArrayList<>();
    private final ScheduledThreadPoolExecutor executor;

    public WebsocketSyncDataService(final WebsocketConfig websocketConfig,
                                    final PluginDataSubscriber pluginDataSubscriber,
                                    final List<MetaDataSubscriber> metaDataSubscribers,
                                    final List<AuthDataSubscriber> authDataSubscribers){
        String[] urls = StringUtils.split(websocketConfig.getUrls(), ",");
        executor = new ScheduledThreadPoolExecutor(urls.length, LylThreadFactory.create("lyl-websocket-connect", true));
        for (String url : urls){
            try {
                clients.add(new LylWebsocketClient(new URI(url),
                        Objects.requireNonNull(pluginDataSubscriber),
                        metaDataSubscribers,
                        authDataSubscribers));
            } catch (URISyntaxException e){
                log.error("websocket url({}) is error", url, e);
            }
        }

        try {
            for (WebSocketClient client : clients){
                boolean success = client.connectBlocking(3000, TimeUnit.MILLISECONDS);
                if (success){
                    log.info("websocket connection is successful.....");
                } else {
                    log.error("websocket connection is error.....");
                }
                executor.scheduleAtFixedRate(() -> {
                   try {
                       if (client.isClosed()){
                           boolean reconnectSuccess = client.reconnectBlocking();
                           if (reconnectSuccess){
                               log.info("websocket reconnect server[{}] is successful.....", client.getURI().toString());
                           } else {
                               log.error("websocket reconnection server[{}] is error.....", client.getURI().toString());
                           }
                       }else {
                           client.sendPing();
                       }
                   } catch (InterruptedException e){
                       log.error("websocket connect is error :{}", e.getMessage());
                   }
                }, 10, 10, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            log.info("websocket connection...exception....", e);
        }

    }

    @Override
    public void close() throws Exception {

    }
}

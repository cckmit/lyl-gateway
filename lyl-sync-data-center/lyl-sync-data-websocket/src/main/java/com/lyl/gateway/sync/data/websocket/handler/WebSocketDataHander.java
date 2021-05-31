package com.lyl.gateway.sync.data.websocket.handler;

import com.lyl.gateway.common.enums.ConfigGroupEnum;
import com.lyl.gateway.sync.data.api.AuthDataSubscriber;
import com.lyl.gateway.sync.data.api.MetaDataSubscriber;
import com.lyl.gateway.sync.data.api.PluginDataSubscriber;

import java.util.EnumMap;
import java.util.List;

/**
 * @ClassName WebSocketDataHander
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:44
 **/
public class WebSocketDataHander {

    private static final EnumMap<ConfigGroupEnum, DataHandler> ENUM_MAP = new EnumMap<ConfigGroupEnum, DataHandler>(ConfigGroupEnum.class);

    public WebSocketDataHander(final PluginDataSubscriber pluginDataSubscriber,
                                final List<MetaDataSubscriber> metaDataSubscribers,
                                final List<AuthDataSubscriber> authDataSubscribers) {
        ENUM_MAP.put(ConfigGroupEnum.PLUGIN, new PluginDataHandler(pluginDataSubscriber));
        ENUM_MAP.put(ConfigGroupEnum.SELECTOR, new SelectorDataHandler(pluginDataSubscriber));
        ENUM_MAP.put(ConfigGroupEnum.RULE, new RuleDataHandler(pluginDataSubscriber));
        ENUM_MAP.put(ConfigGroupEnum.APP_AUTH, new AuthDataHandler(authDataSubscribers));
        ENUM_MAP.put(ConfigGroupEnum.META_DATA, new MetaDataHandler(metaDataSubscribers));
    }

    public void executor(final ConfigGroupEnum type, final String json, String eventType){
        ENUM_MAP.get(type).handle(json, eventType);
    }

}

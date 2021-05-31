package com.lyl.gateway.sync.data.websocket.client;

import com.lyl.gateway.common.dto.WebsocketData;
import com.lyl.gateway.common.enums.ConfigGroupEnum;
import com.lyl.gateway.common.enums.DataEventTypeEnum;
import com.lyl.gateway.common.utils.GsonUtils;
import com.lyl.gateway.sync.data.api.AuthDataSubscriber;
import com.lyl.gateway.sync.data.api.MetaDataSubscriber;
import com.lyl.gateway.sync.data.api.PluginDataSubscriber;
import com.lyl.gateway.sync.data.websocket.handler.WebSocketDataHander;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;

/**
 * @ClassName LylWebsocketClient
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:43
 **/
@Slf4j
public class LylWebsocketClient extends WebSocketClient {

    private volatile boolean alreadySync = Boolean.FALSE;
    private final WebSocketDataHander webSocketDataHander;

    public LylWebsocketClient(final URI serverUri,
                              final PluginDataSubscriber pluginDataSubscriber,
                              final List<MetaDataSubscriber> metaDataSubscribers,
                              final List<AuthDataSubscriber> authDataSubscribers){
        super(serverUri);
        this.webSocketDataHander = new WebSocketDataHander(pluginDataSubscriber, metaDataSubscribers, authDataSubscribers);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        if (!alreadySync){
            send(DataEventTypeEnum.MYSELF.name());
            alreadySync = true;
        }
    }

    @Override
    public void onMessage(final String result) {
        handlerResult(result);
    }

    @Override
    public void onClose(final int i, final String s, final boolean b) {
        this.close();
    }

    @Override
    public void onError(final Exception e) {
        this.close();
    }

    private void handlerResult(final String result){
        WebsocketData websocketData = GsonUtils.getInstance().fromJson(result, WebsocketData.class);
        ConfigGroupEnum groupEnum = ConfigGroupEnum.acquireByName(websocketData.getGroupType());
        String eventType = websocketData.getEventType();
        String json = GsonUtils.getInstance().toJson(websocketData.getData());
        webSocketDataHander.executor(groupEnum, json, eventType);
    }
}

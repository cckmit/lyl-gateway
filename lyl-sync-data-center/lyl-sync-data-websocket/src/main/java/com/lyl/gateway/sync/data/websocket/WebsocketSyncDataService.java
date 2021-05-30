package com.lyl.gateway.sync.data.websocket;

import com.lyl.gateway.sync.data.api.SyncDataService;
import com.lyl.gateway.sync.data.websocket.config.WebsocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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
                                    final Pluginda){

    }

    @Override
    public void close() throws Exception {

    }
}

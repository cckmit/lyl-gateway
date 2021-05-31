package com.lyl.gateway.sync.data.websocket.handler;

/**
 * @ClassName DataHandler
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:47
 **/
public interface DataHandler {

    void handle(String json, String eventType);

}

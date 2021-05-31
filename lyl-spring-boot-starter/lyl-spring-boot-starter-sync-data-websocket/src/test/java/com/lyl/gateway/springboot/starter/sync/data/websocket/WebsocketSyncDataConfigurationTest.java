package com.lyl.gateway.springboot.starter.sync.data.websocket;

import com.lyl.gateway.sync.data.api.PluginDataSubscriber;
import com.lyl.gateway.sync.data.websocket.WebsocketSyncDataService;
import com.lyl.gateway.sync.data.websocket.config.WebsocketConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @ClassName WebsocketSyncDataConfigurationTest
 * @Description
 * @Author Administrator
 * @Date 2021/5/31 21:19
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                WebsocketSyncDataConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "lyl.sync.websocket.urls = ws://localhost:9095/websocket"
        })
@EnableAutoConfiguration
@MockBean(PluginDataSubscriber.class)
public class WebsocketSyncDataConfigurationTest {

    @Autowired
    private WebsocketConfig websocketConfig;
    @Autowired
    private WebsocketSyncDataService websocketSyncDataService;

    @Test
    public void testWebSocketSyncDataService(){
        assertNotNull(websocketSyncDataService);
    }

    @Test
    public void testWebsocketConfig(){
        assertThat(websocketConfig.getUrls(), is("ws://localhost:9095/websocket"));
    }

}

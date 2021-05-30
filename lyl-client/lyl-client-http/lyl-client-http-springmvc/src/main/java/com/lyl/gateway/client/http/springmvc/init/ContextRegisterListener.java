package com.lyl.gateway.client.http.springmvc.init;

import com.lyl.gateway.client.core.disruptor.LylClientRegisterEventPunlisher;
import com.lyl.gateway.common.enums.RpcTypeEnum;
import com.lyl.gateway.common.utils.IpUtils;
import com.lyl.gateway.register.client.spi.LylClientRegisterRepository;
import com.lyl.gateway.register.common.config.LylRegisterCenterConfig;
import com.lyl.gateway.register.common.dto.MetaDataRegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName ContextRegisterListener
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 16:08
 **/
@Slf4j
public class ContextRegisterListener implements ApplicationListener<ContextRefreshedEvent> {

    private LylClientRegisterEventPunlisher punlisher = LylClientRegisterEventPunlisher.getInstance();
    private final AtomicBoolean registered = new AtomicBoolean(false);
    private String contextPath;
    private String appName;
    private String host;
    private Integer port;
    private final Boolean isFull;

    public ContextRegisterListener(final LylRegisterCenterConfig config, final LylClientRegisterRepository registerRepository){
        Properties props = config.getProps();
        this.isFull = Boolean.parseBoolean(props.getProperty("isFull", "false"));
        if (isFull){
            String registerType = config.getRegisterType();
            String serverLists = config.getServerLists();
            String contextPath = props.getProperty("contextPath");
            int port = Integer.parseInt(props.getProperty("port"));
            if (StringUtils.isBlank(contextPath) || StringUtils.isBlank(registerType) || StringUtils.isBlank(serverLists) || port <= 0){
                String errorMsg = "http register param must config the contextPath, registerType , serverLists and port must > 0";
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            this.appName = props.getProperty("appName");
            this.host = props.getProperty("host");
            this.port = port;
            this.contextPath = contextPath;
            punlisher.start(registerRepository);
        }
    }

    private MetaDataRegisterDTO buildMetaDataDTO(){
        String contextPath = this.contextPath;
        String appName = this.appName;
        Integer port = this.port;
        String path = contextPath + "/**";
        String configHost = this.host;
        String host = StringUtils.isBlank(configHost) ? IpUtils.getHost() : configHost;
        return MetaDataRegisterDTO.builder()
                .contextPath(contextPath)
                .host(host)
                .port(port)
                .appName(appName)
                .path(path)
                .rpcType(RpcTypeEnum.HTTP.getName())
                .enabled(true)
                .ruleName(path)
                .build();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!registered.compareAndSet(false, true)){
            return;
        }
        if (isFull){
            punlisher.publishEvent(buildMetaDataDTO());
        }
    }


}





























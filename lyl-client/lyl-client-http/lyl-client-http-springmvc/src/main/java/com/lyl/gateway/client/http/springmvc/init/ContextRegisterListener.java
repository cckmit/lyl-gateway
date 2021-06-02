package com.lyl.gateway.client.http.springmvc.init;

import com.lyl.gateway.client.http.springmvc.config.LylSpringMvcConfig;
import com.lyl.gateway.client.http.springmvc.dto.SpringMvcRegisterDTO;
import com.lyl.gateway.common.utils.IpUtils;
import com.lyl.gateway.common.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName ContextRegisterListener
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 16:08
 **/
@Slf4j
public class ContextRegisterListener implements ApplicationListener<ContextRefreshedEvent> {

    private volatile AtomicBoolean registered = new AtomicBoolean(false);
    private final String url;
    private final LylSpringMvcConfig lylSpringmvcConfig;


    public ContextRegisterListener(final LylSpringMvcConfig lylSpringmvcConfig){
        String contextPath = lylSpringmvcConfig.getContextPath();
        String adminUrl = lylSpringmvcConfig.getAdminUrl();
        Integer port = lylSpringmvcConfig.getPort();
        if (contextPath == null || "".equals(contextPath)
                || adminUrl == null || "".equals(adminUrl)
                || port == null) {
            log.error("spring mvc param must config  contextPath ,adminUrl and port ");
            throw new RuntimeException("spring mvc param must config  contextPath ,adminUrl and port");
        }
        this.lylSpringmvcConfig = lylSpringmvcConfig;
        url = adminUrl + "/soul-client/springmvc-register";
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!registered.compareAndSet(false, true)){
            return;
        }
        if (lylSpringmvcConfig.isFull()){
            post(buildJsonParams(lylSpringmvcConfig.getContextPath()));
        }
    }

    private void post(final String json) {
        try {
            String result = OkHttpUtils.getInstance().post(url, json);
            if (Objects.equals(result, "success")) {
                log.info("http context register success :{} " + json);
            } else {
                log.error("http context register error :{} " + json);
            }
        } catch (IOException e) {
            log.error("cannot register soul admin param :{}", url + ":" + json);
        }
    }

    private String buildJsonParams(final String contextPath) {
        String appName = lylSpringmvcConfig.getAppName();
        Integer port = lylSpringmvcConfig.getPort();
        String path = contextPath + "/**";
        String configHost = lylSpringmvcConfig.getHost();
        String host = ("".equals(configHost) || null == configHost) ? IpUtils.getHost() : configHost;
        SpringMvcRegisterDTO registerDTO = SpringMvcRegisterDTO.builder()
                .context(contextPath)
                .host(host)
                .port(port)
                .appName(appName)
                .path(path)
                .rpcType("http")
                .enabled(true)
                .ruleName(path)
                .build();
        return OkHttpUtils.getInstance().getGosn().toJson(registerDTO);
    }

}





























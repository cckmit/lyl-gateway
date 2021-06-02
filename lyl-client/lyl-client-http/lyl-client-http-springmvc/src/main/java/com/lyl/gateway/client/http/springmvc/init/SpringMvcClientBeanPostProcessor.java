package com.lyl.gateway.client.http.springmvc.init;

import com.lyl.gateway.client.http.springmvc.annotation.LylSpringMvcClient;
import com.lyl.gateway.client.http.springmvc.config.LylSpringMvcConfig;
import com.lyl.gateway.client.http.springmvc.dto.SpringMvcRegisterDTO;
import com.lyl.gateway.common.utils.IpUtils;
import com.lyl.gateway.common.utils.OkHttpUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SpringMvcClientBeanPostProcessor
 * @Description
 * @Author Administrator
 * @Date 2021/5/30 16:05
 **/
@Slf4j
public class SpringMvcClientBeanPostProcessor implements BeanPostProcessor {

    private final ThreadPoolExecutor executorService;
    private final String url;
    private final LylSpringMvcConfig lylSpringMvcConfig;

    public SpringMvcClientBeanPostProcessor(final LylSpringMvcConfig lylSpringMvcConfig) {

        String contextPath = lylSpringMvcConfig.getContextPath();
        String adminUrl = lylSpringMvcConfig.getAdminUrl();
        Integer port = lylSpringMvcConfig.getPort();
        if (contextPath == null || "".equals(contextPath)
                || adminUrl == null || "".equals(adminUrl)
                || port == null) {
            log.error("spring mvc param must config  contextPath ,adminUrl and port ");
            throw new RuntimeException("spring mvc param must config  contextPath ,adminUrl and port");
        }
        this.lylSpringMvcConfig = lylSpringMvcConfig;
        url = adminUrl + "/soul-client/springmvc-register";
        executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    }

    @Override
    public Object postProcessAfterInitialization(@NonNull final Object bean, @NonNull final String beanName) throws BeansException {
        if (lylSpringMvcConfig.isFull()) {
            return bean;
        }
        Controller controller = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
        RestController restController = AnnotationUtils.findAnnotation(bean.getClass(), RestController.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(bean.getClass(), RequestMapping.class);
        if (controller != null || restController != null || requestMapping != null) {
            String contextPath = lylSpringMvcConfig.getContextPath();
            LylSpringMvcClient clazzAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), LylSpringMvcClient.class);
            String prePath = "";
            if (Objects.nonNull(clazzAnnotation)) {
                if (clazzAnnotation.path().indexOf("*") > 1) {
                    String finalPrePath = prePath;
                    executorService.execute(() -> post(buildJsonParams(clazzAnnotation, contextPath, finalPrePath)));
                    return bean;
                }
                prePath = clazzAnnotation.path();
            }
            final Method[] methods = ReflectionUtils.getUniqueDeclaredMethods(bean.getClass());
            for (Method method : methods) {
                LylSpringMvcClient lylSpringMvcClient = AnnotationUtils.findAnnotation(method, LylSpringMvcClient.class);
                if (Objects.nonNull(lylSpringMvcClient)) {
                    String finalPrePath = prePath;
                    executorService.execute(() -> post(buildJsonParams(lylSpringMvcClient, contextPath, finalPrePath)));
                }
            }
        }
        return bean;
    }

    private void post(final String json) {
        try {
            String result = OkHttpUtils.getInstance().post(url, json);
            if (Objects.equals(result, "success")) {
                log.info("http client register success :{} " + json);
            } else {
                log.error("http client register error :{} " + json);
            }
        } catch (IOException e) {
            log.error("cannot register soul admin param :{}", url + ":" + json);
        }
    }

    private String buildJsonParams(final LylSpringMvcClient lylSpringMvcClient, final String contextPath, final String prePath) {
        String appName = lylSpringMvcConfig.getAppName();
        Integer port = lylSpringMvcConfig.getPort();
        String path = contextPath + prePath + lylSpringMvcClient.path();
        String desc = lylSpringMvcClient.desc();
        String configHost = lylSpringMvcConfig.getHost();
        String host = ("".equals(configHost) || null == configHost) ? IpUtils.getHost() : configHost;
        String configRuleName = lylSpringMvcClient.ruleName();
        String ruleName = ("".equals(configRuleName)) ? path : configRuleName;
        SpringMvcRegisterDTO registerDTO = SpringMvcRegisterDTO.builder()
                .context(contextPath)
                .host(host)
                .port(port)
                .appName(appName)
                .path(path)
                .pathDesc(desc)
                .rpcType(lylSpringMvcClient.rpcType())
                .enabled(lylSpringMvcClient.enabled())
                .ruleName(ruleName)
                .registerMetaData(lylSpringMvcClient.registerMetaData())
                .build();
        return OkHttpUtils.getInstance().getGosn().toJson(registerDTO);
    }
}

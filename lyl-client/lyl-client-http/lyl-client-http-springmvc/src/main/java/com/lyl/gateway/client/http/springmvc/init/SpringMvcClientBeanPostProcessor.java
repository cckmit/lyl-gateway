package com.lyl.gateway.client.http.springmvc.init;

import com.lyl.gateway.client.core.disruptor.LylClientRegisterEventPunlisher;
import com.lyl.gateway.client.http.springmvc.annotation.LylSpringMvcClient;
import com.lyl.gateway.common.utils.IpUtils;
import com.lyl.gateway.register.client.spi.LylClientRegisterRepository;
import com.lyl.gateway.register.common.config.LylRegisterCenterConfig;
import com.lyl.gateway.register.common.dto.MetaDataRegisterDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Properties;
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

    private LylClientRegisterEventPunlisher punlisher = LylClientRegisterEventPunlisher.getInstance();
    private final ThreadPoolExecutor executorService;
    private final String contextPath;
    private final String appName;
    private final String host;
    private final Integer port;
    private final Boolean isFull;

    public SpringMvcClientBeanPostProcessor(final LylRegisterCenterConfig config, final LylClientRegisterRepository lylClientRegisterRepository) {

        String registerType = config.getRegisterType();
        String serverLists = config.getServerLists();
        Properties props = config.getProps();
        int port = Integer.parseInt(props.getProperty("port"));
        if (StringUtils.isBlank(registerType) || StringUtils.isBlank(serverLists) || port <= 0) {
            String errorMsg = "http register param must config the registerType , serverLists and port must > 0";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        this.appName = props.getProperty("appName");
        this.host = props.getProperty("host");
        this.port = port;
        this.contextPath = props.getProperty("contextPath");
        this.isFull = Boolean.parseBoolean(props.getProperty("isFull", "false"));
        executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        punlisher.start(lylClientRegisterRepository);

    }

    @Override
    public Object postProcessAfterInitialization(@NonNull final Object bean, @NonNull final String beanName) throws BeansException {
        if (isFull){
            return bean;
        }
        Controller controller = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(bean.getClass(), RequestMapping.class);
        if (controller != null || requestMapping != null){
            LylSpringMvcClient classAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), LylSpringMvcClient.class);
            String prePath = "";
            if (Objects.isNull(classAnnotation)){
                return bean;
            }
            if (classAnnotation.path().indexOf("*") > 1){
                String finalPrePath = prePath;
                executorService.execute(() -> punlisher.publishEvent(buildMetaDataDTO(classAnnotation, finalPrePath)));
                return bean;
            }
            prePath = classAnnotation.path();
            final Method[] methods = ReflectionUtils.getUniqueDeclaredMethods(bean.getClass());
            for (Method method : methods){
                LylSpringMvcClient lylSpringMvcClient = AnnotationUtils.findAnnotation(method, LylSpringMvcClient.class);
                if (Objects.nonNull(lylSpringMvcClient)){
                    String finalPrePath = prePath;
                    executorService.execute(() -> punlisher.publishEvent(buildMetaDataDTO(lylSpringMvcClient, finalPrePath)));
                }
            }
        }
        return bean;
    }

    private MetaDataRegisterDTO buildMetaDataDTO(final LylSpringMvcClient lylSpringMvcClient, final String prePath) {
        String contextPath = this.contextPath;
        String appName = this.appName;
        Integer port = this.port;
        String path;
        if (StringUtils.isEmpty(contextPath)) {
            path = prePath + lylSpringMvcClient.path();
        } else {
            path = contextPath + prePath + lylSpringMvcClient.path();
        }
        String desc = lylSpringMvcClient.desc();
        String configHost = this.host;
        String host = StringUtils.isBlank(configHost) ? IpUtils.getHost() : configHost;
        String configRuleName = lylSpringMvcClient.ruleName();
        String ruleName = StringUtils.isBlank(configRuleName) ? path : configRuleName;
        return MetaDataRegisterDTO.builder()
                .contextPath(contextPath)
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
    }
}























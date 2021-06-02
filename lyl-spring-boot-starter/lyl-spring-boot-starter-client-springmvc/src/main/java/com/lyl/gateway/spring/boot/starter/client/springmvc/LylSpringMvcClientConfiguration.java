package com.lyl.gateway.spring.boot.starter.client.springmvc;

import com.lyl.gateway.client.http.springmvc.config.LylSpringMvcConfig;
import com.lyl.gateway.client.http.springmvc.init.ContextRegisterListener;
import com.lyl.gateway.client.http.springmvc.init.SpringMvcClientBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName LylSpringMvcClientConfiguration
 * @Description
 * @Author lyl
 * @Date 2021/6/2 17:45
 **/
@Configuration
public class LylSpringMvcClientConfiguration {

    @Bean
    public SpringMvcClientBeanPostProcessor springHttpClientBeanPostProcessor(final LylSpringMvcConfig lylSpringMvcConfig) {
        return new SpringMvcClientBeanPostProcessor(lylSpringMvcConfig);
    }

    @Bean
    public ContextRegisterListener contextRegisterListener(final LylSpringMvcConfig lylSpringMvcConfig) {
        return new ContextRegisterListener(lylSpringMvcConfig);
    }

    @Bean
    @ConfigurationProperties(prefix = "lyl.http")
    public LylSpringMvcConfig soulHttpConfig() {
        return new LylSpringMvcConfig();
    }

}

package com.lyl.gateway.client.http.springmvc.config;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName SoulSpringmvcConfig
 * @Description
 * @Author lyl
 * @Date 2021/6/2 17:11
 **/
@Data
public class LylSpringMvcConfig {

    private String adminUrl;
    private String contextPath;
    private String appName;
    private boolean full;
    private String host;
    private Integer port;

}

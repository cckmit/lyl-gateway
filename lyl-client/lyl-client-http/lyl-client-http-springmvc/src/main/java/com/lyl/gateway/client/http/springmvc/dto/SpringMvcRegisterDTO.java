package com.lyl.gateway.client.http.springmvc.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName SpringMvcRegisterDTO
 * @Description
 * @Author lyl
 * @Date 2021/6/2 17:30
 **/
@Data
@Builder
public class SpringMvcRegisterDTO {

    private String appName;
    private String context;
    private String path;
    private String pathDesc;
    private String rpcType;
    private String host;
    private Integer port;
    private String ruleName;
    private boolean enabled;
    private boolean registerMetaData;

}

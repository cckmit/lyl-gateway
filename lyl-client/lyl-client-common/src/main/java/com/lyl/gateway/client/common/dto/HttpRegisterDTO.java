package com.lyl.gateway.client.common.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName HttpRegisterDTO
 * @Description
 * @Author lyl
 * @Date 2021/6/2 17:05
 **/
@Data
@Builder
public class HttpRegisterDTO {

    private String appName;
    private String context;
    private String path;
    private String pathDesc;
    private String rpcType;
    private String serviceName;
    private String methodName;
    private String host;
    private String port;
    private Boolean writeMetaData;
    private String ruleName;
    private boolean enabled;

}



























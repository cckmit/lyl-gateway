package com.lyl.gateway.client.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @ClassName RpcTypeEnum
 * @Description
 * @Author lyl
 * @Date 2021/6/2 17:08
 **/
@Getter
@RequiredArgsConstructor
public enum RpcTypeEnum {

    HTTP("http"),
    DUBBO("dubbo"),
    SPRING_CLOUD("springCloud"),
    MOTAN("motan"),
    GRPC("grpc");
    private final String name;

}

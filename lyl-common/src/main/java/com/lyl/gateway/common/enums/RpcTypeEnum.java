package com.lyl.gateway.common.enums;

import com.lyl.gateway.common.exception.LylException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName RpcTypeEnum
 * @Description
 * @Author Administrator
 * @Date 2021/5/30 14:32
 **/
@Getter
@RequiredArgsConstructor
public enum RpcTypeEnum {

    HTTP("http", true),
    DUBBO("dubbo", true),
    SOFA("sofa", true),
    TARS("tars", true),
    WEB_SOCKET("websocket", true),
    SPRING_CLOUD("springcloud", true),
    MOTAN("motan", true),
    GRPC("grpc", true);


    private final String name;
    private final Boolean support;

    public static List<RpcTypeEnum> acquireSupports(){
        return Arrays.stream(RpcTypeEnum.values())
                .filter(e -> e.support).collect(Collectors.toList());
    }

    public static List<RpcTypeEnum> acquireSupportURIs(){
        return Arrays.asList(RpcTypeEnum.GRPC,
                RpcTypeEnum.HTTP,
                RpcTypeEnum.TARS);
    }

    public static List<RpcTypeEnum> acquireSupportMetadatas(){
        return Arrays.asList(RpcTypeEnum.DUBBO,
                RpcTypeEnum.GRPC,
                RpcTypeEnum.HTTP,
                RpcTypeEnum.SPRING_CLOUD,
                RpcTypeEnum.SOFA,
                RpcTypeEnum.TARS);
    }

    public static RpcTypeEnum acquireByName(final String name){
        return Arrays.stream(RpcTypeEnum.values())
                .filter(e -> e.support && e.name.equals(name)).findFirst()
                .orElseThrow(() -> new LylException(String.format(" this rpc type can not support %s", name)));
    }

}

package com.lyl.gateway.common.enums;

import com.lyl.gateway.common.exception.LylException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @ClassName ConfigGroupEnum
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:45
 **/
public enum ConfigGroupEnum {

    APP_AUTH,
    PLUGIN,
    RULE,
    SELECTOR,
    META_DATA;

    public static ConfigGroupEnum acquireByName(final String name) {
        return Arrays.stream(ConfigGroupEnum.values())
                .filter(e -> Objects.equals(e.name(), name))
                .findFirst().orElseThrow(() -> new LylException(String.format(" this ConfigGroupEnum can not support %s", name)));
    }

}

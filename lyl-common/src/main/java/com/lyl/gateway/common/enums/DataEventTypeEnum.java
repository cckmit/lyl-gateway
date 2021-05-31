package com.lyl.gateway.common.enums;

import com.lyl.gateway.common.exception.LylException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @ClassName DataEventTypeEnum
 * @Description
 * @Author lyl
 * @Date 2021/5/31 16:19
 **/
public enum DataEventTypeEnum {

    DELETE,
    CREATE,
    UPDATE,
    REFRESH,
    MYSELF;

    public static DataEventTypeEnum acquireByName(final String name) {
        return Arrays.stream(DataEventTypeEnum.values())
                .filter(e -> Objects.equals(e.name(), name))
                .findFirst().orElseThrow(() -> new LylException(String.format(" this DataEventTypeEnum can not support %s", name)));
    }

}

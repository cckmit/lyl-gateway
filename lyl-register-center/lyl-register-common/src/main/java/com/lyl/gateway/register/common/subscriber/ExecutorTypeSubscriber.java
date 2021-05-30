package com.lyl.gateway.register.common.subscriber;

import com.lyl.gateway.register.common.type.DataType;
import com.lyl.gateway.register.common.type.DataTypeParent;

/**
 * @ClassName ExecutorTypeSubscriber
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 20:35
 **/
public interface ExecutorTypeSubscriber<T extends DataTypeParent> extends ExecutorSubScriber<T> {

    DataType getType();

}

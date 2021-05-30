package com.lyl.gateway.register.common.subscriber;

import java.util.Collection;

/**
 * @ClassName ExecutorSubScriber
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 16:23
 **/
public interface ExecutorSubScriber<T> {

    void executor(Collection<T> dataList);

}

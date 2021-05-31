package com.lyl.gateway.sync.data.api;

import com.lyl.gateway.common.dto.AppAuthData;
import com.lyl.gateway.common.dto.AuthPathData;
import com.lyl.gateway.common.dto.MetaData;

/**
 * @ClassName AuthDataSubscriber
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:20
 **/
public interface AuthDataSubscriber {

    void onSubscribe(AppAuthData appAuthData);
    void unSubscribe(AppAuthData appAuthData);
    default void refresh(){}

}

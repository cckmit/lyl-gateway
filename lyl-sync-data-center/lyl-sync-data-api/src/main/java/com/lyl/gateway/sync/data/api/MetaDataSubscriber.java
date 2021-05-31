package com.lyl.gateway.sync.data.api;

import com.lyl.gateway.common.dto.MetaData;

/**
 * @ClassName MetaDataSubscriber
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:15
 **/
public interface MetaDataSubscriber {

    void onSubscribe(MetaData metaData);
    void unSubscribe(MetaData metaData);
    default void refresh(){}

}

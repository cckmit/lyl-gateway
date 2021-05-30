package com.lyl.gateway.sync.data.api;

import com.lyl.gateway.common.dto.PluginData;

import java.util.List;

/**
 * @ClassName PluginDataSubscriber
 * @Description
 * @Author Administrator
 * @Date 2021/5/30 23:25
 **/
public interface PluginDataSubscriber {

    default void onSubscribe(PluginData pluginData){}
    default void unSubscribe(PluginData pluginData){}
    default void refreshPluginDataAll(){}
    default void refreshPluginDataSelf(List<PluginData> pluginDataList){}
    default void onSelectorSubscribe(){}

}

package com.lyl.gateway.sync.data.websocket.handler;

import com.lyl.gateway.common.dto.PluginData;
import com.lyl.gateway.common.utils.GsonUtils;
import com.lyl.gateway.sync.data.api.PluginDataSubscriber;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @ClassName PluginDataHandler
 * @Description
 * @Author lyl
 * @Date 2021/5/31 16:13
 **/
@RequiredArgsConstructor
public class PluginDataHandler extends AbstractDataHandler<PluginData> {

    private final PluginDataSubscriber pluginDataSubscriber;

    @Override
    protected List<PluginData> convert(String json) {
        return GsonUtils.getInstance().fromList(json, PluginData.class);
    }

    @Override
    protected void doRefresh(List<PluginData> dataList) {
        pluginDataSubscriber.refreshPluginDataSelf(dataList);
        dataList.forEach(pluginDataSubscriber::onSubscribe);
    }

    @Override
    protected void doUpdate(List<PluginData> dataList) {
        dataList.forEach(pluginDataSubscriber::onSubscribe);
    }

    @Override
    protected void doDelete(List<PluginData> dataList) {
        dataList.forEach(pluginDataSubscriber::unSubscribe);
    }
}

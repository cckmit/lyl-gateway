package com.lyl.gateway.sync.data.websocket.handler;

import com.lyl.gateway.common.enums.DataEventTypeEnum;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @ClassName AbstractDataHandler
 * @Description
 * @Author lyl
 * @Date 2021/5/31 16:13
 **/
public abstract class AbstractDataHandler<T> implements DataHandler {

    protected abstract List<T> convert(String json);
    protected abstract void doRefresh(List<T> dataList);
    protected abstract void doUpdate(List<T> dataList);
    protected abstract void doDelete(List<T> dataList);

    @Override
    public void handle(final String json, final String eventType) {
        List<T> dataList = convert(json);
        if (CollectionUtils.isNotEmpty(dataList)){
            DataEventTypeEnum eventTypeEnum = DataEventTypeEnum.acquireByName(eventType);
            switch (eventTypeEnum){
                case REFRESH:
                case MYSELF:
                    doRefresh(dataList);
                    break;
                case UPDATE:
                case CREATE:
                    doUpdate(dataList);
                    break;
                case DELETE:
                    doDelete(dataList);
                    break;
                default:
                    break;
            }
        }
    }
}

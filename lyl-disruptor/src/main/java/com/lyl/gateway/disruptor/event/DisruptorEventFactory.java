package com.lyl.gateway.disruptor.event;

import com.lmax.disruptor.EventFactory;

/**
 * @ClassName DisruptorEventFactory
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 16:54
 **/
public class DisruptorEventFactory<T> implements EventFactory<DataEvent<T>> {
    @Override
    public DataEvent<T> newInstance() {
        return new DataEvent<>();
    }
}

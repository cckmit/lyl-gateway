package com.lyl.gateway.disruptor.consumer;

import com.lmax.disruptor.WorkHandler;
import com.lyl.gateway.disruptor.event.DataEvent;

import java.util.concurrent.ExecutorService;

/**
 * @ClassName QueueConsumer
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 17:05
 **/
public class QueueConsumer<T> implements WorkHandler<DataEvent<T>> {

    private ExecutorService executor;
    private QueueConsumerFactory<T> factory;

    public QueueConsumer(final ExecutorService executor, final QueueConsumerFactory<T> factory){
        this.executor = executor;
        this.factory = factory;
    }

    @Override
    public void onEvent(DataEvent<T> t) {
        if (null != t){
            QueueConsumerExecutor<T> queueConsumerExecutor = factory.create();

        }
    }
}

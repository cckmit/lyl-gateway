package com.lyl.gateway.disruptor.consumer;

/**
 * @ClassName QueueConsumerFactory
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 17:07
 **/
public interface QueueConsumerFactory<T> {

    QueueConsumerExecutor create();

    String fixName();

}

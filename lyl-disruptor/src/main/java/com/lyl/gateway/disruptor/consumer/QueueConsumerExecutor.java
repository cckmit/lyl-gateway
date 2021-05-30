package com.lyl.gateway.disruptor.consumer;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName QueueConsumerFactory
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 17:06
 **/
@Getter
@Setter
public abstract class QueueConsumerExecutor<T> implements Runnable {

    private T data;

}

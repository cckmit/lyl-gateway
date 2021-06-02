package com.lyl.gateway.disruptor.provider;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lyl.gateway.disruptor.event.DataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @ClassName DisruptorProvider
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 16:40
 **/
public class DisruptorProvider<T> {

    private Logger logger = LoggerFactory.getLogger(DisruptorProvider.class);

    private final RingBuffer<DataEvent<T>> ringBuffer;
    private final Disruptor<DataEvent<T>> disruptor;

    public DisruptorProvider(final RingBuffer<DataEvent<T>> ringBuffer, final Disruptor<DataEvent<T>> disruptor){
        this.ringBuffer = ringBuffer;
        this.disruptor = disruptor;
    }

    public void onData(final Consumer<DataEvent<T>> function){
        long position = ringBuffer.next();
        try {
            DataEvent<T> dataEvent = ringBuffer.get(position);
            function.accept(dataEvent);
            ringBuffer.publish(position);
        } catch (Exception ex){
            logger.error("ex", ex);
        }
    }

    public void shutdown(){
        if (null != disruptor){
            disruptor.shutdown();
        }
    }

}

package com.lyl.gateway.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lyl.gateway.disruptor.consumer.QueueConsumer;
import com.lyl.gateway.disruptor.consumer.QueueConsumerFactory;
import com.lyl.gateway.disruptor.event.DataEvent;
import com.lyl.gateway.disruptor.event.DisruptorEventFactory;
import com.lyl.gateway.disruptor.provider.DisruptorProvider;
import com.lyl.gateway.disruptor.thread.DisruptorThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DisruptorProviderManage
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 16:38
 **/
public class DisruptorProviderManage<T> {

    public static final Integer DEFAULT_SIZE = 4096 << 1 << 1;
    private static final Integer DEFAULT_CONSUMER_SIZE = Runtime.getRuntime().availableProcessors();
    private final Integer size;
    private DisruptorProvider<T> provider;
    private Integer consumerSize;
    private QueueConsumerFactory consumerFactory;
    private ExecutorService executor;

    public DisruptorProviderManage(final QueueConsumerFactory<T> consumerFactory,
                                   final int ringBufferSize,
                                   final int consumerSize){
        this.consumerFactory = consumerFactory;
        this.size = ringBufferSize;
        this.consumerSize = consumerSize;
        this.executor = new ThreadPoolExecutor(consumerSize,
                consumerSize,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(),
                DisruptorThreadFactory.create("lyl_disruptor_consumer", false),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public DisruptorProviderManage(final QueueConsumerFactory<T> consumerFactory,
                                   final Integer ringBufferSize){
        this(consumerFactory, ringBufferSize, DEFAULT_SIZE);
    }

    public DisruptorProviderManage(final QueueConsumerFactory<T> consumerFactory){
        this(consumerFactory, DEFAULT_CONSUMER_SIZE, DEFAULT_SIZE);
    }


    public DisruptorProvider<T> getProvider(){
        return provider;
    }

    public void startUp(){
        Disruptor<DataEvent<T>> disruptor = new Disruptor<DataEvent<T>>(new DisruptorEventFactory<>(),
                size,
                DisruptorThreadFactory.create("lyl_disruptor_provider_" + consumerFactory.fixName(), false),
                ProducerType.MULTI,
                new BlockingWaitStrategy());
        QueueConsumer<T>[] consumers = new QueueConsumer[consumerSize];
        for (int i = 0; i < consumerSize; i++){
            consumers[i] = new QueueConsumer<>(executor,consumerFactory);
        }
        disruptor.handleEventsWithWorkerPool(consumers);
        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        disruptor.start();
        RingBuffer<DataEvent<T>> ringBuffer = disruptor.getRingBuffer();
        provider = new DisruptorProvider<>(ringBuffer, disruptor);
    }

}

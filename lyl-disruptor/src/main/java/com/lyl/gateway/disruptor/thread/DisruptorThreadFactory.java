package com.lyl.gateway.disruptor.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName DisruptorThreadFactory
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 18:37
 **/
public final class DisruptorThreadFactory implements ThreadFactory {

    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);
    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("lyl-disruptor");
    private final boolean daemon;
    private final String namePrefix;
    private final int priority;

    private DisruptorThreadFactory(final String namePrefix,
                                  final boolean daemon,
                                  final int priority){
        this.namePrefix = namePrefix;
        this.daemon = daemon;
        this.priority = priority;
    }

    public static ThreadFactory create(final String namePrefix,
                                       final boolean daemon){
        return create(namePrefix, daemon, Thread.NORM_PRIORITY);
    }

    public static ThreadFactory create(final String namePrefix,
                                       final boolean daemon,
                                       final int priority){
        return new DisruptorThreadFactory(namePrefix, daemon, priority);
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        Thread thread = new Thread(THREAD_GROUP,
                runnable,
                THREAD_GROUP.getName() + "-" + namePrefix + "-" + THREAD_NUMBER.getAndDecrement());
        thread.setDaemon(daemon);
        thread.setPriority(priority);
        return thread;
    }
}

package com.lyl.gateway.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName LylThreadFactory
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:33
 **/
public final class LylThreadFactory implements ThreadFactory {

    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);
    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("LYL");
    private final boolean daemon;
    private final String namePrefix;
    private final int priority;

    private LylThreadFactory(final String namePrefix,
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

    public static ThreadFactory create(final String namePrefix, final boolean daemon, final int priority){
        return new LylThreadFactory(namePrefix, daemon, priority);
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(THREAD_GROUP,
                                    runnable,
                                    THREAD_GROUP.getName() + "-" + namePrefix + "-" + THREAD_NUMBER.getAndIncrement());
        thread.setDaemon(daemon);
        thread.setPriority(priority);
        return thread;
    }
}

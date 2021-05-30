package com.lyl.gateway.client.core.shutdown;

import com.lyl.gateway.register.client.spi.LylClientRegisterRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName LylClientShutdownHook
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 20:41
 **/
@Slf4j
public class LylClientShutdownHook {

    private static String hookNamePrefix = "LylClientShutHook";
    private static AtomicInteger hookId = new AtomicInteger();
    private static Properties props;
    private static AtomicBoolean delay = new AtomicBoolean(false);
    private static IdentityHashMap<Thread, Thread> delayHooks = new IdentityHashMap<>();
    private static IdentityHashMap<Thread, Thread> delayedHooks = new IdentityHashMap<>();

    public static void set(final LylClientRegisterRepository result, final Properties props){
        String name = hookNamePrefix + "-" + hookId.incrementAndGet();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            result.close();
        }, name));
        log.info("Add hook {}", name);
        LylClientShutdownHook.props = props;
    }

    public static void delayOtherHooks(){
        if (!delay.compareAndSet(false, true)){
            return;
        }
        TakeOverOtherHooksThread thread = new TakeOverOtherHooksThread();
        thread.start();;
    }

    public static class TakeOverOtherHooksThread extends Thread{

        @SneakyThrows
        @Override
        public void run() {
            int shutdownWaitTime = Integer.parseInt(props.getProperty("shutdownWaitTime", "3000"));
            int delayOtherHooksExecTime = Integer.parseInt(props.getProperty("delayOtherHooksExecTime", "2000"));
            Class<?> clazz = Class.forName(props.getProperty("applicationShutdownHooksClassName", "java.lang.ApplicationShutdownHooks"));
            Field field = clazz.getDeclaredField(props.getProperty("applicationShutdownHooksFieldName", "hooks"));
            field.setAccessible(true);
            IdentityHashMap<Thread, Thread> hooks = (IdentityHashMap<Thread, Thread>) field.get(clazz);
            long s = System.currentTimeMillis();
            while (System.currentTimeMillis() - s < delayOtherHooksExecTime){
                for (Iterator<Thread> iterator = hooks.keySet().iterator(); iterator.hasNext();) {
                    Thread hook = iterator.next();
                    if (hook.getName().startsWith(hookNamePrefix)){
                        continue;
                    }
                    if (delayHooks.containsKey(hook) || delayedHooks.containsKey(hook)){
                        continue;
                    }
                    Thread delayHook = new Thread(() -> {
                        log.info("sleep {}ms", shutdownWaitTime);
                        try {
                            TimeUnit.MICROSECONDS.sleep(shutdownWaitTime);
                        } catch (InterruptedException ex){
                            ex.printStackTrace();
                        }
                        hook.run();
                    }, hook.getName());
                    delayHooks.put(delayHook,delayHook);
                    iterator.remove();
                }
                for (Iterator<Thread> iterator = delayHooks.keySet().iterator(); iterator.hasNext();) {
                    Thread delayHook = iterator.next();
                    Runtime.getRuntime().addShutdownHook(delayHook);
                    delayedHooks.put(delayHook, delayHook);
                    iterator.remove();
                    log.info("hook {} will sleep {}ms when it start", delayHook.getName(), shutdownWaitTime);
                }
                TimeUnit.MICROSECONDS.sleep(100);
            }
            hookNamePrefix = null;
            hookId = null;
            props = null;
            delayHooks = null;
            delayedHooks = null;
        }
    }

}

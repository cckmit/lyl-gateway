package com.lyl.gateway.client.core.disruptor;

import com.lyl.gateway.client.core.disruptor.executor.RegisterClientConsumerExecutor;
import com.lyl.gateway.client.core.disruptor.subcriber.LylClientMetadataExecutorSubcriber;
import com.lyl.gateway.disruptor.DisruptorProviderManage;
import com.lyl.gateway.disruptor.provider.DisruptorProvider;
import com.lyl.gateway.register.client.spi.LylClientRegisterRepository;

/**
 * @ClassName LylClientRegisterEventPunlisher
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 16:31
 **/
public class LylClientRegisterEventPunlisher {

    private static final LylClientRegisterEventPunlisher INSTANCE = new LylClientRegisterEventPunlisher();

    private DisruptorProviderManage providerManage;

    private RegisterClientConsumerExecutor.RegisterClientExecutorFactory factory;

    public static LylClientRegisterEventPunlisher getInstance(){
        return INSTANCE;
    }

    public void start(final LylClientRegisterRepository lylClientRegisterRepository){
        factory = new RegisterClientConsumerExecutor.RegisterClientExecutorFactory(new LylClientMetadataExecutorSubcriber(lylClientRegisterRepository));
        providerManage = new DisruptorProviderManage(factory);
        providerManage.startUp();
    }

    public <T> void publishEvent(final T data){
        DisruptorProvider<Object> provider = providerManage.getProvider();
        provider.onData(f -> f.setData(data));
    }

}

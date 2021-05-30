package com.lyl.gateway.client.core.disruptor.executor;

import com.google.common.collect.Lists;
import com.lyl.gateway.disruptor.consumer.QueueConsumerExecutor;
import com.lyl.gateway.disruptor.consumer.QueueConsumerFactory;
import com.lyl.gateway.register.common.subscriber.ExecutorSubScriber;
import com.lyl.gateway.register.common.type.DataTypeParent;

/**
 * @ClassName RegisterClientConsumerExecutor
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 19:07
 **/
public class RegisterClientConsumerExecutor extends QueueConsumerExecutor<DataTypeParent> {

    private ExecutorSubScriber executorSubScriber;

    public RegisterClientConsumerExecutor(final ExecutorSubScriber executorSubScriber){
        this.executorSubScriber = executorSubScriber;
    }

    @Override
    public void run() {
        DataTypeParent result = getData();
        executorSubScriber.executor(Lists.newArrayList(result));
    }

    public static class RegisterClientExecutorFactory implements QueueConsumerFactory {

        private ExecutorSubScriber executorSubScriber;

        public RegisterClientExecutorFactory(final ExecutorSubScriber executorSubScriber){
            this.executorSubScriber = executorSubScriber;
        }

        @Override
        public QueueConsumerExecutor create() {
            return new RegisterClientConsumerExecutor(executorSubScriber);
        }

        @Override
        public String fixName() {
            return "lyl_register_client";
        }
    }

}

package com.lyl.gateway.disruptor.event;

import lombok.Data;

/**
 * @ClassName DataEvent
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 16:54
 **/
@Data
public class DataEvent<T> {

    private T data;

}

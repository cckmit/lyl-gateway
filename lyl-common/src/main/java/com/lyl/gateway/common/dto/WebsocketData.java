package com.lyl.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName WebsocketData
 * @Description
 * @Author lyl
 * @Date 2021/5/31 17:02
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class WebsocketData<T> implements Serializable {

    private String groupType;
    private String eventType;
    private List<T> data;

}

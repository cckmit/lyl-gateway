package com.lyl.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PluginData
 * @Description
 * @Author Administrator
 * @Date 2021/5/30 23:27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionData implements Serializable {

    private String paramType;
    private String operator;
    private String paramName;
    private String paramValue;

}

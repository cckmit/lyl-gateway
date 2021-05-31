package com.lyl.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName RuleData
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:10
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RuleData implements Serializable {

    private String id;
    private String name;
    private String pluginName;
    private String selectorId;
    private Integer matchMode;
    private Integer sort;
    private Boolean enabled;
    private Boolean loged;
    private String handle;
    private List<ConditionData> conditionDataList;

}
















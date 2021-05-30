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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectorData implements Serializable {

    private String id;
    private String pluginId;
    private String pluginName;
    private String name;
    private Integer matchMode;
    private Integer type;
    private Integer sort;
    private Boolean enabled;
    private Boolean logged;
    private Boolean continued;
    private String handle;
    private List<ConditionData> conditionData;

}

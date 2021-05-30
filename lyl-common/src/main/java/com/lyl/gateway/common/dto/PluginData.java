package com.lyl.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class PluginData implements Serializable {

    private String id;
    private String name;
    private String config;
    private Integer role;
    private Boolean enabled;

}

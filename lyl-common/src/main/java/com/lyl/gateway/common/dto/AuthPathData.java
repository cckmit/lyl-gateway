package com.lyl.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName AuthPathData
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthPathData implements Serializable {

    private String appName;
    private String path;
    private Boolean enabled;

}

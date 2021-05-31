package com.lyl.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName AuthParamData
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthParamData implements Serializable {

    private String appName;
    private String appParam;

}

package com.lyl.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName AppAuthData
 * @Description
 * @Author lyl
 * @Date 2021/5/31 11:20
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppAuthData implements Serializable {

    private String appkey;
    private String appSecret;
    private Boolean enabled;
    private Boolean open;
    private List<AppAuthData> paramDataList;
    private List<AuthPathData> pathDataList;

}

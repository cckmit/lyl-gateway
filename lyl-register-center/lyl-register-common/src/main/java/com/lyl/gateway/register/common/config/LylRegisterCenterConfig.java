package com.lyl.gateway.register.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Properties;

/**
 * @ClassName LylRegisterCenterConfig
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 20:02
 **/
@Getter
@Setter
@RequiredArgsConstructor
public final class LylRegisterCenterConfig {

    private String registerType;
    private String serverLists;
    private Properties props = new Properties();

}

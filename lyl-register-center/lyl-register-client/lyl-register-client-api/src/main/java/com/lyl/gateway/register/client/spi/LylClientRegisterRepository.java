package com.lyl.gateway.register.client.spi;

import com.lyl.gateway.register.common.config.LylRegisterCenterConfig;
import com.lyl.gateway.register.common.dto.MetaDataRegisterDTO;
import com.lyl.gateway.spi.SPI;

/**
 * @ClassName LylClientRegisterRepository
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 19:44
 **/
@SPI
public interface LylClientRegisterRepository {

    String PATH_SEPARATOR = "/";

    String DOT_SEPARATROR = ".";

    default void init(LylRegisterCenterConfig config){
    }

    void persistInterface(MetaDataRegisterDTO metaData);

    default void close(){

    }

}

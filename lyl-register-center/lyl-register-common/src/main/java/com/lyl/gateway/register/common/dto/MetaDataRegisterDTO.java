package com.lyl.gateway.register.common.dto;

import com.lyl.gateway.register.common.type.DataType;
import com.lyl.gateway.register.common.type.DataTypeParent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName MetaDataRegisterDTO
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 20:07
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaDataRegisterDTO implements DataTypeParent, Serializable {

    private String appName;
    private String contextPath;
    private String path;
    private String pathDesc;
    private String rpcType;
    private String serviceName;
    private String methodName;
    private String ruleName;
    private String parameterTypes;
    private String rpcExt;
    private boolean enabled;
    private String host;
    private Integer port;
    private List<String> pluginsName;
    private boolean registerMetaData;


    @Override
    public DataType getType() {
        return DataType.META_DATA;
    }
}

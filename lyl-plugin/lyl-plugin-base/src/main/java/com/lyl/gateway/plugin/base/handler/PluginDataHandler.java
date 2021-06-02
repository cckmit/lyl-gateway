package com.lyl.gateway.plugin.base.handler;

import com.lyl.gateway.common.dto.PluginData;
import com.lyl.gateway.common.dto.RuleData;
import com.lyl.gateway.common.dto.SelectorData;

/**
 * @ClassName PluginDataHandler
 * @Description
 * @Author lyl
 * @Date 2021/6/2 14:20
 **/
public interface PluginDataHandler {

    default void handlerPlugin(PluginData pluginData){}
    default void removePlugin(PluginData pluginData){}
    default void handlerSelector(SelectorData selectorData){}
    default void removeSelector(SelectorData selectorData){}
    default void handlerRule(RuleData ruleData){}
    default void removeRule(RuleData ruleData){}
    String pluginNamed();

}

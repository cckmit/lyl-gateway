package com.lyl.gateway.sync.data.api;

import com.lyl.gateway.common.dto.PluginData;
import com.lyl.gateway.common.dto.RuleData;
import com.lyl.gateway.common.dto.SelectorData;

import java.util.List;

/**
 * @ClassName PluginDataSubscriber
 * @Description
 * @Author Administrator
 * @Date 2021/5/30 23:25
 **/
public interface PluginDataSubscriber {

    default void onSubscribe(PluginData pluginData){}
    default void unSubscribe(PluginData pluginData){}
    default void refreshPluginDataAll(){}
    default void refreshPluginDataSelf(List<PluginData> pluginDataList){}
    default void onSelectorSubscribe(SelectorData selectorData){}
    default void unSelectorSubscribe(SelectorData selectorData){}
    default void refreshSelectorDataAll(){}
    default void refreshSelectorDataSelf(List<SelectorData> selectorDataList){}
    default void onRuleSubscribe(RuleData ruleData){}
    default void unRuleSubscribe(RuleData ruleData){}
    default void refreshRuleDataAll(){}
    default void refreshRuleDataSelf(List<RuleData> ruleDataList){}

}

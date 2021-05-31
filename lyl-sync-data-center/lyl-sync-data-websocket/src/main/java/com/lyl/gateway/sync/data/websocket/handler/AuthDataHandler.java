package com.lyl.gateway.sync.data.websocket.handler;

import com.lyl.gateway.common.dto.AppAuthData;
import com.lyl.gateway.common.utils.GsonUtils;
import com.lyl.gateway.sync.data.api.AuthDataSubscriber;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @ClassName AuthDataHandler
 * @Description
 * @Author lyl
 * @Date 2021/5/31 16:42
 **/
@RequiredArgsConstructor
public class AuthDataHandler extends AbstractDataHandler<AppAuthData> {

    private final List<AuthDataSubscriber> authDataSubscribers;

    @Override
    public List<AppAuthData> convert(final String json) {
        return GsonUtils.getInstance().fromList(json, AppAuthData.class);
    }

    @Override
    protected void doRefresh(final List<AppAuthData> dataList) {
        authDataSubscribers.forEach(AuthDataSubscriber::refresh);
        dataList.forEach(appAuthData -> authDataSubscribers.forEach(authDataSubscriber -> authDataSubscriber.onSubscribe(appAuthData)));
    }

    @Override
    protected void doUpdate(final List<AppAuthData> dataList) {
        dataList.forEach(appAuthData -> authDataSubscribers.forEach(authDataSubscriber -> authDataSubscriber.onSubscribe(appAuthData)));
    }

    @Override
    protected void doDelete(final List<AppAuthData> dataList) {
        dataList.forEach(appAuthData -> authDataSubscribers.forEach(authDataSubscriber -> authDataSubscriber.unSubscribe(appAuthData)));
    }

}

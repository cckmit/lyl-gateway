package com.lyl.gateway.common.utils;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OkHttpTools
 * @Description
 * @Author lyl
 * @Date 2021/6/2 17:14
 **/
public final class OkHttpUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpUtils OK_HTTP_UTILS = new OkHttpUtils();
    private static final Gson GSON = new Gson();
    private OkHttpClient client;

    private OkHttpUtils(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        client = builder.build();
    }

    public static OkHttpUtils getInstance(){
        return OK_HTTP_UTILS;
    }

    public String post(final String url, final String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute().body().string();
    }

    public <T> T post(final String url, final String json, final Class<T> classOfT) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        final String result = response.body().string();
        return GSON.fromJson(result, classOfT);
    }

    public String post(final String url, final Map<String, String> params) throws IOException {
        String json = GSON.toJson(params);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute().body().string();
    }

    public Gson getGosn() {
        return GSON;
    }


}

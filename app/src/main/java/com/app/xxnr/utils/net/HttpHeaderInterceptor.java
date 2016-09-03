package com.app.xxnr.utils.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/18 16:19
 * 邮箱：hepeng@xinxinnongren.com
 */
public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("user-agent", "android-v1.1")
                .build();
        return chain.proceed(request);
    }
}

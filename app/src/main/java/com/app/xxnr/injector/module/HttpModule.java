package com.app.xxnr.injector.module;

import com.app.xxnr.Constants;
import com.app.xxnr.injector.scope.RetrofitGson;
import com.app.xxnr.injector.scope.RetrofitString;
import com.app.xxnr.utils.net.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by CAI on 2016/8/8.
 * 网络有关的实例仓库
 */
@Module
public class HttpModule {
    private static final int TIME_OUT = Constants.TIME_OUT;
    private static final String BASE_URL = Constants.BASE_URL;

    /**
     * 网络拦截器
     */
    @Provides
    @Singleton
    public HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(Constants.HTTP_LOG_LEVEL);
        return interceptor;
    }

    /**
     * 网络请求客户端OkHttpClient
     */
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }


    /**
     * 网络请求客户端retrofit(解析gson)
     */

    @Provides
    @Singleton
    @RetrofitGson
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    /**
     * 网络请求客户端retrofit(String)
     */

    @Provides
    @Singleton
    @RetrofitString
    public Retrofit provideRetrofitString(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }


}

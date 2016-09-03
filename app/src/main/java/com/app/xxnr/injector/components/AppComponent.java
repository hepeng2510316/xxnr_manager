package com.app.xxnr.injector.components;

import android.content.Context;

import com.app.xxnr.App;
import com.app.xxnr.DBManager;
import com.app.xxnr.UserManager;
import com.app.xxnr.api.ApiInterface;
import com.app.xxnr.api.ApiService;
import com.app.xxnr.injector.module.AppModule;
import com.app.xxnr.injector.module.HttpModule;
import com.app.xxnr.injector.scope.RetrofitGson;
import com.app.xxnr.injector.scope.RetrofitString;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by 何鹏 on 2016/8/4.
 * 提供全局的实例对象
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    //Exposed to sub-graphs.
    Context getContext();

    UserManager getUserInfo();

    DBManager getDBManager();

    Gson getGson();

    OkHttpClient getOkHttpClient();

    @RetrofitGson
    Retrofit getRetrofitGson();

    @RetrofitString
    Retrofit getRetrofitString();

    ApiInterface getInterface();

    ApiService getApiService();

    EventBus getEventBus();
    // inject to application
    void inject(App app);
}

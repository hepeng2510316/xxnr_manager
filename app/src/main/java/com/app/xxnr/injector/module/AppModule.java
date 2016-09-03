package com.app.xxnr.injector.module;

import android.content.Context;

import com.app.xxnr.DBManager;
import com.app.xxnr.UserManager;
import com.app.xxnr.api.ApiInterface;
import com.app.xxnr.api.ApiService;
import com.app.xxnr.injector.scope.RetrofitGson;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by CAI on 2016/8/4.
 * 全局实例仓库
 */
@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    /**
     *
     * @return Context(application)
     */
    @Singleton
    @Provides
    public  Context ProvideContext(){
        return context;
    }

    /**
     *
     * @return DBManager
     */
    @Singleton
    @Provides
    public DBManager ProvideDBManager(){
        return DBManager.getInstance(context);
    }

    /**
     *
     * @return UserManager
     */
    @Singleton
    @Provides
    public UserManager ProvideUserManager(DBManager dbManager){
        return UserManager.getInstance(context,dbManager);
    }

    /***
     * Gson客户端
     */
    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }


    /***
     * ApiInterface
     */
    @Provides
    @Singleton
    public ApiInterface provideApiInterface(@RetrofitGson Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }


    /***
     * ApiService
     */
    @Provides
    @Singleton
    public ApiService provideApiService(ApiInterface apiInterface, UserManager userManager) {
        return ApiService.getInstance(apiInterface,userManager);
    }


    /***
     * EventBus
     */
    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }




}

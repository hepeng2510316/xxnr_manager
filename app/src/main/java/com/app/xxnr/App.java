package com.app.xxnr;

import android.app.Application;

import com.app.xxnr.injector.components.AppComponent;
import com.app.xxnr.injector.components.DaggerAppComponent;
import com.app.xxnr.injector.module.AppModule;
import com.app.xxnr.injector.module.HttpModule;
import com.app.xxnr.utils.T;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by 何鹏 on 2016/8/4.
 * <p>
 * Application类
 */
public class App extends Application {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
        T.register(this);
        LeakCanary.install(this);
    }

    private void initComponent() {
        mComponent = DaggerAppComponent
                .builder()
                .httpModule(new HttpModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mComponent;
    }
}

package com.app.xxnr.injector.components;

import android.app.Activity;

import com.app.xxnr.injector.scope.PerActivity;
import com.app.xxnr.injector.module.ActivityModule;
import com.app.xxnr.ui.activity.CustomerDetailActivity;
import com.app.xxnr.ui.activity.CustomerSearchActivity;
import com.app.xxnr.ui.activity.LoginActivity;
import com.app.xxnr.ui.activity.MainActivity;
import com.app.xxnr.ui.activity.OrderDetailActivity;
import com.app.xxnr.ui.activity.OrderSearchActivity;
import com.app.xxnr.ui.activity.PotentialListActivity;
import com.app.xxnr.ui.activity.PotentialDetailActivity;
import com.app.xxnr.ui.activity.PotentialSearchActivity;
import com.app.xxnr.ui.activity.SplashActivity;
import com.app.xxnr.ui.activity.datacenter.DailyDetailActivity;
import com.app.xxnr.ui.activity.datacenter.WeekDetailActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();

    // inject Activity
    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(CustomerSearchActivity activity);

    void inject(CustomerDetailActivity activity);

    void inject(PotentialListActivity activity);

    void inject(PotentialSearchActivity activity);

    void inject(PotentialDetailActivity activity);

    void inject(OrderSearchActivity activity);

    void inject(OrderDetailActivity activity);

    void inject(DailyDetailActivity activity);

    void inject(WeekDetailActivity activity);


}
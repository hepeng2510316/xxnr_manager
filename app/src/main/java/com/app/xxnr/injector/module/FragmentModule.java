package com.app.xxnr.injector.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.app.xxnr.injector.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/09
 * 邮箱：hepeng@xinxinnongren.com
 */
@Module
public class FragmentModule {

    private final Fragment mFragment;
    private final Activity activity;


    public FragmentModule(Fragment fragment,Activity activity) {
        this.mFragment = fragment;
        this.activity=activity;
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return activity;
    }




}

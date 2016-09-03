package com.app.xxnr.injector.module;



import android.app.Activity;
import android.content.Context;

import com.app.xxnr.injector.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/6 18:13
 * 邮箱：hepeng@xinxinnongren.com
 */
@Module
public class ActivityModule {

  private final Activity mActivity;

  public ActivityModule(Activity mActivity) {
    this.mActivity = mActivity;
  }

  @Provides
  @PerActivity
  public Activity provideActivity() {
    return mActivity;
  }
}

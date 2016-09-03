package com.app.xxnr.injector.components;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.app.xxnr.injector.module.FragmentModule;
import com.app.xxnr.injector.scope.PerFragment;
import com.app.xxnr.ui.fragment.AgentFragment;
import com.app.xxnr.ui.fragment.AgentGroupReportAchievementFragment;
import com.app.xxnr.ui.fragment.AgentGroupReportOrderFragment;
import com.app.xxnr.ui.fragment.AgentYesterdayReportFragment;
import com.app.xxnr.ui.fragment.CustomerManageFragment;
import com.app.xxnr.ui.fragment.DailyReportFragment;
import com.app.xxnr.ui.fragment.OrderListFragment;
import com.app.xxnr.ui.fragment.WeekReportFragment;

import dagger.Component;

/**
 * 类描述：
 * 作者：何鹏 on 2016/4/19 15:23
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerFragment
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {

    //Exposed to sub-graphs.
    Activity getActivity();

    Fragment getFragment();

    // inject Fragment
    void inject(OrderListFragment fragment);

    void inject(CustomerManageFragment fragment);

    void inject(DailyReportFragment fragment);

    void inject(WeekReportFragment fragment);

    void inject(AgentFragment fragment);

    void inject(AgentYesterdayReportFragment fragment);

    void inject(AgentGroupReportAchievementFragment fragment);

    void inject(AgentGroupReportOrderFragment fragment);


}


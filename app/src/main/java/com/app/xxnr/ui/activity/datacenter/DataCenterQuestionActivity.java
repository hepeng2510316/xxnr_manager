package com.app.xxnr.ui.activity.datacenter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.app.xxnr.R;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.CommonPagerAdapter;
import com.app.xxnr.ui.fragment.DataCenterQuestionFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/11 14:42
 * 邮箱：hepeng@xinxinnongren.com
 */
public class DataCenterQuestionActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int initContentView() {
        return R.layout.activity_common_viewpager_tab;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        setTitle("数据说明");
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initTab();
        viewPager.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), titleList, fragments));
        viewPager.setOffscreenPageLimit(titleList.size());
        tabs.setupWithViewPager(viewPager);//设置联动
    }

    private void initTab() {
        if (titleList.isEmpty()){
            titleList.add("日报");
            titleList.add("周报");
            titleList.add("经纪人");

        }
        if (fragments.isEmpty()){
            for (int i = 0; i < titleList.size(); i++) {
                fragments.add(DataCenterQuestionFragment.newInstance(i));
            }
        }
    }

}

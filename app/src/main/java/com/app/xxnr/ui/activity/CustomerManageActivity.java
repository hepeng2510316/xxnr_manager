package com.app.xxnr.ui.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.app.xxnr.R;
import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.injector.HasComponent;
import com.app.xxnr.injector.components.ActivityComponent;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.CommonPagerAdapter;
import com.app.xxnr.ui.fragment.CustomerManageFragment;
import com.app.xxnr.ui.fragment.OrderListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 类描述：客户管理
 * 作者：何鹏 on 2016/5/3 17:48
 * 邮箱：hepeng@xinxinnongren.com
 */
public class CustomerManageActivity extends BaseSwipeBackActivity{

    @BindView(R.id.title_right_img)
    ImageView titleRightImg;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int initContentView() {
        return R.layout.activity_customer_manage;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        setTitle("客户管理");
        ButterKnife.bind(this);
        titleRightImg.setVisibility(View.VISIBLE);
        titleRightImg.setImageResource(R.mipmap.search_icon);
    }

    @Override
    public void initData() {
        initTab();
        viewPager.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), titleList, fragments));
        tabs.setupWithViewPager(viewPager);//设置联动
        viewPager.setOffscreenPageLimit(titleList.size());
    }

    private void initTab() {
        if (titleList.isEmpty()){
            titleList.add("所有客户");
            titleList.add("待认证");
        }
        if (fragments.isEmpty()){
            for (int i = 0; i < titleList.size(); i++) {
                fragments.add(CustomerManageFragment.newInstance(i));
            }
        }
    }


    @OnClick(R.id.ll_title_right_view)
    void ll_title_right_view() {
        startActivity(CustomerSearchActivity.class);
    }
}

package com.app.xxnr.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.xxnr.R;
import com.app.xxnr.event.OrderListShowPopBg;
import com.app.xxnr.event.PagerSwipeEvent;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.CommonPagerAdapter;
import com.app.xxnr.ui.fragment.OrderListFragment;
import com.app.xxnr.utils.xxnr.PopWindowUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：订单管理
 * 作者：何鹏 on 2016/8/18 16:51
 * 邮箱：hepeng@xinxinnongren.com
 */
public class OrderManagerActivity extends BaseSwipeBackActivity {
    @BindView(R.id.title_right_img)
    ImageView titleRightImg;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.pop_bg)
    RelativeLayout popBg;

    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();


    @Override
    public int initContentView() {
        return R.layout.activity_common_viewpager_tab;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        setTitle("订单管理");
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        titleRightImg.setVisibility(View.VISIBLE);
        titleRightImg.setImageResource(R.mipmap.search_icon);
    }

    @Override
    public void initData() {
        initTab();
        CommonPagerAdapter adapter = new CommonPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(titles.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(new PagerSwipeEvent(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });
    }

    private void initTab() {
        if (titles.isEmpty()) {
            titles.add("全部");
            titles.add("待审核");
            titles.add("待发货");
        }
        if (fragments.isEmpty()) {
            for (int i = 0; i < titles.size(); i++) {
                fragments.add(OrderListFragment.newInstance(i));
            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showPopUpwindowBg(OrderListShowPopBg ShowPopBg) {
        PopWindowUtils.setBackgroundBlack(popBg, ShowPopBg.isShow());
    }


    @OnClick(R.id.ll_title_right_view)
    void ll_title_right_view() {
        startActivity(OrderSearchActivity.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

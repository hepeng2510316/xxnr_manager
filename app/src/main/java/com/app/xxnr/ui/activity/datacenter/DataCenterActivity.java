package com.app.xxnr.ui.activity.datacenter;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.xxnr.R;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.CommonPagerAdapter;
import com.app.xxnr.ui.fragment.AgentFragment;
import com.app.xxnr.ui.fragment.DailyReportFragment;
import com.app.xxnr.ui.fragment.WeekReportFragment;
import com.app.xxnr.utils.KeyBoardUtils;
import com.app.xxnr.utils.SPUtils;
import com.app.xxnr.utils.ScreenUtils;
import com.app.xxnr.widget.UnSwipeViewPager;

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
public class DataCenterActivity extends BaseSwipeBackActivity {

    @BindView(R.id.title_right_img)
    ImageView titleRightImg;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    UnSwipeViewPager viewPager;
    @BindView(R.id.pop_bg)
    RelativeLayout popBg;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int initContentView() {
        return R.layout.activity_data_center;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        setTitle("数据中心");
        ButterKnife.bind(this);
        titleRightImg.setVisibility(View.VISIBLE);
        titleRightImg.setImageResource(R.mipmap.question_unpress);
    }

    @Override
    public void initData() {
        initTab();
        viewPager.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), titleList, fragments));
        tabs.setupWithViewPager(viewPager);//设置联动
        viewPager.setScanScroll(false);
        viewPager.setOffscreenPageLimit(titleList.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    //是否是第一次到经纪人tab 如果是的话展示引导
                    Boolean first_agent = (Boolean) SPUtils.get(instance, "first_agent", true);
                    if (first_agent != null && first_agent) {
                        popBg.setVisibility(View.VISIBLE);
                        popBg.setOnTouchListener((v, event) -> {
                            popBg.setVisibility(View.GONE);
                            return false;
                        });
                    }
                    SPUtils.put(instance, "first_agent", false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTab() {
        if (titleList.isEmpty()) {
            titleList.add("日报");
            titleList.add("周报");
            titleList.add("经纪人");
        }
        if (fragments.isEmpty()) {
            fragments.add(new DailyReportFragment());
            fragments.add(new WeekReportFragment());
            fragments.add(new AgentFragment());
        }
    }


    @OnClick(R.id.ll_title_right_view)
    void ll_title_right_view() {
        startActivity(DataCenterQuestionActivity.class);
    }

    @OnClick(R.id.tips_button)
    void tips_button() {
        popBg.setVisibility(View.GONE);
    }


    //返回监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (popBg.getVisibility() == View.VISIBLE) {
                popBg.setVisibility(View.GONE);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null) {
                if (ScreenUtils.isShouldHideInput(view, ev)) {
                    KeyBoardUtils.closeKeyboard((EditText) view, instance);
                    if (listener != null) {
                        listener.onTouchEvent(ev);
                    }
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }


    private OutTouchListener listener;


    public interface OutTouchListener {
        void onTouchEvent(MotionEvent event);
    }

    public void registerOutTouchListener(OutTouchListener listener) {
        this.listener = listener;
    }

    public void unRegisterOutTouchListener() {
        this.listener = null;
    }

}

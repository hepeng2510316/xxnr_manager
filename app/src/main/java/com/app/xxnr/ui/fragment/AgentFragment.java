package com.app.xxnr.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.app.xxnr.R;
import com.app.xxnr.event.AgentPageSelect;
import com.app.xxnr.ui.BaseFragment;
import com.app.xxnr.ui.adapter.CommonPagerAdapter;
import com.app.xxnr.widget.UnSwipeViewPager;
import com.app.xxnr.widget.transforms.FlipHorizontalTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/12 18:45
 * 邮箱：hepeng@xinxinnongren.com
 */
public class AgentFragment extends BaseFragment {

    @BindView(R.id.unSwipeViewPager)
    UnSwipeViewPager unSwipeViewPager;

    @Inject
    EventBus mBus;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int initContentView() {
        return R.layout.fragment_agent;
    }

    @Override
    public void initInjector() {
        mFragmentComponet.inject(this);
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mBus.register(this);
        showContent(true);
        unSwipeViewPager.setScanScroll(false);
    }

    @Override
    public void initData() {
        initTabsFragments();
        unSwipeViewPager.setAdapter(new CommonPagerAdapter(getChildFragmentManager(), null, fragments));
        unSwipeViewPager.setPageTransformer(true, new FlipHorizontalTransformer());
        unSwipeViewPager.setOffscreenPageLimit(fragments.size());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void agentPageSelect(AgentPageSelect select) {
        if (select.getPage() >= 0 && select.getPage() <= fragments.size() - 1){
            unSwipeViewPager.setCurrentItem(select.getPage());
        }
    }


    private void initTabsFragments() {
        if (fragments.isEmpty()) {
            fragments.add(new AgentYesterdayReportFragment());
            fragments.add(new AgentGroupReportFragment());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }
}

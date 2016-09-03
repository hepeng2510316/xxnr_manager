package com.app.xxnr.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.app.xxnr.R;
import com.app.xxnr.bean.OrderListResult;
import com.app.xxnr.contract.OrderListContract;
import com.app.xxnr.contract.OrderOperationContract;
import com.app.xxnr.event.OrderListShowPopBg;
import com.app.xxnr.event.OrderOperateEvent;
import com.app.xxnr.event.PagerSwipeEvent;
import com.app.xxnr.presenter.OrderListPresenter;
import com.app.xxnr.presenter.OrderOperationPresenter;
import com.app.xxnr.ui.BaseFragment;
import com.app.xxnr.ui.activity.OrderDetailActivity;
import com.app.xxnr.ui.adapter.OrderListAdapter;
import com.app.xxnr.widget.LoadMoreScrollListener;
import com.app.xxnr.widget.LoadingFooter;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/18 16:59
 * 邮箱：hepeng@xinxinnongren.com
 */
public class OrderListFragment extends BaseFragment implements
        OrderListContract.View,
        OrderOperationContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        OrderListAdapter.OnItemButtonListener,
        OrderListAdapter.OnItemClickListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    OrderListPresenter mPresenter;
    @Inject
    OrderOperationPresenter mOperationPresenter;
    @Inject
    OrderListAdapter adapter;
    @Inject
    LoadingFooter mLoadingFooter;
    @Inject
    EventBus mBus;


    private int position;
    private int type;

    public static OrderListFragment newInstance(int position) {
        OrderListFragment mFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        mFragment.setArguments(bundle);
        return mFragment;
    }


    @Override
    public void initInjector() {
        mFragmentComponet.inject(this);
    }

    @Override
    public int initContentView() {
        return R.layout.common_list;
    }

    @Override
    public void getBundle(Bundle bundle) {
        position = bundle.getInt("position");
    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        mOperationPresenter.attachView(this);
        mBus.register(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnItemButtonListener(this);
        adapter.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        mLoadingFooter.bindView(listView);
        listView.setOnScrollListener(new LoadMoreScrollListener(mLoadingFooter) {
            @Override
            public void loadMore() {
                mPresenter.onLoadMore();
            }
        });
    }

    @Override
    public void initData() {
        type = 0;
        if (position == 0) {
            type = 0;
        } else if (position == 1) {
            type = 6;
        } else if (position == 2) {
            type = 5;
        }
        if (type == 6) {
            adapter.setComment(true);
        } else {
            adapter.setComment(false);
        }
        mPresenter.onLoad(type);
    }


    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showContent(true);
    }

    @Override
    public void renderList(List<OrderListResult.DatasBean.ItemsBean> itemsBean) {
        mLoadingFooter.setState(LoadingFooter.State.Idle);
        adapter.bindData(itemsBean);
    }

    @Override
    public void onRefreshCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void allLoaded() {
        if (adapter.getCount() > 10) {
            mLoadingFooter.setState(LoadingFooter.State.TheEnd);
        } else {
            mLoadingFooter.setState(LoadingFooter.State.Deal);
        }
    }

    @Override
    public void onError() {
        mLoadingFooter.setState(LoadingFooter.State.Deal);
        showError(true);
    }

    @Override
    public void onEmpty() {
        adapter.clear();
        mLoadingFooter.setState(LoadingFooter.State.Deal);
        setEmptyText("暂无订单");
        showEmpty(true);
    }


    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void OnItemClick(OrderListResult.DatasBean.ItemsBean itemsBean) {
        OrderDetailActivity.startActivity(activity, itemsBean);
    }

    @Override
    public void onItemButtonClick(View view, boolean isComment, OrderListResult.DatasBean.ItemsBean itemsBean) {
        if (isComment) {
            mOperationPresenter.checkOffline(view, itemsBean);
        } else {
            mOperationPresenter.deliveryState(view, itemsBean);
        }
    }

    @Override
    public void showDialogBg(boolean show) {
        EventBus.getDefault().post(new OrderListShowPopBg(show));
    }

    @Override
    public void operateSuccess() {
        mBus.post(new OrderOperateEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orderOperate(OrderOperateEvent event) {
        mPresenter.onReload();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pagerSwipe(PagerSwipeEvent event) {
        if (event.getPage()==position){
            mPresenter.onLoad(type);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mOperationPresenter.detachView();
        mBus.unregister(this);
    }


}

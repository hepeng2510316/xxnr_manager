package com.app.xxnr.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.app.xxnr.R;
import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.event.UpdateCustomer;
import com.app.xxnr.ui.BaseFragment;
import com.app.xxnr.ui.activity.CustomerDetailActivity;
import com.app.xxnr.ui.adapter.CustomerListAdapter;
import com.app.xxnr.contract.CustomerListContract;
import com.app.xxnr.presenter.CustomerListPresenter;
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
 * 作者：何鹏 on 2016/5/3 17:53
 * 邮箱：hepeng@xinxinnongren.com
 */
public class CustomerManageFragment extends BaseFragment implements CustomerListContract.View,
        CustomerListAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    CustomerListPresenter mPresenter;
    @Inject
    CustomerListAdapter adapter;
    @Inject
    LoadingFooter mLoadingFooter;
    @Inject
    EventBus mBus;


    int position;


    public static CustomerManageFragment newInstance(int position) {
        CustomerManageFragment mFragment = new CustomerManageFragment();
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
        mBus.register(this);
        mPresenter.attachView(this);

        swipeRefreshLayout.setOnRefreshListener(this);
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
        int query = 0;
        if (position == 0) {
            query = 0;
        } else if (position == 1) {
            query = 3;
        }
        mPresenter.onLoad(query);
    }

    @Override
    public void onReloadClicked() {
        mPresenter.onReload();
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
    public void renderList(List<CustomerListResult.Users.ItemsBean> itemsBean) {
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
        setEmptyText("暂无客户");
        showEmpty(true);
    }

    @Override
    public void onListClick(CustomerListResult.Users.ItemsBean itemsBean) {
        CustomerDetailActivity.startActivity(activity, itemsBean);
    }


    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    /**
     * 修改用户信息刷新列表页
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCustomer(UpdateCustomer event) {
        mPresenter.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBus.unregister(this);
    }
}

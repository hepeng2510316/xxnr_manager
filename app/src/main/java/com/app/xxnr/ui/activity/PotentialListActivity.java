package com.app.xxnr.ui.activity;


import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.xxnr.R;
import com.app.xxnr.bean.PotentialListResult;
import com.app.xxnr.contract.PotentialListContract;
import com.app.xxnr.presenter.PotentialListPresenter;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.PotentialListAdapter;
import com.app.xxnr.widget.LoadMoreScrollListener;
import com.app.xxnr.widget.LoadingFooter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：潜在客户
 * 作者：何鹏 on 2016/5/4 16:55
 * 邮箱：hepeng@xinxinnongren.com
 */
public class PotentialListActivity extends BaseSwipeBackActivity implements PotentialListContract.View, PotentialListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_right_img)
    ImageView titleRightImg;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Inject
    PotentialListPresenter mPresenter;
    @Inject
    PotentialListAdapter mAdapter;
    @Inject
    LoadingFooter mLoadingFooter;

    @Override
    public int initContentView() {
        return R.layout.activity_common_list;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        setTitle("潜在客户");
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        titleRightImg.setVisibility(View.VISIBLE);
        titleRightImg.setImageResource(R.mipmap.search_icon);

        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mLoadingFooter.bindView(listView);
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(new LoadMoreScrollListener(mLoadingFooter) {
            @Override
            public void loadMore() {
                mPresenter.onLoadMore();
            }
        });
    }


    @OnClick(R.id.ll_title_right_view)
    void ll_title_right_view() {
        startActivity(PotentialSearchActivity.class);
    }

    @Override
    public void initData() {
        mPresenter.onLoad();
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        dismissProgress();
    }

    @Override
    public void renderList(List<PotentialListResult.PotentialCustomersBean> itemsBean) {
        mLoadingFooter.setState(LoadingFooter.State.Idle);
        mAdapter.bindData(itemsBean);
    }


    @Override
    public void allLoaded() {
        if (mAdapter.getCount()>10){
            mLoadingFooter.setState(LoadingFooter.State.TheEnd);
        }else {
            mLoadingFooter.setState(LoadingFooter.State.Deal);
        }
    }

    @Override
    public void onError() {
        mLoadingFooter.setState(LoadingFooter.State.Deal);
    }

    @Override
    public void onEmpty() {
        mAdapter.clear();
        mLoadingFooter.setState(LoadingFooter.State.Deal);
    }

    @Override
    public void onListClick(PotentialListResult.PotentialCustomersBean itemsBean) {
        PotentialDetailActivity.startActivity(this,itemsBean);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onRefreshCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}

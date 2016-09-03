package com.app.xxnr.presenter;


import com.app.xxnr.api.ApiService;
import com.app.xxnr.bean.OrderListResult;
import com.app.xxnr.contract.OrderListContract;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/18 17:09
 * 邮箱：hepeng@xinxinnongren.com
 */
public class OrderListPresenter extends BasicPresenter<OrderListContract.View>implements OrderListContract.Presenter {



    private int page = 1;
    private int type;
    private String search;
    private List<OrderListResult.DatasBean.ItemsBean> lists = new ArrayList<>();
    private boolean hasNextPage = true;
    private Subscription subscription;


    @Inject
    public OrderListPresenter(ApiService api) {
        super(api);
    }

    @Override
    public void onLoad(int type) {
        mView.showLoading();
        page = 1;
        this.type = type;
        getData();
    }

    @Override
    public void onSearch(String search, boolean isShowProgress) {
        if (isShowProgress) {
            mView.showLoading();
        }
        if (!StringUtil.checkStr(search)) {
            mView.onRefreshCompleted();
            mView.hideLoading();
            return;
        }

        page = 1;
        this.search = search;
        this.type = 0;
        getData();
    }


    private void getData() {
        subscription = api.getOrderList(page, type, search)
                .subscribe(orderListResult -> {
                    mView.hideLoading();
                    mView.onRefreshCompleted();
                    if (orderListResult.datas != null) {
                        page = orderListResult.datas.page;
                        hasNextPage = orderListResult.datas.page < orderListResult.datas.pages;
                        handleLists(orderListResult.datas.items);
                    }
                    if (!hasNextPage) {
                        mView.allLoaded();
                    }
                }, throwable -> {
                    handleError();
                });
    }


    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    @Override
    public void onReload() {
        onLoad(type);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }


    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void handleLists(List<OrderListResult.DatasBean.ItemsBean> beanList) {
        if (page == 1) {
            lists.clear();
        }
        if (beanList != null && !beanList.isEmpty()) {
            lists.addAll(beanList);
            mView.renderList(lists);
        } else {
            if (lists.isEmpty()) {
                mView.onEmpty();
            } else {
                if (page != 1) {
                    page--;
                    T.showShort("没有更多了");
                }
            }
        }
    }

    private void handleError() {
        mView.hideLoading();
        mView.onRefreshCompleted();
        if (lists.isEmpty()) {
            mView.onError();
        }
        if (page != 1) {
            page--;
        }
    }


}

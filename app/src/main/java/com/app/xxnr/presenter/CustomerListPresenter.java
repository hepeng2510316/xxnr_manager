package com.app.xxnr.presenter;

import com.app.xxnr.api.ApiService;
import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.contract.CustomerListContract;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/10 17:55
 * 邮箱：hepeng@xinxinnongren.com
 */
public class CustomerListPresenter extends BasicPresenter<CustomerListContract.View> implements CustomerListContract.Presenter {
    private Subscription subscription;

    private int page = 1;
    private int query;
    private String search;

    private List<CustomerListResult.Users.ItemsBean> lists = new ArrayList<>();
    private boolean hasNextPage = true;


    @Inject
    public CustomerListPresenter(ApiService api) {
        super(api);
    }

    @Override
    public void onLoad(int query) {
        mView.showLoading();
        page = 1;
        this.search = null;
        this.query = query;
        getUsers();
    }

    @Override
    public void onSearch(String search, boolean isShowProgress) {
        if (isShowProgress) {
            mView.showLoading();
        }
        this.query = 0;
        this.search = search;
        if (!StringUtil.checkStr(search)) {
            mView.hideLoading();
            mView.onRefreshCompleted();
            return;
        }
        page = 1;
        getUsers();
    }

    private void getUsers() {

        subscription = api.getUsers(page, query, search)
                .subscribe(customerListResult -> {
                    mView.hideLoading();
                    mView.onRefreshCompleted();
                    if (customerListResult.users != null) {
                        page = customerListResult.users.page;
                        hasNextPage = customerListResult.users.page < customerListResult.users.pages;
                        handleLists(customerListResult.users.items);
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
        getUsers();
    }

    @Override
    public void onReload() {
        onLoad(query);
    }

    @Override
    public void onLoadMore() {
        page++;
        getUsers();
    }



    private void handleLists(List<CustomerListResult.Users.ItemsBean> beanList) {
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


    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


}

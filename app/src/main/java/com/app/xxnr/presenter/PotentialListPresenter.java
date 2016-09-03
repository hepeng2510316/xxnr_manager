package com.app.xxnr.presenter;



import com.app.xxnr.api.ApiService;
import com.app.xxnr.bean.PotentialListResult;
import com.app.xxnr.contract.PotentialListContract;
import com.app.xxnr.injector.scope.PerActivity;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/23 14:58
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerActivity
public class PotentialListPresenter extends BasicPresenter<PotentialListContract.View> implements PotentialListContract.Presenter {
    private Subscription subscription;
    private int page = 1;
    private String search;
    private List<PotentialListResult.PotentialCustomersBean> lists = new ArrayList<>();
    private boolean hasNextPage = true;


    @Inject
    public PotentialListPresenter(ApiService api) {
        super(api);
    }

    @Override
    public void onSearch(String Search, boolean isShowProgress) {
        this.search = Search;
        if (isShowProgress) {
            mView.showLoading();
        }
        if (!StringUtil.checkStr(search)) {
            mView.hideLoading();
            mView.onRefreshCompleted();
            return;
        }
        page = 1;
        getData();
    }

    @Override
    public void onLoad() {
        page = 1;
        search = null;
        mView.showLoading();
        getData();
    }

    private void getData() {

        subscription = api.getPotentList(page, search)
                .subscribe(ListResult -> {
                    mView.hideLoading();
                    mView.onRefreshCompleted();
                    if (ListResult != null) {
                        hasNextPage = page < ListResult.pageCount;
                        handleLists(ListResult.potentialCustomers);
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
    public void onLoadMore() {
        page++;
        getData();
    }


    private void handleLists(List<PotentialListResult.PotentialCustomersBean> beanList) {
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

package com.app.xxnr.contract;

import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/10 17:52
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface CustomerListContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void renderList(List<CustomerListResult.Users.ItemsBean> itemsBean);

        void onRefreshCompleted();

        void allLoaded();

        void onError();

        void onEmpty();
    }

    interface Presenter extends BasePresenter<View> {

        void onLoad(int query);

        void onSearch(String Search, boolean isShowProgress);

        void onRefresh();

        void onReload();

        void onLoadMore();

    }

}

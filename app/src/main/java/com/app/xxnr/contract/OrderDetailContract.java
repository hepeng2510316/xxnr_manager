package com.app.xxnr.contract;

import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.bean.OrderDetailResult;
import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/24 17:44
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface OrderDetailContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void renderList(OrderDetailResult result);

    }

    interface Presenter extends BasePresenter<View> {
        void onLoad(String orderId);

        void reLoad();

    }

}

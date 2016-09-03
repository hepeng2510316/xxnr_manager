package com.app.xxnr.contract;

import com.app.xxnr.bean.PotentialDetailResult;
import com.app.xxnr.bean.PotentialListResult;
import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

/**
 * 类描述：
 * 作者：何鹏 on 2016/5/11 17:52
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface PotentialDetailContract {

    interface View extends BaseView {
        void loadSuccess(PotentialDetailResult potentialDetailResult);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter<View> {
        void load(String customerId);
    }
}
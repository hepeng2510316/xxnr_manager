package com.app.xxnr.contract;

import com.app.xxnr.bean.CustomerDetailResult;
import com.app.xxnr.bean.RscInfoResult;
import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/10 17:52
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface CustomerDetailContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void onSuccessDetail(CustomerDetailResult result);

        void onSuccessRscInfo(RscInfoResult result);

        void onSuccessChange();


    }

    interface Presenter extends BasePresenter<View> {

        void onLoadRscInfo(String id);

        void onLoadDetail(String id);

        void changeCustomer(String id, List<String> list);

    }

}

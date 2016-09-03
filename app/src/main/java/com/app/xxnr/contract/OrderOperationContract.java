package com.app.xxnr.contract;

import com.app.xxnr.bean.OrderListResult;
import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/24 13:11
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface OrderOperationContract {

    interface View extends BaseView {

        void showDialogBg(boolean show);

        void operateSuccess();

    }

    interface Presenter extends BasePresenter<View> {
        void checkOffline(android.view.View parent, OrderListResult.DatasBean.ItemsBean itemsBean);
        void deliveryState(android.view.View parent, OrderListResult.DatasBean.ItemsBean itemsBean);
    }

}

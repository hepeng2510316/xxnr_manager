package com.app.xxnr.presenter;


import com.app.xxnr.api.ApiService;
import com.app.xxnr.contract.OrderDetailContract;
import com.app.xxnr.injector.scope.PerActivity;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.ui.activity.OrderDetailActivity;
import com.app.xxnr.utils.StringUtil;
import com.trello.rxlifecycle.ActivityEvent;

import javax.inject.Inject;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/24 17:49
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerActivity
public class OrderDetailPresenter extends BasicPresenter<OrderDetailContract.View> implements OrderDetailContract.Presenter {
    private String orderId;

    @Inject
    public OrderDetailPresenter(ApiService api) {
        super(api);
    }

    @Override
    public void onLoad(String orderId) {
        if (!StringUtil.checkStr(orderId)) {
            return;
        }
        this.orderId=orderId;
        mView.showLoading();
        getData();
    }

    private void getData(){
        api.getOrderDetail(orderId)
                .compose(((OrderDetailActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(result -> {
                    mView.hideLoading();
                    mView.renderList(result);
                }, throwable -> {
                    mView.hideLoading();
                });
    }

    @Override
    public void reLoad() {
        getData();
    }
}

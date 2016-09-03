package com.app.xxnr.presenter;


import com.app.xxnr.api.ApiService;
import com.app.xxnr.contract.CustomerDetailContract;
import com.app.xxnr.injector.scope.PerActivity;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.ui.activity.CustomerDetailActivity;
import com.app.xxnr.utils.StringUtil;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import javax.inject.Inject;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/18 11:31
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerActivity
public class CustomerDetailPresenter extends BasicPresenter<CustomerDetailContract.View> implements CustomerDetailContract.Presenter {

    @Inject
    public CustomerDetailPresenter(ApiService api) {
        super(api);
    }

    @Override
    public void onLoadRscInfo(String id) {
        if (!StringUtil.checkStr(id)) {
            return;
        }
        api.getRscInfo(id)
                .compose(((CustomerDetailActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .doOnSubscribe(() -> mView.showLoading())
                .subscribe(rscInfoResult -> {
                    mView.hideLoading();
                    mView.onSuccessRscInfo(rscInfoResult);
                }, throwable -> {
                    mView.hideLoading();
                });
    }

    @Override
    public void onLoadDetail(String id) {
        api.getCustomerDetail(id)
                .compose(((CustomerDetailActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .doOnSubscribe(() -> mView.showLoading())
                .subscribe(customerDetailResult -> {
                    mView.hideLoading();
                    mView.onSuccessDetail(customerDetailResult);
                }, throwable -> {
                    mView.hideLoading();
                });
    }

    @Override
    public void changeCustomer(String id, List<String> list) {
        api.changeCustomer(id, list)
                .compose(((CustomerDetailActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .doOnSubscribe(() -> mView.showLoading())
                .subscribe(baseResult -> {
                    mView.hideLoading();
                    mView.onSuccessChange();
                }, throwable -> {
                    mView.hideLoading();
                });

    }

}

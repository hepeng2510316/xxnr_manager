package com.app.xxnr.presenter;



import com.app.xxnr.api.ApiService;
import com.app.xxnr.contract.PotentialDetailContract;
import com.app.xxnr.injector.scope.PerActivity;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.ui.activity.PotentialDetailActivity;
import com.app.xxnr.utils.StringUtil;
import com.trello.rxlifecycle.ActivityEvent;


import javax.inject.Inject;



/**
 * 类描述：
 * 作者：何鹏 on 2016/8/23 14:58
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerActivity
public class PotentialDetailPresenter extends BasicPresenter<PotentialDetailContract.View>implements PotentialDetailContract.Presenter {

    @Inject
    public PotentialDetailPresenter(ApiService api) {
        super(api);
    }

    @Override
    public void load(String customerId) {
        if (!StringUtil.checkStr(customerId)) {
            return;
        }
        mView.showLoading();
        api.getPotentDetail(customerId)
                .compose(((PotentialDetailActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(potentialListResult -> {
                    mView.hideLoading();
                    mView.loadSuccess(potentialListResult);
                }, throwable -> {
                    mView.hideLoading();
                });
    }

}

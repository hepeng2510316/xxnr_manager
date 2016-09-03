package com.app.xxnr.presenter;

import com.app.xxnr.api.ApiService;
import com.app.xxnr.contract.DailyReportContract;
import com.app.xxnr.injector.scope.PerFragment;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.ui.fragment.DailyReportFragment;
import com.trello.rxlifecycle.FragmentEvent;

import javax.inject.Inject;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 17:05
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerFragment
public class DailyReportPresenter extends BasicPresenter<DailyReportContract.View> implements DailyReportContract.Presenter {

    @Inject
    public DailyReportPresenter(ApiService api) {
        super(api);
    }

    @Override
    public void getDailyReport(String date) {
        mView.showLoading();
        api.getDailyReport(date)
                .compose(((DailyReportFragment) mView).bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(result -> {
                    mView.renderDailyReport(result);
                    mView.hideLoading();
                }, throwable -> {
                    mView.hideLoading();
                });
    }

    @Override
    public void getStatistic() {
        api.getStatistic()
                .compose(((DailyReportFragment) mView).bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(result -> {
                    mView.renderStatistic(result);
                }, throwable -> {});
    }
}

package com.app.xxnr.presenter;

import android.support.annotation.NonNull;

import com.app.xxnr.UserManager;
import com.app.xxnr.bean.dbbeans.DatasBean;
import com.app.xxnr.injector.scope.PerActivity;
import com.app.xxnr.contract.MainContract;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.ui.activity.CustomerDetailActivity;
import com.app.xxnr.ui.activity.MainActivity;
import com.trello.rxlifecycle.ActivityEvent;


import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/3 17:55
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerActivity
public class MainPresenter extends BasicPresenter<MainContract.View> implements MainContract.Presenter {

    private UserManager userManager;

    @Inject
    public MainPresenter(UserManager userManager) {
        super(null);
        this.userManager = userManager;
    }

    @Override
    public void getUserAccount() {

        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        DatasBean bean = userManager.getUserData();
                        subscriber.onNext(bean.account);
                    }
                })
                .compose(((MainActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    mView.setUserAccount(s);
                }, Throwable::printStackTrace);
    }

    @Override
    public void getRoleList() {
        Observable
                .create(new Observable.OnSubscribe<List<String>>() {
                    @Override
                    public void call(Subscriber<? super List<String>> subscriber) {
                        List<String> userRole = userManager.getRole();
                        subscriber.onNext(userRole);
                    }
                })
                .compose(((MainActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    mView.setRoleList(strings);
                }, Throwable::printStackTrace);

    }

    @Override
    public void clearUser() {
        new Thread(() -> {
            userManager.clearUserInfo();
        }).start();
    }

}

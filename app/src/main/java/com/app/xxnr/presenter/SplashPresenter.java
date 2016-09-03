package com.app.xxnr.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.app.xxnr.UserManager;
import com.app.xxnr.contract.SplashContract;
import com.app.xxnr.injector.scope.PerActivity;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.ui.activity.SplashActivity;
import com.app.xxnr.utils.SPUtils;
import com.app.xxnr.utils.StringUtil;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/3 17:55
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerActivity
public class SplashPresenter extends BasicPresenter<SplashContract.View>implements SplashContract.Presenter {


    private Context context;
    private UserManager userManager;

    private final long DAY_TIME = 24 * 60 * 60 * 1000;


    @Inject
    public SplashPresenter(Context context, UserManager userManager) {
        super(null);
        this.context = context;
        this.userManager = userManager;
    }


    @Override
    public void isNeedLogin() {
            Observable
                .create((Observable.OnSubscribe<Boolean>) subscriber -> {
                    long last_up_date = (Long) SPUtils.get(context, "login_time", 0L);
                    long currentTimeMillis = System.currentTimeMillis();
                    //每24小时 清除登录信息
                    if ((currentTimeMillis - last_up_date) > (DAY_TIME)) {
                        userManager.clearUserInfo();
                    }
                    subscriber.onNext(!StringUtil.checkStr(userManager.getUid()));
                })
                .compose(((SplashActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(2, TimeUnit.SECONDS)
                .subscribe(aBoolean -> {
                    mView.isNeedLogin(aBoolean);
                }, Throwable::printStackTrace);

    }

}

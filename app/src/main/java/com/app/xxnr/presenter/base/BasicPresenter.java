package com.app.xxnr.presenter.base;


import android.support.annotation.NonNull;

import com.app.xxnr.api.ApiService;
import com.app.xxnr.ui.BaseView;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 17:09
 * 邮箱：hepeng@xinxinnongren.com
 *
 * @param <T> BaseView
 */
public abstract class BasicPresenter<T extends BaseView> {
    protected ApiService api;
    protected T mView;

    public BasicPresenter(ApiService api) {
        this.api = api;
    }

    public void attachView(@NonNull T t) {
        this.mView = t;
    }

    public void detachView() {
        mView = null;
    }
}

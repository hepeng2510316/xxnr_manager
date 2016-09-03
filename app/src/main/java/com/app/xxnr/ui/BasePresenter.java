package com.app.xxnr.ui;


import android.support.annotation.NonNull;

import com.app.xxnr.ui.BaseView;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/9 17:53
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface BasePresenter<T extends BaseView> {

    void attachView(@NonNull T view);

    void detachView();
}
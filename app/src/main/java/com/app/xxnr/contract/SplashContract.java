package com.app.xxnr.contract;

import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

/**
 * 类描述：
 * 作者：何鹏 on 2016/5/11 17:52
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface SplashContract {


    interface View extends BaseView {
        void isNeedLogin(boolean isNeedLogin);
    }

    interface Presenter extends BasePresenter<View> {
        void isNeedLogin();
    }
}
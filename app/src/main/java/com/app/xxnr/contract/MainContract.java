package com.app.xxnr.contract;

import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/5/11 17:52
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface MainContract {


    interface View extends BaseView {
        void setUserAccount(String account);
        void setRoleList(List<String> roleList );
    }

    interface Presenter extends BasePresenter<View> {
        void getUserAccount();
        void getRoleList();
        void clearUser();
    }
}
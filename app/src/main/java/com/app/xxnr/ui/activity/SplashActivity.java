package com.app.xxnr.ui.activity;

import com.app.xxnr.R;
import com.app.xxnr.ui.BaseActivity;
import com.app.xxnr.contract.SplashContract;
import com.app.xxnr.presenter.SplashPresenter;

import javax.inject.Inject;

/**
 * 类描述：启动页
 * 作者：何鹏 on 2016/8/9 17:50
 * 邮箱：hepeng@xinxinnongren.com
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashPresenter mPresenter;

    @Override
    public int initContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {

    }

    @Override
    public void initData() {
        mPresenter.attachView(this);
        //is need login?
        mPresenter.isNeedLogin();
    }

    @Override
    public void isNeedLogin(boolean isNeedLogin) {
        if (isNeedLogin) {
            startActivity(LoginActivity.class);
        } else {
            startActivity(MainActivity.class);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}

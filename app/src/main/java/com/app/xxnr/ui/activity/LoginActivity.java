package com.app.xxnr.ui.activity;

import android.widget.ScrollView;

import com.app.xxnr.R;
import com.app.xxnr.ui.BaseActivity;
import com.app.xxnr.contract.LoginContract;
import com.app.xxnr.presenter.LoginPresenter;
import com.app.xxnr.widget.ClearEditText;
import com.app.xxnr.widget.KeyboardListenRelativeLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：登录
 * 作者：何鹏 on 2016/8/10 17:49
 * 邮箱：hepeng@xinxinnongren.com
 */
public class LoginActivity extends BaseActivity implements LoginContract.View, KeyboardListenRelativeLayout.IOnKeyboardStateChangedListener {
    @BindView(R.id.login_name_et)
    ClearEditText login_name_et;
    @BindView(R.id.login_pass_et)
    ClearEditText login_pass_et;
    @BindView(R.id.login_layout)
    ScrollView login_layout;
    @BindView(R.id.rootView)
    KeyboardListenRelativeLayout rootView;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        loginPresenter.attachView(this);
        rootView.setBackgroundResource(R.mipmap.login_bg_img);
        rootView.setOnKeyboardStateChangedListener(this);

        rootView.postDelayed(() -> {
            login_layout.fullScroll(ScrollView.FOCUS_DOWN);
            login_name_et.requestFocus();
        }, 300);
    }

    @Override
    public void initData() {

    }

    /**
     * 登录
     */
    @OnClick(R.id.login_sure_bt)
    void login() {
        String mUserName = login_name_et.getText().toString().trim();
        String mPassword = login_pass_et.getText().toString().trim();
        loginPresenter.loginAndSave(mUserName, mPassword);
    }

    @Override
    public void loginSuccess() {
        showToast("登录成功");
        startActivity(MainActivity.class); //去主页
        finish();
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        dismissProgress();
    }

    @Override
    public void onKeyboardStateChanged(int state) {
        if (state == KeyboardListenRelativeLayout.KEYBOARD_STATE_SHOW) {
            rootView.postDelayed(() -> login_layout.fullScroll(ScrollView.FOCUS_DOWN), 300);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }


}

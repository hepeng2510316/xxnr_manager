package com.app.xxnr.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.xxnr.App;
import com.app.xxnr.AppManager;
import com.app.xxnr.ClassFilter;
import com.app.xxnr.R;
import com.app.xxnr.injector.components.ActivityComponent;
import com.app.xxnr.injector.components.AppComponent;
import com.app.xxnr.injector.components.DaggerActivityComponent;
import com.app.xxnr.injector.module.ActivityModule;
import com.app.xxnr.utils.FastDoubleClickUtils;
import com.app.xxnr.utils.RLog;
import com.app.xxnr.widget.CustomProgressDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * 类描述：activity的超类
 * 作者：何鹏 on 2016/8/4 17:53
 * 邮箱：hepeng@xinxinnongren.com
 */
public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {
    public String TAG = this.getClass().getSimpleName();

    protected Context instance = this;

    protected LinearLayout titleLeftView;

    private TextView tv_title;

    private Dialog progressDialog;

    private boolean titleLoaded = false; // 标题是否加载成功

    private Toast toast;

    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        // 屏幕竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(initContentView());
        //load title
        loadTitle();
        //设置状态栏颜色状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!ClassFilter.getUnSetStatusBarClasses().contains(getClass().getSimpleName())) {
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
                if (titleLoaded) {
                    tintManager.setTintColor(getResources().getColor(R.color.green));
                } else {
                    tintManager.setTintColor(getResources().getColor(R.color.black));
                }
            }
        }
        initAppComponent();
        initInjector();
        initUiAndListener();
        initData();
    }



    /**
     * 设置view
     */
    public abstract int initContentView();

    /**
     * 注入Injector
     */
    public abstract void initInjector();

    /**
     * init UI && Listener
     */
    public abstract void initUiAndListener();
    /**
     * init Data
     */
    public abstract void initData();

    /**
     * init ActivityModule
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * init AppComponent
     */
    private void initAppComponent(){
        mActivityComponent = DaggerActivityComponent.builder().activityModule(getActivityModule()).appComponent(getAppComponent()).build();
    }
    /**
     * get AppComponent
     */
    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    /**
     * 左上角返回
     */
    @Override
    public final void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_left_view:
                finish();
                break;
        }
    }

    private void loadTitle() {
        titleLeftView = (LinearLayout) findViewById(R.id.ll_title_left_view);
        tv_title = (TextView) findViewById(R.id.title_name_text);
        RelativeLayout titleView = (RelativeLayout) findViewById(R.id.titleView);
        if (titleView != null) {
            titleLeftView.setOnClickListener(this);
            titleLoaded = true;
            RLog.i(TAG, "titleView loaded.");
        }
    }

    // ----------------------------公共方法区---------------------------------//

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (titleLoaded) {
            tv_title.setText(title);
        }
    }

    /**
     * 隐藏左控件
     */
    protected void hideLeft() {
        if (titleLoaded) {
            titleLeftView.setVisibility(View.GONE);
        }
    }


    /**
     * 展示提示
     *
     * @param msg
     */
    protected void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }


    /**
     * 展示加载Progress
     */
    protected void showProgress() {
        showProgress("加载中...");
    }

    /**
     * 展示加载Progress
     *
     * @param msg
     */
    protected void showProgress(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            // activity maybe is destroy
            try {
                progressDialog.dismiss();
                progressDialog = null;
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
        }
        progressDialog = CustomProgressDialog.createLoadingDialog(this, msg, Color.parseColor("#000000"));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        // activity maybe is destroy
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }


    /**
     * 取消加载Progress
     */
    protected void dismissProgress() {
        // activity maybe is destroy
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转一个界面不传递数据
     *
     * @param clazz 要启动的Activity
     */
    protected void startActivity(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }


    /**
     * 判断触摸时间派发间隔
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (FastDoubleClickUtils.isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}

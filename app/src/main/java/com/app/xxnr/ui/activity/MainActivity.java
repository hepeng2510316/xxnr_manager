package com.app.xxnr.ui.activity;


import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.xxnr.AppManager;
import com.app.xxnr.R;
import com.app.xxnr.UserManager;
import com.app.xxnr.event.TokenErrorEvent;
import com.app.xxnr.ui.BaseActivity;
import com.app.xxnr.contract.MainContract;
import com.app.xxnr.presenter.MainPresenter;
import com.app.xxnr.ui.activity.datacenter.DataCenterActivity;
import com.app.xxnr.utils.RLog;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.widget.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 类描述：主页
 * 作者：何鹏 on 2016/8/10 17:49
 * 邮箱：hepeng@xinxinnongren.com
 */
public class MainActivity extends BaseActivity implements MainContract.View {


    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.main_user_ll)
    LinearLayout mainUserLl;
    @BindView(R.id.main_order_ll)
    LinearLayout mainOrderLl;
    @BindView(R.id.main_dashboard)
    LinearLayout mainDashboard;
    @BindView(R.id.customer_manage_ll)
    LinearLayout customerManageLl;
    @BindView(R.id.potential_customer_ll)
    LinearLayout potentialCustomerLl;
    @BindView(R.id.order_manage_ll)
    LinearLayout orderManageLl;
    @BindView(R.id.data_center_ll)
    LinearLayout dataCenterLl;

    @Inject
    MainPresenter presenter;
    @Inject
    EventBus mBus;
    @Inject
    UserManager userManager;

    private long backPressTime;

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }


    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        setTitle("首页");
        hideLeft();
        ButterKnife.bind(this);
        mBus.register(this);
        presenter.attachView(this);

        mainUserLl.setVisibility(View.GONE);
        mainOrderLl.setVisibility(View.GONE);
        mainDashboard.setVisibility(View.GONE);

        customerManageLl.setEnabled(false);
        potentialCustomerLl.setEnabled(false);
        orderManageLl.setEnabled(false);
        dataCenterLl.setEnabled(false);


    }

    @Override
    public void initData() {
        presenter.getUserAccount();
        presenter.getRoleList();
    }

    @Override
    public void setUserAccount(String account) {
        userName.setText(StringUtil.checkStr(account) ? account : "");
    }

    @Override
    public void setRoleList(List<String> roleList) {
        if (roleList != null) {
            if (roleList.contains("users")) {
                mainUserLl.setVisibility(View.VISIBLE);
                customerManageLl.setEnabled(true);
                potentialCustomerLl.setEnabled(true);
            }
            if (roleList.contains("orders")) {
                mainOrderLl.setVisibility(View.VISIBLE);
                orderManageLl.setEnabled(true);
            }
            if (roleList.contains("dashboard")) {
                mainDashboard.setVisibility(View.VISIBLE);
                dataCenterLl.setEnabled(true);
            }
        }
    }


    @OnClick(R.id.login_out)
    void login_out() {
        CustomDialog.Builder builder = new CustomDialog.Builder(instance);
        builder.setMessage("确定要退出新新农人吗？")
                .setPositiveButton("确定",
                        (dialog, which) -> {
                            dialog.dismiss();
                            presenter.clearUser();
                            showToast("您已退出登录");
                            startActivity(LoginActivity.class);
                            finish();
                        })
                .setNegativeButton("取消",
                        (dialog, which) -> {
                            dialog.dismiss();
                        })
                .create()
                .show();
    }


    //客户管理
    @OnClick(R.id.customer_manage_ll)
    void customer_manage_ll() {
        startActivity(CustomerManageActivity.class);
    }

    //潜在客户
    @OnClick(R.id.potential_customer_ll)
    void potential_customer_ll() {
        startActivity(PotentialListActivity.class);
    }

    //订单管理
    @OnClick(R.id.order_manage_ll)
    void order_manage_ll() {
        startActivity(OrderManagerActivity.class);
    }

    //数据中心
    @OnClick(R.id.data_center_ll)
    void data_center_ll() {
        startActivity(DataCenterActivity.class);
    }

    /**
     * token 验证错误 重新登录
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tokenError(TokenErrorEvent event){
        userManager.clearUserInfo();
        startActivity(LoginActivity.class);
        finish();
    }


    // 再按一次退出程序
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            long current = System.currentTimeMillis();
            if (current - backPressTime > 2000) {
                backPressTime = current;
                showToast("再次按返回键退出程序");
            } else {
                RLog.i("MainActivity", "Exiting...");
                AppManager.getAppManager().AppExit(this);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        mBus.unregister(this);
    }



}

package com.app.xxnr.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.app.xxnr.UserManager;
import com.app.xxnr.api.ApiService;
import com.app.xxnr.bean.dbbeans.DatasBean;
import com.app.xxnr.injector.scope.PerActivity;
import com.app.xxnr.contract.LoginContract;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.ui.activity.CustomerDetailActivity;
import com.app.xxnr.ui.activity.LoginActivity;
import com.app.xxnr.utils.RSAUtil;
import com.app.xxnr.utils.SPUtils;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import javax.inject.Inject;

import rx.schedulers.Schedulers;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/3 17:55
 * 邮箱：hepeng@xinxinnongren.com
 */
@PerActivity
public class LoginPresenter extends BasicPresenter<LoginContract.View> implements LoginContract.Presenter {

    private UserManager userManager;
    private Context context;


    @Inject
    public LoginPresenter(UserManager userManager, ApiService api, Context context) {
        super(api);
        this.userManager = userManager;
        this.context = context;
    }


    @Override
    public void loginAndSave(String phone, String passWord) {
        if (!StringUtil.checkStr(phone)) {
            T.showShort("请输入用户名");
            return;
        }
        if (!StringUtil.checkStr(passWord)) {
            T.showShort("请输入密码");
            return;
        }
        api.getPublicKey()  //请求公钥
                .compose(((LoginActivity) mView).bindUntilEvent(ActivityEvent.DESTROY))
                .doOnSubscribe(() -> mView.showLoading())
                .flatMap(publicKeyResult -> {   //请求登陆
                    try {
                        return api.login(phone, RSAUtil.encryptByPublicKey(passWord,
                                RSAUtil.generatePublicKey(publicKeyResult.public_key)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .map(result -> { //save
                    //token
                    if (StringUtil.checkStr(result.token)) {
                        userManager.saveToken(result.token);
                    }
                    DatasBean datasBean = result.datas;
                    if (datasBean != null) {
                        //id
                        if (StringUtil.checkStr(datasBean._id)) {
                            userManager.saveUid(datasBean._id);
                        }
                        //role
                        if (datasBean.role != null) {
                            List<String> view_roles = datasBean.role.view_roles;
                            if (view_roles != null) {
                                userManager.saveRole(view_roles);
                            }
                        }
                        //data
                        userManager.saveUserData(datasBean);
                    }
                    //记录一下登录时间，以便于登录信息过期
                    SPUtils.put(context, "login_time", System.currentTimeMillis());
                    return true;
                })

                .subscribe(aBoolean -> {
                    mView.hideLoading();
                    mView.loginSuccess();
                }, throwable -> {
                    mView.hideLoading();
                });
    }

}

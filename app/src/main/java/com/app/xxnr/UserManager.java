package com.app.xxnr;


import android.content.Context;

import com.app.xxnr.bean.dbbeans.DatasBean;
import com.app.xxnr.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by 何鹏 on 2016/4/29.
 * 用户管理类
 */
public class UserManager {

    private static UserManager mInstance;
    private Context context;
    private DBManager dbManager;

    private UserManager(Context context, DBManager dbManager) {
        this.context = context;
        this.dbManager = dbManager;
    }


    /**
     * 获取单例引用
     */
    public static UserManager getInstance(Context context,DBManager dbManager) {
        if (mInstance == null) {
            synchronized (UserManager.class) {
                if (mInstance == null) {
                    mInstance = new UserManager(context,dbManager);
                }
            }
        }
        return mInstance;
    }



    //保存用户数据到本地
    public void saveToken(String token) {
        SPUtils.put(context, "token", token);
    }

    //读取用户的token
    public String getToken() {
        return (String) SPUtils.get(context, "token", "");
    }

    //获取用户Id
    public String getUid() {
        return (String) SPUtils.get(context, "uid", "");
    }

    //保存用户Id
    public void saveUid(String uid) {
        SPUtils.put(context, "uid", uid);
    }


    //读取用户类型
    public List<String> getRole() {
        @SuppressWarnings("unchecked")
        Set<String> roleSet = (Set<String>) SPUtils.get(context, "role", new HashSet<>());
        if (roleSet != null) {
            return new ArrayList<>(roleSet);
        }
        return null;
    }

    //保存用户类型
    public void saveRole(List<String> roleSet) {
        SPUtils.put(context, "role", new HashSet<>(roleSet));
    }


    //清除当前用户所有信息
    public void clearUserInfo() {
        SPUtils.remove(context, "uid");
        SPUtils.remove(context, "token");
        SPUtils.remove(context, "role");
        dbManager.getWritableDaoSession().getDatasBeanDao().deleteAll();
    }

    //保存用户信息
    public void saveUserData(DatasBean datasBean) {
        dbManager.getWritableDaoSession().getDatasBeanDao().insertOrReplace(datasBean);
    }

    public DatasBean getUserData() {
        return dbManager.getReadableDaoSession().getDatasBeanDao().load(getUid());
    }
}

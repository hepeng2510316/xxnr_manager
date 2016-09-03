package com.app.xxnr;


import com.app.xxnr.ui.activity.SplashActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 何鹏 on 2016/6/3.
 * 过滤（api activity等）类
 */
public class ClassFilter {
    /**
     * 不设置状态栏颜色的activity
     *
     * @return List
     */
    private static final List<String> unSetStatusBar = new ArrayList<>();

    public static List<String> getUnSetStatusBarClasses() {
        if (unSetStatusBar.isEmpty()) {
            unSetStatusBar.add(SplashActivity.class.getSimpleName());
        }
        return unSetStatusBar;
    }

}

package com.app.xxnr.utils;

/**
 * 类描述：判断是否是快速点击
 * 作者：何鹏 on 2016/8/17 18:10
 * 邮箱：hepeng@xinxinnongren.com
 */
public class FastDoubleClickUtils {

    private FastDoubleClickUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    //上次点击时间
    private static long lastClickTime;
    //周期
    private static final long INTERVAL = 500;

    /**
     * 判断是否快速连续点击
     *
     * @return 是否是连点
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < INTERVAL) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}

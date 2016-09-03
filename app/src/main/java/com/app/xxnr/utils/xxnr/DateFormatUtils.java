package com.app.xxnr.utils.xxnr;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 类描述：时间格式化工具类
 * 作者：何鹏 on 2015/12/29 17:39
 * 邮箱：hepeng@xinxinnongren.com
 */

/**
 * 格式化时间
 */
public class DateFormatUtils {

    private DateFormatUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dspFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 将输入时间字串转换为UTC时间
     *
     * @param srcTime UTC时间
     * @return 当前时间
     */
    public static String convertTime(String srcTime) {
        // 如果传入参数异常，使用本地时间
        if (null == srcTime) {
            return "";
        }
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
            dspFmt.setTimeZone(TimeZone.getDefault());
            Date result_date = sdf.parse(srcTime);
            return dspFmt.format(result_date);
        } catch (Exception e) { // 出现异常时，返回空字符串
            return "";
        }
    }
}
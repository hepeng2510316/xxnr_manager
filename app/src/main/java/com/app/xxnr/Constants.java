package com.app.xxnr;

import com.app.xxnr.utils.net.HttpLoggingInterceptor;

/**
 * Created by sll on 2015/8/21.
 * 配置中心
 */

public class Constants {
    public static final String API_ERROR = "api_error";
    public static final String SUCCESS_CODE = "1000";
    public static final String TOKEN_ERROR = "1401";

    public static final String BASE_URL = "http://ppe.xinxinnongren.com";
//    public static final String BASE_URL = "http://192.168.1.15:8070";

    public static final int TIME_OUT = 20;
    public static final boolean DEBUG_MODE = true;
    public static final HttpLoggingInterceptor.Level HTTP_LOG_LEVEL = HttpLoggingInterceptor.Level.BODY;
}

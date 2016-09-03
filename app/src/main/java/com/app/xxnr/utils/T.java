package com.app.xxnr.utils;

import android.content.Context;
import android.widget.Toast;

import com.app.xxnr.utils.xxnr.CustomToast;

/**
 * Toast统一管理类
 */
public class T {
    public static Context mContext;
    private static Toast toast;
    private static CustomToast customToast;


    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final boolean isShow = true;

    public static void register(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(CharSequence message) {
        if (mContext==null){
            throw new RuntimeException("unRegister Context in Application");
        }
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

    /**
     * 显示大号提示框
     */
    public static void showCustomToast(String msg, int imgRes) {
        if (mContext==null){
            throw new RuntimeException("unRegister Context in Application");
        }
        if (customToast != null) {
            customToast.cancel();
        }
        CustomToast.Builder builder = new CustomToast.Builder(mContext);
        customToast = builder.setMessage(msg).setMessageImage(imgRes).create();
        customToast.show();
    }


}

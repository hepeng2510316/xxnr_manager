package com.app.xxnr.utils;




import java.text.DecimalFormat;

/**
 * 字符串有关的utils
 */
public class StringUtil {

    // 判断字符串的合法性
    public static boolean checkStr(String str) {
        if (null == str) {
            return false;
        }
        if ("".equals(str)) {
            return false;
        }
        if ("".equals(str.trim())) {
            return false;
        }
        if ("null".equals(str)) {
            return false;
        }
        return true;
    }


    // 判断字符串组的合法性 并拼接
    public static String checkBufferStr(String str1, String str2, String str3, String str4) {
        StringBuilder buffer = new StringBuilder();
        if (StringUtil.checkStr(str1)) {
            buffer.append(str1);
        }
        if (StringUtil.checkStr(str2)) {
            buffer.append(str2);
        }
        if (StringUtil.checkStr(str3)) {
            buffer.append(str3);
        }
        if (StringUtil.checkStr(str4)) {
            buffer.append(str4);
        }
        return buffer.toString();
    }


    // 将float转换成浮点型保留两位小数的字符串
    public static String toTwoString(String str) {
        try {
            Double v = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("0.00");
            return String.valueOf(df.format(v));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    // 判断密码格式是否正确 6-13位
    public static boolean isPassword(String password) {
        return password.length() >= 6 || password.length() <= 13;
    }



    /**
     * 验证字符串是否为空
     *
     * @param param
     * @return
     */
    public static boolean empty(String param) {
        return param == null || param.trim().length() < 1;
    }


}

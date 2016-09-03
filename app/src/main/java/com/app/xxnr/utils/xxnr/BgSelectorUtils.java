package com.app.xxnr.utils.xxnr;

import android.content.res.ColorStateList;

/**
 * Created by 何鹏 on 2016/5/30.
 */
public class BgSelectorUtils {


    /** 对TextView设置不同状态时其文字颜色。 */
    public static ColorStateList createColorStateList(int normal, int pressed) {
        int[] colors = new int[] { pressed, normal };
        int[][] states = new int[2][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] {};
        return new ColorStateList(states, colors);
    }
}

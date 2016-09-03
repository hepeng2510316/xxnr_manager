package com.app.xxnr.utils.xxnr;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by HePeng on 2016/2/24.
 */
public class PopWindowUtils {

    private PopWindowUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static void setBackgroundBlack(View view, boolean isShow) {
        if (isShow){
            AlphaAnimation animation =new AlphaAnimation(0.0f,1.0f);
            animation.setDuration(150);
            view.setAnimation(animation);
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
    }
}

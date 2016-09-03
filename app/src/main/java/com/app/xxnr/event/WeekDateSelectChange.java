package com.app.xxnr.event;

import java.util.Date;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 16:51
 * 邮箱：hepeng@xinxinnongren.com
 */
public class WeekDateSelectChange {

    private int  index;

    public WeekDateSelectChange(int date) {
        this.index = date;
    }

    public int getIndex() {
        return index;
    }
}

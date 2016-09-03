package com.app.xxnr.event;

import java.util.Date;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 16:51
 * 邮箱：hepeng@xinxinnongren.com
 */
public class DailyDateSelectChange {

    private Date date;

    public DailyDateSelectChange(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}

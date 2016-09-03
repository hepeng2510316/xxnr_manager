package com.app.xxnr.event;

import java.util.Date;
import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/26 15:40
 * 邮箱：hepeng@xinxinnongren.com
 */
public class AgentDateSelectRange {

    private List<Date> dateList;

    public AgentDateSelectRange(List<Date> dateList) {
        this.dateList = dateList;
    }

    public List<Date> getDateList() {
        return dateList;
    }
}

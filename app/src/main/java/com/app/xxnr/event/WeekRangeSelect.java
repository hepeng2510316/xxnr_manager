package com.app.xxnr.event;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/26 18:00
 * 邮箱：hepeng@xinxinnongren.com
 */
public class WeekRangeSelect {
    int startIndex;

    int endIndex;

    public WeekRangeSelect(int endIndex, int startIndex) {
        this.endIndex = endIndex;
        this.startIndex = startIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}

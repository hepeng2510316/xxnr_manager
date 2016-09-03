package com.app.xxnr.event;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 14:12
 * 邮箱：hepeng@xinxinnongren.com
 */
public class PagerSwipeEvent {
   private int page;

    public PagerSwipeEvent(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}

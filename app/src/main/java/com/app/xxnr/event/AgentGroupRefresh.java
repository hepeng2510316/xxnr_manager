package com.app.xxnr.event;

/**
 * Created by CAI on 2016/8/12.
 */
public class AgentGroupRefresh {
   private boolean current_page_isOrder;

    public AgentGroupRefresh(boolean current_page_isOrder) {
        this.current_page_isOrder = current_page_isOrder;
    }

    public boolean isCurrent_page_isOrder() {
        return current_page_isOrder;
    }
}

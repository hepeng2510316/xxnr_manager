package com.app.xxnr.event;

/**
 * Created by CAI on 2016/8/12.
 */
public class AgentGroupDateEvent {
    private String dateStart;
    private String dateEnd;

    public AgentGroupDateEvent(String dateStart, String dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }
}

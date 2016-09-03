package com.app.xxnr.bean;


import java.util.List;

/**
 * Created by 何鹏 on 2016/5/19.
 */
public class WeekReportResult extends BaseResult {


    /**
     * registeredUserCount : 0
     * orderCount : 0
     * paidOrderCount : 0
     * paidAmount : 0
     * signedUserCount : 0
     * completedOrderCount : 0
     * newPotentialCustomerCount : 0
     * potentialCustomerRegisteredCount : 0
     * sequenceNo : 189
     * day : 20160523
     */

    public List<DailyReportsBean> dailyReports;

    public static class DailyReportsBean {
        public int registeredUserCount;
        public int agentVerifiedCount;
        public int orderCount;
        public int paidOrderCount;
        public double paidAmount;
        public int signedUserCount;
        public int completedOrderCount;
        public int newPotentialCustomerCount;
        public int potentialCustomerRegisteredCount;
        public int sequenceNo;
        public String day;
    }
}

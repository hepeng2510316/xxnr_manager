package com.app.xxnr.bean;


/**
 * Created by 何鹏 on 2016/5/23.
 */
public class StatisticReportResult extends BaseResult{


    /**
     * registeredUserCount : 20
     * orderCount : 2223
     * completedOrderCount : 51
     * paidAmount : 1.907611801E7
     * serviceStartTime : 2015-11-17
     */

    public int agentVerifiedCount;
    public int registeredUserCount;
    public int orderCount;
    public int completedOrderCount;
    public double completedOrderPaidAmount;
    public double paidAmount;
    public String serviceStartTime;
}

package com.app.xxnr.bean;


import java.util.List;

/**
 * Created by 何鹏 on 2016/5/25.
 */
public class AgentReportResult extends BaseResult {


    /**
     * agentReportYesterday : [{"agent":"5649bd6f8eba3c20360b0779","name":"靳美佳","phone":"15201532357","newInviteeCount":0,"newPotentialCustomerCount":0,"totalInviteeCount":2,"totalPotentialCustomerCount":30,"totalCompletedOrderCount":12,"totalPaidAmount":7795351.96},{"agent":"5649bd6f8eba3c20360b0765","name":"鲁琲","phone":"15110102070","newInviteeCount":0,"newPotentialCustomerCount":0,"totalInviteeCount":2,"totalPotentialCustomerCount":4,"totalCompletedOrderCount":0,"totalPaidAmount":0},{"agent":"567a2da01d51de356ce1f345","phone":"18518671828","newInviteeCount":0,"newPotentialCustomerCount":0,"totalInviteeCount":0,"totalPotentialCustomerCount":0,"totalCompletedOrderCount":0,"totalPaidAmount":0},{"agent":"56726f3b96ce96824f51f866","phone":"13764007122","newInviteeCount":0,"newPotentialCustomerCount":0,"totalInviteeCount":0,"totalPotentialCustomerCount":0,"totalCompletedOrderCount":0,"totalPaidAmount":0},{"agent":"5649bd6f8eba3c20360b079c","name":"凯凯王","phone":"13512721874","newInviteeCount":0,"newPotentialCustomerCount":8,"totalInviteeCount":43,"totalPotentialCustomerCount":38,"totalCompletedOrderCount":20,"totalPaidAmount":3370390.56},{"agent":"5649bd6f8eba3c20360b07b0","name":"哈哈","phone":"13271788771","newInviteeCount":0,"newPotentialCustomerCount":0,"totalInviteeCount":0,"totalPotentialCustomerCount":15,"totalCompletedOrderCount":0,"totalPaidAmount":0},{"agent":"5649bd6f8eba3c20360b075d","name":"13810000081","phone":"13810000081","newInviteeCount":0,"newPotentialCustomerCount":1,"totalInviteeCount":0,"totalPotentialCustomerCount":2,"totalCompletedOrderCount":0,"totalPaidAmount":0},{"agent":"566e38d60e91610a12b4336c","name":"123","phone":"13581946028","newInviteeCount":0,"newPotentialCustomerCount":0,"totalInviteeCount":0,"totalPotentialCustomerCount":0,"totalCompletedOrderCount":0,"totalPaidAmount":0}]
     * lastUpdateTime : 2016-05-24T20:00:03.279Z
     * page : 1
     * pages : 1
     * count : 8
     */

    public String lastUpdateTime;
    public int page;
    public int pages;
    public int count;
    /**
     * agent : 5649bd6f8eba3c20360b0779
     * name : 靳美佳
     * phone : 15201532357
     * newInviteeCount : 0
     * newPotentialCustomerCount : 0
     * totalInviteeCount : 2
     * totalPotentialCustomerCount : 30
     * totalCompletedOrderCount : 12
     * totalPaidAmount : 7795351.96
     */

    public List<AgentReportYesterdayBean> agentReportYesterday;

    public static class AgentReportYesterdayBean {
        public String agent;
        public String name;
        public String phone;
        public int newInviteeCount;
        public int newPotentialCustomerCount;
        public int newAgentCount;
        public int totalInviteeCount;
        public int totalPotentialCustomerCount;
        public int totalCompletedOrderCount;
        public double totalPaidAmount;
        public double totalCompletedOrderPaidAmount;
    }
}

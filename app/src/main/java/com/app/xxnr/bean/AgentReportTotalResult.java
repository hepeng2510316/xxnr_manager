package com.app.xxnr.bean;


import java.util.List;

/**
 * Created by CAI on 2016/8/12.
 */
public class AgentReportTotalResult extends BaseResult{


    /**
     * agentReports : [{"newInviteeCount":1,"newPotentialCustomerCount":4,"newAgentCount":0,"newRSCCount":0,"newOrderCount":4,"newOrderCompletedCount":1,"sequenceNo":263,"name":"靳美佳的姓名","phone":"15201532357"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"鲁琲","phone":"15110102070"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"程序员","phone":"18331058523"},{"newInviteeCount":0,"newPotentialCustomerCount":1,"newAgentCount":0,"newRSCCount":0,"newOrderCount":1,"newOrderCompletedCount":0,"sequenceNo":263,"name":"( ´･ᴗ･` )","phone":"15538313272"},{"newInviteeCount":0,"newPotentialCustomerCount":9,"newAgentCount":0,"newRSCCount":0,"newOrderCount":6,"newOrderCompletedCount":1,"sequenceNo":263,"name":"凯凯王","phone":"13512721874"},{"newInviteeCount":0,"newPotentialCustomerCount":1,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"呵","phone":"13271788771"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"123","phone":"13581946028"},{"newInviteeCount":0,"newPotentialCustomerCount":1,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"Qqqqqqqqqqqqq","phone":"18790674259"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"高达叼叼叼","phone":"18610240422"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"18537328616","phone":"18537328616"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":null,"phone":"13764007122"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":null,"phone":"18518671828"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"His","phone":"13671288039"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"13810000081","phone":"13810000081"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"你大爷","phone":"15810567786"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"素慧","phone":"15294852357"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"13839606652","phone":"13839606652"},{"newInviteeCount":0,"newPotentialCustomerCount":1,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"18732199883","phone":"18732199883"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"假的用户2","phone":"15000000001"},{"newInviteeCount":0,"newPotentialCustomerCount":0,"newAgentCount":0,"newRSCCount":0,"newOrderCount":0,"newOrderCompletedCount":0,"sequenceNo":263,"name":"果果","phone":"15176121661"}]
     * lastUpdateTime : 2016-08-12T03:45:41.838Z
     * page : 1
     * pageCount : 2
     * count : 24
     */

    public String lastUpdateTime;
    public int page;
    public int pageCount;
    public int count;
    /**
     * newInviteeCount : 1
     * newPotentialCustomerCount : 4
     * newAgentCount : 0
     * newRSCCount : 0
     * newOrderCount : 4
     * newOrderCompletedCount : 1
     * sequenceNo : 263
     * name : 靳美佳的姓名
     * phone : 15201532357
     */

    public List<AgentReportsBean> agentReports;

    public static class AgentReportsBean {
        public int newInviteeCount;
        public int newPotentialCustomerCount;
        public int newAgentCount;
        public int newRSCCount;
        public int newOrderCount;
        public int newOrderCompletedCount;
        public int sequenceNo;

        public int completedOrderCount;
        public double completedOrderPaidAmount;

        public String name;
        public String phone;



    }
}

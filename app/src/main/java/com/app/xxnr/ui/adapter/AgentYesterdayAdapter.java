package com.app.xxnr.ui.adapter;

import android.app.Activity;
import android.util.Log;

import com.app.xxnr.R;
import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.utils.StringUtil;

import javax.inject.Inject;

/**
 * agent list
 */
public class AgentYesterdayAdapter extends CommonAdapter<AgentReportResult.AgentReportYesterdayBean> {
    @Inject
    public AgentYesterdayAdapter(Activity context) {
        super(R.layout.item_agent_yesterday_report, context);
    }

    @Override
    public void convert(CommonViewHolder holder, AgentReportResult.AgentReportYesterdayBean agentReportYesterdayBean) {
        if (agentReportYesterdayBean != null) {

            if (holder.getPosition() % 2 == 0) {
                holder.getView(R.id.item_agent_report_ll).setBackgroundColor(mContext.getResources().getColor(R.color.order_title_bg));
            } else {
                holder.getView(R.id.item_agent_report_ll).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            holder.setText(R.id.new_customer_count, agentReportYesterdayBean.newInviteeCount + "");//昨日新增客户
            holder.setText(R.id.total_customer_count, agentReportYesterdayBean.totalInviteeCount + "");//总客户数
            holder.setText(R.id.total_agent_count, agentReportYesterdayBean.newAgentCount + "");//新增经纪人数
            holder.setText(R.id.reg_customer_count, agentReportYesterdayBean.newPotentialCustomerCount + "");//昨日登记客户
            holder.setText(R.id.total_reg_customer_count, agentReportYesterdayBean.totalPotentialCustomerCount + "");//总登记客户
            holder.setText(R.id.order_count, agentReportYesterdayBean.totalCompletedOrderCount + "");
            holder.setText(R.id.price_amount, StringUtil.toTwoString(agentReportYesterdayBean.totalCompletedOrderPaidAmount + ""));
        }
    }
}
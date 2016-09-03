package com.app.xxnr.ui.adapter;

import android.app.Activity;

import com.app.xxnr.R;
import com.app.xxnr.bean.AgentReportTotalResult;

import javax.inject.Inject;

/**
 * 业绩进度汇总
 */
public class AgentAchievementAdapter extends CommonAdapter<AgentReportTotalResult.AgentReportsBean> {
    @Inject
    public AgentAchievementAdapter(Activity activity) {
        super(R.layout.item_agent_group_report_achievement, activity);
    }

    @Override
    public void convert(CommonViewHolder holder, AgentReportTotalResult.AgentReportsBean agentReportBean) {
        if (agentReportBean != null) {
            if (holder.getPosition() % 2 == 0) {
                holder.getView(R.id.item_agent_report_ll).setBackgroundColor(mContext.getResources().getColor(R.color.order_title_bg));
            } else {
                holder.getView(R.id.item_agent_report_ll).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            holder.setText(R.id.new_customer_count, agentReportBean.newInviteeCount + "");//新增客户
            holder.setText(R.id.new_agent_count, agentReportBean.newAgentCount + "");//新登记经纪人
            holder.setText(R.id.reg_customer_count, agentReportBean.newPotentialCustomerCount + "");//新登记客户
            holder.setText(R.id.new_order_count, agentReportBean.newOrderCount + "");//新订单数
            holder.setText(R.id.order_count, agentReportBean.newOrderCompletedCount + "");//新完成订单数
        }
    }
}
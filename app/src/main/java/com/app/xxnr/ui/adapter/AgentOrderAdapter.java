package com.app.xxnr.ui.adapter;

import android.app.Activity;

import com.app.xxnr.R;
import com.app.xxnr.bean.AgentReportTotalResult;
import com.app.xxnr.utils.StringUtil;

import javax.inject.Inject;

/**
 * 业绩进度汇总
 */
public class AgentOrderAdapter extends CommonAdapter<AgentReportTotalResult.AgentReportsBean> {
    @Inject
    public AgentOrderAdapter(Activity activity) {
        super(R.layout.item_agent_group_report_order, activity);
    }

    @Override
    public void convert(CommonViewHolder holder, AgentReportTotalResult.AgentReportsBean agentReportBean) {
        if (agentReportBean != null) {
            if (holder.getPosition() % 2 == 0) {
                holder.getView(R.id.item_agent_report_ll).setBackgroundColor(mContext.getResources().getColor(R.color.order_title_bg));
            } else {
                holder.getView(R.id.item_agent_report_ll).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            holder.setText(R.id.item_order_complete_count, agentReportBean.completedOrderCount + "");//完成订单数
            holder.setText(R.id.item_order_complete_price, StringUtil.toTwoString(agentReportBean.completedOrderPaidAmount + ""));//完成订单金额
        }
    }
}
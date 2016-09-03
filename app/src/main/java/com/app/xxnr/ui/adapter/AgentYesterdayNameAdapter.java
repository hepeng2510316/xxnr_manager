package com.app.xxnr.ui.adapter;

import android.app.Activity;

import com.app.xxnr.R;
import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.utils.StringUtil;

import javax.inject.Inject;

/**
 * agent name list
 */
public class AgentYesterdayNameAdapter extends CommonAdapter<AgentReportResult.AgentReportYesterdayBean> {

    @Inject
    public AgentYesterdayNameAdapter(Activity activity) {
        super(R.layout.item_agent_report_name, activity);
    }

    @Override
    public void convert(CommonViewHolder holder, AgentReportResult.AgentReportYesterdayBean agentReportYesterdayBean) {
        if (agentReportYesterdayBean != null) {
            if (holder.getPosition() % 2 == 0) {
                holder.getView(R.id.item_agent_report_ll).setBackgroundColor(mContext.getResources().getColor(R.color.order_title_bg));
            } else {
                holder.getView(R.id.item_agent_report_ll).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            if (StringUtil.checkStr(agentReportYesterdayBean.name)) {
                holder.setText(R.id.agent_name, agentReportYesterdayBean.name);
            } else {
                holder.setText(R.id.agent_name, "");
            }
        }
    }
}
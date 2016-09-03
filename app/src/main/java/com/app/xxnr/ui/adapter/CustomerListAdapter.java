package com.app.xxnr.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.utils.xxnr.DateFormatUtils;
import com.app.xxnr.utils.StringUtil;


import java.util.List;

import javax.inject.Inject;

/**
 * 类描述：客户列表Adapter
 * 作者：何鹏 on 2016/5/4 17:51
 * 邮箱：hepeng@xinxinnongren.com
 */
public class CustomerListAdapter extends CommonAdapter<CustomerListResult.Users.ItemsBean> {

    @Inject
    public CustomerListAdapter(Activity mContext) {
        super(R.layout.item_customer_list_layout, mContext);
    }

    @Override
    public void convert(CommonViewHolder holder, final CustomerListResult.Users.ItemsBean itemsBean) {
        if (itemsBean != null) {
            if (StringUtil.checkStr(itemsBean.name)) {
                holder.setText(R.id.customer_name, itemsBean.name);
            } else {
                holder.setText(R.id.customer_name, "未填写姓名");
            }

            if (StringUtil.checkStr(itemsBean.account)) {
                holder.setText(R.id.customer_phone, itemsBean.account);
            } else {
                holder.setText(R.id.customer_phone, "");
            }
            ImageView sex_iv = holder.getView(R.id.customer_sex);

            if (itemsBean.sex) {
                sex_iv.setImageResource(R.mipmap.girl_icon);
            } else {
                sex_iv.setImageResource(R.mipmap.boy_icon);
            }
            if (StringUtil.checkStr(itemsBean.datecreated)) {
                holder.setText(R.id.customer_time, DateFormatUtils.convertTime(itemsBean.datecreated));
            } else {
                holder.setText(R.id.customer_time, "");
            }
            //是否点亮经销商 经纪人
            TextView county_agency = holder.getView(R.id.customer_county_agency);//县级
            TextView customer_agent = holder.getView(R.id.customer_agent);//经纪人
            county_agency.setVisibility(View.GONE);
            customer_agent.setVisibility(View.GONE);

            if (itemsBean.type.equals("5")) {
                county_agency.setVisibility(View.VISIBLE);
                county_agency.setBackgroundResource(R.drawable.circle_gray_bg);
            }
            if (itemsBean.type.equals("6")) {
                customer_agent.setVisibility(View.VISIBLE);
                customer_agent.setBackgroundResource(R.drawable.circle_gray_bg);
            }
            List<String> typeVerified = itemsBean.typeVerified;
            if (typeVerified != null) {
                if (typeVerified.contains("5")) {
                    county_agency.setVisibility(View.VISIBLE);
                    county_agency.setBackgroundResource(R.drawable.circle_orange_bg);
                }
                if (typeVerified.contains("6")) {
                    customer_agent.setVisibility(View.VISIBLE);
                    customer_agent.setBackgroundResource(R.drawable.circle_blue_bg);
                }
            }
            holder.getConvertView().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onListClick(itemsBean);
                }
            });
        }
    }

    //click interface
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onListClick(CustomerListResult.Users.ItemsBean itemsBean);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}

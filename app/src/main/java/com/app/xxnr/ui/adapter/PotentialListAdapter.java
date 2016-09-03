package com.app.xxnr.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.xxnr.R;
import com.app.xxnr.bean.PotentialListResult;
import com.app.xxnr.utils.xxnr.DateFormatUtils;
import com.app.xxnr.utils.StringUtil;

import javax.inject.Inject;

/**
 * 类描述：客户列表Adapter
 * 作者：何鹏 on 2016/5/4 17:51
 * 邮箱：hepeng@xinxinnongren.com
 */
public class PotentialListAdapter extends CommonAdapter<PotentialListResult.PotentialCustomersBean> {

    @Inject
    public PotentialListAdapter(Activity mContext) {
        super(R.layout.item_potential_list_layout, mContext);
    }

    @Override
    public void convert(CommonViewHolder holder, final PotentialListResult.PotentialCustomersBean itemsBean) {
        if (itemsBean != null) {
            if (StringUtil.checkStr(itemsBean.name)) {
                holder.setText(R.id.customer_name, itemsBean.name);
            } else {
                holder.setText(R.id.customer_name, "");
            }
            if (StringUtil.checkStr(itemsBean.phone)) {
                holder.setText(R.id.customer_phone, itemsBean.phone);
            } else {
                holder.setText(R.id.customer_phone, "");
            }
            ImageView sex_iv = holder.getView(R.id.customer_sex);
            if (itemsBean.sex) {
                sex_iv.setImageResource(R.mipmap.girl_icon);
            } else {
                sex_iv.setImageResource(R.mipmap.boy_icon);
            }
            if (StringUtil.checkStr(itemsBean.dateTimeAdded)) {
                holder.setText(R.id.customer_time, DateFormatUtils.convertTime(itemsBean.dateTimeAdded));
            } else {
                holder.setText(R.id.customer_time, "");
            }
            //是否已注册
            View registered_text = holder.getView(R.id.registered_text);
            View registered_icon = holder.getView(R.id.registered_icon);
            if (itemsBean.isRegistered) {
                registered_text.setVisibility(View.VISIBLE);
                registered_icon.setVisibility(View.VISIBLE);
            } else {
                registered_text.setVisibility(View.INVISIBLE);
                registered_icon.setVisibility(View.INVISIBLE);
            }
            //跳转到潜在客户详情
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
        void onListClick(PotentialListResult.PotentialCustomersBean  itemsBean);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}

package com.app.xxnr.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.OrderDetailResult;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.CommonAdapter;
import com.app.xxnr.ui.adapter.CommonViewHolder;
import com.app.xxnr.utils.xxnr.DateFormatUtils;
import com.app.xxnr.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 11:42
 * 邮箱：hepeng@xinxinnongren.com
 */
public class OrderPayDetailActivity extends BaseSwipeBackActivity {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ViewHolder viewHolder;

    public static void startActivity(Context mContext, OrderDetailResult.DatasBean.SubOrdersBean subOrder,String orderId) {
        Intent intent = new Intent(mContext, OrderPayDetailActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("subOrder", subOrder);
        mContext.startActivity(intent);
    }


    @Override
    public int initContentView() {
        return R.layout.activity_common_list;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        setTitle("查看支付详情");
        ButterKnife.bind(this);
        swipeRefreshLayout.setEnabled(false);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.light_gray)));
        listView.setDividerHeight(1);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_layout_check_pay_detail, null);
        viewHolder = new ViewHolder(headView);
        listView.addHeaderView(headView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {

        String orderId = getIntent().getStringExtra("orderId");
        OrderDetailResult.DatasBean.SubOrdersBean subOrder =
                (OrderDetailResult.DatasBean.SubOrdersBean) getIntent().getSerializableExtra("subOrder");

        if (StringUtil.checkStr(orderId)) {
            viewHolder.checkPayOrderId.setText("订单号：" + orderId);

            if (subOrder != null) {
                //支付阶段
                switch (subOrder.type) {
                    case "deposit":
                        viewHolder.checkPayOrderStep.setText("阶段一：订金");
                        break;
                    case "balance":
                        viewHolder.checkPayOrderStep.setText("阶段二：尾款");
                        break;
                    case "full":
                        viewHolder.checkPayOrderStep.setText("订单总额");
                        break;
                }
                //支付状态
                switch (subOrder.payStatus) {
                    case 1:
                        viewHolder.checkPayOrderState.setTextColor(getResources().getColor(R.color.orange));
                        viewHolder.checkPayOrderState.setText("待付款");
                        break;
                    case 2:
                        viewHolder.checkPayOrderState.setTextColor(getResources().getColor(R.color.default_black));
                        viewHolder.checkPayOrderState.setText("已付款");
                        break;
                    case 3:
                        viewHolder.checkPayOrderState.setTextColor(getResources().getColor(R.color.orange));
                        viewHolder.checkPayOrderState.setText("部分付款");
                        break;
                }
                //应支付金额
                viewHolder.toPayPrice.setText("¥" + StringUtil.toTwoString(subOrder.price + ""));
                //已支付金额
                viewHolder.orderYetPrice.setText("¥" + StringUtil.toTwoString(subOrder.paidPrice + ""));

                List<OrderDetailResult.DatasBean.SubOrdersBean.PaymentsBean> payments = subOrder.payments;
                List<OrderDetailResult.DatasBean.SubOrdersBean.PaymentsBean> payments1 = new ArrayList<>();

                if (payments != null && !payments.isEmpty()) {
                    for (int i = 0; i < payments.size(); i++) {
                        OrderDetailResult.DatasBean.SubOrdersBean.PaymentsBean bean = payments.get(i);
                        if (bean != null) {
                            if (bean.payStatus == 2) {
                                payments1.add(bean);
                            }
                        }
                    }

                }
                if (!payments1.isEmpty()) {
                    PayInfoAdapter payInfoAdapter = new PayInfoAdapter(instance);
                    listView.setAdapter(payInfoAdapter);
                    payInfoAdapter.bindData(payments1);
                }

            }
        }

    }


    static class ViewHolder {
        @BindView(R.id.check_pay_orderId)
        TextView checkPayOrderId;
        @BindView(R.id.check_pay_orderStep)
        TextView checkPayOrderStep;
        @BindView(R.id.check_pay_orderState)
        TextView checkPayOrderState;
        @BindView(R.id.to_pay_price)
        TextView toPayPrice;
        @BindView(R.id.order_yet_price)
        TextView orderYetPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    class PayInfoAdapter extends CommonAdapter<OrderDetailResult.DatasBean.SubOrdersBean.PaymentsBean> {


        public PayInfoAdapter(Context context) {
            super(R.layout.item_payinfo_detail,context);
        }

        @Override
        public void convert(CommonViewHolder holder, OrderDetailResult.DatasBean.SubOrdersBean.PaymentsBean payments) {
            if (payments != null) {
                //文本内容

                if (payments.payType == 1) {
                    holder.setText(R.id.order_pay_type, "支付宝支付");
                } else if (payments.payType == 2) {
                    holder.setText(R.id.order_pay_type, "银联支付");
                } else if (payments.payType == 3) {
                    holder.setText(R.id.order_pay_type, "现金");
                } else if (payments.payType == 4) {
                    holder.setText(R.id.order_pay_type, "线下POS机");
                } else if (payments.payType == 5) {
                    holder.setText(R.id.order_pay_type, "EPOS支付");
                }
                //支付金额
                if (StringUtil.checkStr(payments.price + "")) {
                    holder.setText(R.id.pay_price, "¥" + StringUtil.toTwoString(payments.price + ""));
                }
                //支付时间
                if (StringUtil.checkStr(payments.datePaid)) {
                    holder.setText(R.id.order_pay_time, DateFormatUtils.convertTime(payments.datePaid));
                }
                //支付结果
                if (payments.payStatus == 1) {
                    holder.setText(R.id.item_payInfo_state, "");
                } else if (payments.payStatus == 2) {
                    holder.setText(R.id.item_payInfo_state, "支付成功");
                }

                //第几次付款
                holder.setText(R.id.item_payInfo_times, "第" + payments.slice + "次");

                //支付网点

                View order_pay_state_ll = holder.getView(R.id.order_pay_state_ll);

                if (StringUtil.checkStr(payments.RSCCompanyName)) {
                    order_pay_state_ll.setVisibility(View.VISIBLE);
                    holder.setText(R.id.order_pay_state, payments.RSCCompanyName);
                } else {
                    order_pay_state_ll.setVisibility(View.GONE);
                }

            }


        }
    }

}

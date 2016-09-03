package com.app.xxnr.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.OrderDetailResult;
import com.app.xxnr.bean.OrderListResult;
import com.app.xxnr.contract.OrderDetailContract;
import com.app.xxnr.contract.OrderOperationContract;
import com.app.xxnr.event.OrderOperateEvent;
import com.app.xxnr.presenter.OrderDetailPresenter;
import com.app.xxnr.presenter.OrderOperationPresenter;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.OrderCarAdapter;
import com.app.xxnr.ui.adapter.CommonAdapter;
import com.app.xxnr.ui.adapter.CommonViewHolder;
import com.app.xxnr.utils.xxnr.DateFormatUtils;
import com.app.xxnr.utils.xxnr.PopWindowUtils;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.widget.UnSwipeListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/24 15:29
 * 邮箱：hepeng@xinxinnongren.com
 */
public class OrderDetailActivity extends BaseSwipeBackActivity implements OrderDetailContract.View, OrderOperationContract.View {
    @BindView(R.id.order_shangpin_list)
    ListView orderShangpinList;
    @BindView(R.id.change_pay_type)
    Button changePayType;
    @BindView(R.id.go_to_pay)
    Button goToPay;
    @BindView(R.id.go_to_pay_rel)
    RelativeLayout goToPayRel;
    @BindView(R.id.topay_ll)
    LinearLayout topayLl;
    @BindView(R.id.pop_bg)
    RelativeLayout popBg;
    @BindView(R.id.order_detail_rl)
    RelativeLayout orderDetailRl;


    @Inject
    OrderDetailPresenter mPresenter;

    @Inject
    OrderOperationPresenter mOperationPresenter;

    private OrderListResult.DatasBean.ItemsBean itemsBean;
    private HeadViewHolder head_holder;
    private FootViewHolder foot_holder;


    public static void startActivity(Context mContext, OrderListResult.DatasBean.ItemsBean itemsBean) {
        Intent intent = new Intent(mContext, OrderDetailActivity.class);
        intent.putExtra("itemsBean", itemsBean);
        mContext.startActivity(intent);
    }


    @Override
    public int initContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        setTitle("订单详情");
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mOperationPresenter.attachView(this);
        //头部信息 订单号： 交易状态 送货人 地址
        View head_layout = LayoutInflater.from(instance).inflate(R.layout.head_layout_order_detail, null);
        head_holder = new HeadViewHolder(head_layout);
        head_holder.selectStateAddressLl.setVisibility(View.GONE);
        head_holder.addressShouhuoLl.setVisibility(View.GONE);
        head_holder.selectStateAddressNone.setVisibility(View.GONE);
        head_holder.selectStateAddressLlPerson.setVisibility(View.GONE);
        View foot_layout = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.foot_layout_order_detail, null);
        foot_holder = new FootViewHolder(foot_layout);

        orderShangpinList.addHeaderView(head_layout);
        orderShangpinList.addFooterView(foot_layout);

    }

    @Override
    public void initData() {
        itemsBean = (OrderListResult.DatasBean.ItemsBean) getIntent().getSerializableExtra("itemsBean");
        if (itemsBean!=null){
            mPresenter.onLoad(itemsBean.id);
        }
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        dismissProgress();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void renderList(OrderDetailResult result) {
        OrderDetailResult.DatasBean datasBean = result.datas;
        if (datasBean != null) {
            //订单号
            head_holder.myOrderDetailId.setText("订单号：" + datasBean.id);
            //下单时间
            head_holder.payTime.setText(DateFormatUtils.convertTime(datasBean.dateCreated));

            //下单人
            if (StringUtil.checkStr(datasBean.buyerName)) {
                head_holder.addOrderMan.setText(datasBean.buyerName);
            } else {
                head_holder.addOrderMan.setText(datasBean.consigneePhone);
            }
            //配送方式
            if (datasBean.deliveryType == 1) {  //网点自提
                head_holder.deliveryIcon.setVisibility(View.VISIBLE);
                head_holder.deliveryText.setVisibility(View.VISIBLE);
                head_holder.deliveryIcon.setBackgroundResource(R.mipmap.home_delivery_icon);
                head_holder.deliveryText.setText("网点自提");

                head_holder.selectStateAddressLlPerson.setVisibility(View.VISIBLE);
                head_holder.selectStatePersonInfo.setText(datasBean.consigneeName + "  " + datasBean.consigneePhone);

            } else {
                head_holder.deliveryIcon.setVisibility(View.VISIBLE);//其他的暂用 送货到家图标
                head_holder.deliveryText.setVisibility(View.VISIBLE);
                head_holder.deliveryIcon.setBackgroundResource(R.mipmap.state_delivery_icon);
                head_holder.deliveryText.setText("配送到户");

                head_holder.addressShouhuoLl.setVisibility(View.VISIBLE);
                //联系人 及电话
                head_holder.orderDetailNameTv.setText(datasBean.consigneeName + "  " + datasBean.consigneePhone);
                //联系人地址
                head_holder.orderDetailAddressTv.setText(datasBean.consigneeAddress);
            }
            //设置RSCInfo
            if (datasBean.RSCInfo != null) {
                //收货地址网点等信息
                head_holder.selectStateAddressLl.setVisibility(View.VISIBLE);
                if (StringUtil.checkStr(datasBean.RSCInfo.companyName)) {
                    head_holder.selectStateName.setText(datasBean.RSCInfo.companyName);
                }
                if (StringUtil.checkStr(datasBean.RSCInfo.RSCAddress)) {
                    head_holder.selectStateAddress.setText(datasBean.RSCInfo.RSCAddress);
                }
                if (StringUtil.checkStr(datasBean.RSCInfo.RSCPhone)) {
                    head_holder.selectStatePhone.setText(datasBean.RSCInfo.RSCPhone);
                }
            } else {
                head_holder.selectStateAddressNone.setVisibility(View.VISIBLE);
            }

            //合计 与 去支付
            if (datasBean.order != null) {

                foot_holder.myOrderDetailPrice.setText("¥" + StringUtil.toTwoString(datasBean.order.totalPrice + ""));
                if (datasBean.order.orderStatus != null) {
                    //订单状态
                    if (StringUtil.checkStr(datasBean.order.orderStatus.value)) {
                        head_holder.payState.setText(datasBean.order.orderStatus.value);
                    }
                    //不支付
                    goToPayRel.setVisibility(View.GONE);
                }
            }


            //支付信息列表
            if (datasBean.subOrders != null && !datasBean.subOrders.isEmpty()) {
                PayInfoAdapter payInfoAdapter = new PayInfoAdapter(this, datasBean.subOrders, datasBean.orderType);
                head_holder.payInfoListView.setAdapter(payInfoAdapter);
                payInfoAdapter.bindData(datasBean.subOrders);
            }

            //子商品列表
            List<OrderDetailResult.DatasBean.SKUsBean> skUs = datasBean.SKUs;
            if (skUs != null && !skUs.isEmpty()) {
                OrderCarAdapter orderCarAdapter = new OrderCarAdapter(instance,true, skUs);
                orderShangpinList.setAdapter(orderCarAdapter);
            } else {
                OrderCarAdapter orderCarAdapter = new OrderCarAdapter(instance,datasBean.products, false);
                orderShangpinList.setAdapter(orderCarAdapter);
            }


            //发货
            goToPayRel.setVisibility(View.GONE);
            goToPay.setVisibility(View.GONE);
            changePayType.setVisibility(View.GONE);
            goToPay.setOnClickListener(null);
            changePayType.setOnClickListener(null);

            boolean isSure = false;
            if (datasBean.order != null && datasBean.order.pendingDeliverToRSC) {
                final List<OrderDetailResult.DatasBean.SKUsBean> skUs2 = new ArrayList<>();
                List<OrderDetailResult.DatasBean.SKUsBean> skUs1 = datasBean.SKUs;
                if (skUs1 != null) {
                    for (int i = 0; i < skUs1.size(); i++) {
                        OrderDetailResult.DatasBean.SKUsBean sku = skUs1.get(i);
                        if (sku != null && sku.deliverStatus == 1) {
                            skUs2.add(sku);
                            isSure = true;
                        }
                    }

                }
                if (isSure) {
                    goToPayRel.setVisibility(View.VISIBLE);
                    goToPay.setVisibility(View.VISIBLE);
                    goToPay.setText("发货到服务站");
                    goToPay.setOnClickListener(v -> mOperationPresenter.deliveryState(v,itemsBean));
                }
            }
            //审核
            if (isSure) {
                if (datasBean.order != null && datasBean.order.orderStatus != null) {
                    if (datasBean.order.orderStatus.type == 7) {
                        goToPayRel.setVisibility(View.VISIBLE);
                        changePayType.setVisibility(View.VISIBLE);
                        changePayType.setText("审核付款");
                        changePayType.setOnClickListener(v -> mOperationPresenter.checkOffline(v,itemsBean));
                    }
                }
            } else {
                if (datasBean.order != null && datasBean.order.orderStatus != null) {
                    if (datasBean.order.orderStatus.type == 7) {
                        goToPayRel.setVisibility(View.VISIBLE);
                        goToPay.setVisibility(View.VISIBLE);
                        goToPay.setText("审核付款");
                        goToPay.setOnClickListener(v -> mOperationPresenter.checkOffline(v,itemsBean));
                    }

                }

            }

        }
    }

    @Override
    public void showDialogBg(boolean show) {
        PopWindowUtils.setBackgroundBlack(popBg,show);
    }

    @Override
    public void operateSuccess() {
        mPresenter.reLoad();
        EventBus.getDefault().post(new OrderOperateEvent());
    }

    //支付信息列表

    public class PayInfoAdapter extends CommonAdapter<OrderDetailResult.DatasBean.SubOrdersBean> {
        private List<OrderDetailResult.DatasBean.SubOrdersBean> data;
        private int orderType;

        public PayInfoAdapter(Context context, List<OrderDetailResult.DatasBean.SubOrdersBean> data, int orderType) {
            super( R.layout.item_payinfo_orderdetail,context);
            this.data = data;
            this.orderType = orderType;
        }

        @Override
        public void convert(CommonViewHolder holder, final OrderDetailResult.DatasBean.SubOrdersBean subOrder) {
            if (subOrder != null) {

                //支付阶段
                TextView item_payInfo_step = ( holder.getView(R.id.item_payInfo_step));
                switch (subOrder.type) {
                    case "deposit":
                        item_payInfo_step.setText("阶段一：订金");
                        break;
                    case "balance":
                        item_payInfo_step.setText("阶段二：尾款");
                        break;
                    case "full":
                        item_payInfo_step.setText("订单总额");
                        break;
                }

                //支付状态
                TextView item_payInfo_type =  holder.getView(R.id.item_payInfo_type);
                if (orderType != 0) { //如果交易状态 是已关闭 下方设置已关闭
                    if (subOrder.type.equals("balance")) {//阶段二的子订单
                        try {
                            if (data.get(holder.getPosition() - 1).payStatus == 2) {//如果阶段一的子订单 已付款
                                switch (subOrder.payStatus) {
                                    case 1:
                                        item_payInfo_type.setTextColor(getResources().getColor(R.color.orange));
                                        item_payInfo_type.setText("待付款");
                                        break;
                                    case 2:
                                        item_payInfo_type.setTextColor(getResources().getColor(R.color.default_black));
                                        item_payInfo_type.setText("已付款");
                                        break;
                                    case 3:
                                        item_payInfo_type.setTextColor(getResources().getColor(R.color.orange));
                                        item_payInfo_type.setText("部分付款");
                                        break;
                                }

                            } else {
                                item_payInfo_type.setTextColor(getResources().getColor(R.color.default_black));
                                item_payInfo_type.setText("未开始");
                            }
                        } catch (Exception e) {//如果下标越界 就 设置 默认设置

                            switch (subOrder.payStatus) {
                                case 1:
                                    item_payInfo_type.setTextColor(getResources().getColor(R.color.orange));
                                    item_payInfo_type.setText("待付款");
                                    break;
                                case 2:
                                    item_payInfo_type.setTextColor(getResources().getColor(R.color.default_black));
                                    item_payInfo_type.setText("已付款");
                                    break;
                                case 3:
                                    item_payInfo_type.setTextColor(getResources().getColor(R.color.orange));
                                    item_payInfo_type.setText("部分付款");
                                    break;
                            }
                        }

                    } else {
                        switch (subOrder.payStatus) {
                            case 1:
                                item_payInfo_type.setTextColor(getResources().getColor(R.color.orange));
                                item_payInfo_type.setText("待付款");
                                break;
                            case 2:
                                item_payInfo_type.setTextColor(getResources().getColor(R.color.default_black));
                                item_payInfo_type.setText("已付款");
                                break;
                            case 3:
                                item_payInfo_type.setTextColor(getResources().getColor(R.color.orange));
                                item_payInfo_type.setText("部分付款");
                                break;
                        }
                    }

                } else {
                    item_payInfo_type.setTextColor(getResources().getColor(R.color.default_black));
                    item_payInfo_type.setText("已关闭");
                }

                //应支付金额
                holder.setText(R.id.to_pay_price, "¥" + StringUtil.toTwoString(subOrder.price + ""));
                //已支付金额
                holder.setText(R.id.order_yet_price, "¥" + StringUtil.toTwoString(subOrder.paidPrice + ""));
                //查看详情
                TextView to_get_pay_detail =  holder.getView(R.id.to_get_pay_detail);
                if (subOrder.payStatus == 2 || subOrder.payStatus == 3) {
                    to_get_pay_detail.setVisibility(View.VISIBLE);
                    to_get_pay_detail.setOnClickListener(v -> {
                        if (itemsBean!=null){
                            OrderPayDetailActivity.startActivity(instance,subOrder,itemsBean.id);
                        }
                    });
                } else {
                    to_get_pay_detail.setVisibility(View.GONE);
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mOperationPresenter.detachView();
    }

    static class HeadViewHolder {
        @BindView(R.id.my_order_detail_id)
        TextView myOrderDetailId;
        @BindView(R.id.pay_state)
        TextView payState;
        @BindView(R.id.add_order_man)
        TextView addOrderMan;
        @BindView(R.id.pay_time)
        TextView payTime;
        @BindView(R.id.contact_ll)
        LinearLayout contactLl;
        @BindView(R.id.delivery_icon)
        ImageView deliveryIcon;
        @BindView(R.id.delivery_text)
        TextView deliveryText;
        @BindView(R.id.order_detail_name_tv)
        TextView orderDetailNameTv;
        @BindView(R.id.order_detail_address_tv)
        TextView orderDetailAddressTv;
        @BindView(R.id.address_shouhuo_ll)
        LinearLayout addressShouhuoLl;
        @BindView(R.id.select_state_name)
        TextView selectStateName;
        @BindView(R.id.select_state_address)
        TextView selectStateAddress;
        @BindView(R.id.select_state_phone)
        TextView selectStatePhone;
        @BindView(R.id.select_state_address_ll)
        LinearLayout selectStateAddressLl;
        @BindView(R.id.select_state_person_info)
        TextView selectStatePersonInfo;
        @BindView(R.id.select_state_address_ll_person)
        LinearLayout selectStateAddressLlPerson;
        @BindView(R.id.select_state_address_none)
        LinearLayout selectStateAddressNone;
        @BindView(R.id.pay_info_listView)
        UnSwipeListView payInfoListView;

        HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class FootViewHolder {
        @BindView(R.id.my_order_detail_price)
        TextView myOrderDetailPrice;
        @BindView(R.id.contact_ll)
        RelativeLayout contactLl;

        FootViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.app.xxnr.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.xxnr.Constants;
import com.app.xxnr.R;
import com.app.xxnr.bean.OrderListResult;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.widget.RecyclerImageView;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：OrderListAdapter
 * 作者：何鹏 on 2016/5/5 18:03
 * 邮箱：hepeng@xinxinnongren.com
 */
public class OrderListAdapter extends CommonAdapter<OrderListResult.DatasBean.ItemsBean> {


    private boolean isComment;//防止"发货到服务站"的订单出现在待审核列表

    @Inject
    public OrderListAdapter(Activity context) {
        super(R.layout.item_order_list, context);
    }

    public void setComment(boolean isComment) {
        this.isComment = isComment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void convert(CommonViewHolder holder, final OrderListResult.DatasBean.ItemsBean itemsBean) {
        if (itemsBean != null) {
            //设置订单号
            if (StringUtil.checkStr(itemsBean.id)) {
                holder.setText(R.id.my_order_id, "订单号：" + itemsBean.id);
            } else {
                holder.setText(R.id.my_order_id, "订单号：");
            }
            ///配送方式
            if (itemsBean.deliveryType == 1) {
                holder.setText(R.id.my_order_delivery_type, "网点自提");
            } else if (itemsBean.deliveryType == 2) {
                holder.setText(R.id.my_order_delivery_type, "配送到户");
            } else {
                holder.setText(R.id.my_order_delivery_type, "");

            }
            //订单合计金额
            if (StringUtil.checkStr(itemsBean.price + "")) {
                holder.setText(R.id.my_order_pay_price, "¥" + StringUtil.toTwoString(itemsBean.price + ""));
            } else {
                holder.setText(R.id.my_order_pay_price, "");
            }
            //订单状态 及不同订单状态下所对应的操作
            RelativeLayout go_to_pay_rel = holder.getView(R.id.go_to_pay_rel);
            Button go_to_pay = holder.getView(R.id.go_to_pay);

            holder.setText(R.id.my_order_pay_state, "");
            go_to_pay_rel.setVisibility(View.GONE);
            go_to_pay.setOnClickListener(null);
            OrderListResult.DatasBean.ItemsBean.OrderBean orderBean = itemsBean.order;

            if (orderBean != null && orderBean.orderStatus != null) {
                if (StringUtil.checkStr(orderBean.orderStatus.value)) {
                    holder.setText(R.id.my_order_pay_state, orderBean.orderStatus.value);
                } else {
                    holder.setText(R.id.my_order_pay_state, "");
                }
                if (orderBean.orderStatus.type == 7) {

                    go_to_pay_rel.setVisibility(View.VISIBLE);
                    go_to_pay.setText("审核付款");

                    RxView.clicks(go_to_pay)
                            .throttleFirst(1, TimeUnit.SECONDS)
                            .subscribe(aVoid -> {
                                if (buttonListener != null) {
                                    buttonListener.onItemButtonClick(go_to_pay, true, itemsBean);
                                }
                            });
                }
            }
            if (!isComment) {
                if (orderBean != null && orderBean.pendingDeliverToRSC) {
                    boolean isSure = false;
                    List<OrderListResult.DatasBean.ItemsBean.SKUsBean> skUs = new ArrayList<>();
                    if (itemsBean.SKUs != null) {
                        for (int i = 0; i < itemsBean.SKUs.size(); i++) {
                            OrderListResult.DatasBean.ItemsBean.SKUsBean skus = itemsBean.SKUs.get(i);
                            if (skus != null && skus.deliverStatus == 1) {
                                skUs.add(skus);
                                isSure = true;
                            }
                        }
                    }
                    if (isSure) {
                        go_to_pay_rel.setVisibility(View.VISIBLE);
                        go_to_pay.setText("发货到服务站");

                        RxView.clicks(go_to_pay)
                                .throttleFirst(1, TimeUnit.SECONDS)
                                .subscribe(aVoid -> {
                                    if (buttonListener != null) {
                                        buttonListener.onItemButtonClick(go_to_pay, false, itemsBean);
                                    }
                                });
                    }
                }
            }

            LinearLayout llCommerceContainer = holder.getView(R.id.my_order_llCommerceContainer);

            List<OrderListResult.DatasBean.ItemsBean.SKUsBean> skUs = itemsBean.SKUs;
            List<OrderListResult.DatasBean.ItemsBean.ProductBean> products = itemsBean.products;

            //商品列表
            if (llCommerceContainer.getChildCount() > 0) {
                llCommerceContainer.removeAllViews();
            }
            if (skUs != null && !skUs.isEmpty()) {
                for (int i = 0; i < skUs.size(); i++) {
                    ViewGroup rootView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_item_order_list, null);
                    ViewHolder viewHolder = new ViewHolder(rootView);
                    //商品图片
                    if (StringUtil.checkStr(skUs.get(i).thumbnail)) {
                        Glide.with(mContext)
                                .load(Constants.BASE_URL + skUs.get(i).thumbnail)
                                .error(R.mipmap.error)
                                .placeholder(R.mipmap.zhanweitu)
                                .into(viewHolder.orderingItemImg);
                    }
                    //商品个数
                    viewHolder.orderingItemGeshu.setText("X " + skUs.get(i).count + "");
                    //商品名
                    if (StringUtil.checkStr(skUs.get(i).productName)) {
                        viewHolder.orderingItemName.setText(skUs.get(i).productName);
                    }
                    //附加选项
                    StringBuilder stringAdditions = new StringBuilder();
                    double car_additions_price = 0;
                    if (skUs.get(i).additions != null && !skUs.get(i).additions.isEmpty()) {
                        viewHolder.additionsLin.setVisibility(View.VISIBLE);
                        stringAdditions.append("附加项目:");
                        for (int k = 0; k < skUs.get(i).additions.size(); k++) {
                            if (StringUtil.checkStr(skUs.get(i).additions.get(k).name)) {
                                stringAdditions.append(skUs.get(i).additions.get(k).name).append(";");
                                car_additions_price += skUs.get(i).additions.get(k).price;
                            }
                        }
                        String car_additions = stringAdditions.toString().substring(0, stringAdditions.toString().length() - 1);
                        if (StringUtil.checkStr(car_additions)) {
                            viewHolder.additionsText.setText(car_additions);
                            viewHolder.additionsPrice.setText("¥" + StringUtil.toTwoString(car_additions_price + ""));
                        }
                    } else {
                        viewHolder.additionsLin.setVisibility(View.GONE);
                    }
                    //商品 单价 阶段 订金 尾款
                    viewHolder.orderingNowPri.setTextColor(mContext.getResources().getColor(R.color.orange_goods_price));
                    if (skUs.get(i).deposit == 0) {
                        viewHolder.goodsCarItemBar.setVisibility(View.GONE);
                    } else {
                        viewHolder.goodsCarItemBar.setVisibility(View.VISIBLE);
                        String deposit = StringUtil.toTwoString(skUs
                                .get(i).deposit * skUs.get(i).count + "");
                        if (StringUtil.checkStr(deposit)) {
                            viewHolder.goodsCarItemBarDeposit.setText("¥" + deposit);
                        }
                        String weiKuan = StringUtil.toTwoString((skUs.get(i).price + car_additions_price - skUs
                                .get(i).deposit) * skUs.get(i).count + "");
                        if (StringUtil.checkStr(weiKuan)) {
                            viewHolder.goodsCarItemBarWeikuan.setText("¥" + weiKuan);
                        }
                    }
                    viewHolder.orderingNowPri.setText("¥" + StringUtil.toTwoString(skUs
                            .get(i).price + ""));

                    //Sku属性
                    StringBuilder stringSku = new StringBuilder();
                    if (skUs.get(i).attributes != null && !skUs.get(i).attributes.isEmpty()) {
                        for (int k = 0; k < skUs.get(i).attributes.size(); k++) {
                            if (StringUtil.checkStr(skUs.get(i).attributes.get(k).name)
                                    && StringUtil.checkStr(skUs.get(i).attributes.get(k).value)) {
                                stringSku.append(skUs.get(i).attributes.get(k).name).append(":").append(skUs.get(i).attributes.get(k).value).append(";");
                            }
                        }
                        String car_attr = stringSku.toString().substring(0, stringSku.toString().length() - 1);
                        if (StringUtil.checkStr(car_attr)) {
                            viewHolder.orderingItemAttr.setText(car_attr);
                        }
                    }
                    llCommerceContainer.addView(rootView);
                }

            } else if (products != null && !products.isEmpty()) {//老的订单
                for (int i = 0; i < products.size(); i++) {
                    ViewGroup rootView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_item_order_list, null);
                    ViewHolder viewHolderChild = new ViewHolder(rootView);
                    //商品图片
                    if (StringUtil.checkStr(products.get(i).thumbnail)) {
                        Glide.with(mContext)
                                .load(Constants.BASE_URL + products.get(i).thumbnail)
                                .error(R.mipmap.error)
                                .placeholder(R.mipmap.zhanweitu)
                                .into(viewHolderChild.orderingItemImg);
                    }
                    //商品个数
                    viewHolderChild.orderingItemGeshu.setText("X " + products.get(i).count + "");
                    //商品名
                    if (StringUtil.checkStr(products.get(i).name)) {
                        viewHolderChild.orderingItemName.setText(products.get(i).name);
                    }

                    if (products.get(i).deposit == 0) {
                        viewHolderChild.orderingNowPri.setTextColor(mContext.getResources().getColor(R.color.orange_goods_price));
                        viewHolderChild.goodsCarItemBar.setVisibility(View.GONE);
                    } else {
                        viewHolderChild.orderingNowPri.setTextColor(mContext.getResources().getColor(R.color.deep_black));
                        viewHolderChild.goodsCarItemBar.setVisibility(View.VISIBLE);
                        String deposit = StringUtil.toTwoString(products
                                .get(i).deposit * products.get(i).count + "");
                        if (StringUtil.checkStr(deposit)) {
                            viewHolderChild.goodsCarItemBarDeposit.setText("¥" + deposit);
                        }
                        String weiKuan = StringUtil.toTwoString((products.get(i).price - products
                                .get(i).deposit) * products.get(i).count + "");
                        if (StringUtil.checkStr(weiKuan)) {
                            viewHolderChild.goodsCarItemBarWeikuan.setText("¥" + weiKuan);
                        }
                    }
                    String now_pri = StringUtil.toTwoString(products
                            .get(i).price + "");
                    if (StringUtil.checkStr(now_pri)) {
                        viewHolderChild.orderingNowPri.setText("¥" + now_pri);
                    }
                    llCommerceContainer.addView(rootView);
                }
            }

            RxView.clicks(llCommerceContainer)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(aVoid -> {
                        if (clickListener != null) {
                            clickListener.OnItemClick(itemsBean);
                        }
                    });
        }
    }


    static class ViewHolder {
        @BindView(R.id.ordering_item_img)
        RecyclerImageView orderingItemImg;
        @BindView(R.id.ordering_item_name)
        TextView orderingItemName;
        @BindView(R.id.ordering_item_attr)
        TextView orderingItemAttr;
        @BindView(R.id.ordering_item_geshu)
        TextView orderingItemGeshu;
        @BindView(R.id.ordering_now_pri)
        TextView orderingNowPri;
        @BindView(R.id.additions_text)
        TextView additionsText;
        @BindView(R.id.additions_price)
        TextView additionsPrice;
        @BindView(R.id.additions_lin)
        LinearLayout additionsLin;
        @BindView(R.id.goods_car_item_bar_deposit)
        TextView goodsCarItemBarDeposit;
        @BindView(R.id.goods_car_item_bar_weikuan)
        TextView goodsCarItemBarWeikuan;
        @BindView(R.id.goods_car_item_bar)
        LinearLayout goodsCarItemBar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    //click button interface
    private OnItemButtonListener buttonListener;

    public interface OnItemButtonListener {
        void onItemButtonClick(View view, boolean isComment, OrderListResult.DatasBean.ItemsBean itemsBean);
    }

    public void setOnItemButtonListener(OnItemButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    //click interface
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void OnItemClick(OrderListResult.DatasBean.ItemsBean itemsBean);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

}

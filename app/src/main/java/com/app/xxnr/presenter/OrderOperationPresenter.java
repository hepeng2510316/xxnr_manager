package com.app.xxnr.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.api.ApiService;
import com.app.xxnr.bean.OfflinePayTypeResult;
import com.app.xxnr.bean.OfflineStateListResult;
import com.app.xxnr.bean.OrderDetailResult;
import com.app.xxnr.bean.OrderListResult;
import com.app.xxnr.contract.OrderOperationContract;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.ui.adapter.CommonAdapter;
import com.app.xxnr.ui.adapter.CommonViewHolder;
import com.app.xxnr.utils.xxnr.ShowHideUtils;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;
import com.app.xxnr.widget.UnSwipeGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 类描述：订单操作  审核付款   发货到服务站
 * 作者：何鹏 on 2016/8/23 18:34
 * 邮箱：hepeng@xinxinnongren.com
 */
public class OrderOperationPresenter extends BasicPresenter<OrderOperationContract.View> implements OrderOperationContract.Presenter {
    private Subscription subscription;

    private Activity mActivity;
    private PopupWindow popupWindowConfirm;
    private PopupWindow popupWindowDelivery;

    private ViewHolderConfirm viewHolderConfirm;
    private ViewHolderDelivery viewHolderDelivery;

    private Map<String, Boolean> checkedMapPayType = new HashMap<>();
    private Map<String, Boolean> checkedMapState = new HashMap<>();
    private Map<String, Boolean> checkedDelivery = new HashMap<>();


    private String offlinePaymentId;
    private String deliveringOrderId;

    private boolean confirm_pay_tag;
    private List<OfflinePayTypeResult.OfflinePayTypeBean> offlinePayType;
    private List<OfflineStateListResult.RSCsBean> rsCs;


    @Inject
    public OrderOperationPresenter(ApiService api, Activity activity) {
        super(api);
        this.mActivity = activity;
    }

    /**
     * 审核付款
     */
    @Override
    public void checkOffline(View parent, OrderListResult.DatasBean.ItemsBean itemsBean) {
        if (itemsBean==null){
            return;
        }
        if (popupWindowConfirm == null) {
            initConfirmPop();
        }
        popupWindowConfirm.dismiss();
        viewHolderConfirm.popTitle.setText("审核付款");
        viewHolderConfirm.popLinStep1.setVisibility(View.VISIBLE);
        viewHolderConfirm.popLinStep2.setVisibility(View.GONE);
        viewHolderConfirm.popSave.setEnabled(false);
        viewHolderConfirm.checkPrice.setText("");
        viewHolderConfirm.recipientName.setText("");

        checkedMapPayType.clear();
        checkedMapState.clear();
        offlinePaymentId = null;
        confirm_pay_tag = false;

        mView.showDialogBg(true);
        popupWindowConfirm.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        getData(itemsBean.id);
    }

    /**
     * 发货到服务站
     */
    @Override
    public void deliveryState(View parent, OrderListResult.DatasBean.ItemsBean itemsBean) {
        if (itemsBean==null){
            return;
        }

        if (popupWindowDelivery == null) {
            initDeliveryPop();
        }
        popupWindowDelivery.dismiss();
        viewHolderDelivery.popOrderTitle.setText("发货到服务站");
        viewHolderDelivery.save.setEnabled(false);
        viewHolderDelivery.save.setText("确定");

        checkedDelivery.clear();
        if (StringUtil.checkStr(itemsBean.id)) {
            deliveringOrderId = itemsBean.id;
        } else {
            deliveringOrderId = null;
        }
        final List<OrderListResult.DatasBean.ItemsBean.SKUsBean> skUs = new ArrayList<>();
        List<OrderListResult.DatasBean.ItemsBean.SKUsBean> skUs1 = itemsBean.SKUs;
        if (skUs1 != null) {
            for (int i = 0; i < skUs1.size(); i++) {
                OrderListResult.DatasBean.ItemsBean.SKUsBean skus = skUs1.get(i);
                if (skus != null && skus.deliverStatus == 1) {
                    skUs.add(skus);
                }
            }
        }
        if (!skUs.isEmpty()) {
            PopSkusDeliveryAdapter popSkusDeliveryAdapter = new PopSkusDeliveryAdapter(mActivity, skUs);
            viewHolderDelivery.popListView.setAdapter(popSkusDeliveryAdapter);
        }

        //设置背景及展示
        mView.showDialogBg(true);
        popupWindowDelivery.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * ConfirmPop
     */
    private void initConfirmPop() {
        // TODO Auto-generated method stub
        @SuppressLint("InflateParams") View popupWindow_view = LayoutInflater.from(mActivity).inflate(
                R.layout.pop_layout_rsc_check_pay_dialog, null, false);
        // 创建PopupWindow实例
        popupWindowConfirm = new PopupWindow(popupWindow_view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindowConfirm.setAnimationStyle(R.style.popWindow_anim_style);
        popupWindowConfirm.setFocusable(true);
        popupWindowConfirm.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindowConfirm.setOnDismissListener(() -> {
            //设置背景及展示
            mView.showDialogBg(false);
        });
        //初始化组件
        viewHolderConfirm = new ViewHolderConfirm(popupWindow_view);
    }


    /**
     * ConfirmPop
     */
    private void initDeliveryPop() {
        @SuppressLint("InflateParams") View popupWindow_view = LayoutInflater.from(mActivity).inflate(
                R.layout.pop_layout_sureorder_dialog, null, false);
        // 创建PopupWindow实例
        popupWindowDelivery = new PopupWindow(popupWindow_view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindowDelivery.setAnimationStyle(R.style.popWindow_anim_style);
        popupWindowDelivery.setFocusable(true);
        popupWindowDelivery.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindowDelivery.setOnDismissListener(() -> {
            //设置背景及展示
            mView.showDialogBg(false);
        });
        //初始化组件
        viewHolderDelivery = new ViewHolderDelivery(popupWindow_view);
    }


    //线下支付方式  网点 订单详情
    private void getData(String orderId) {
        if (!StringUtil.checkStr(orderId)) {
            return;
        }
        subscription = api.getOfflinePayType()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(offlinePayTypeResult -> {//线下支付
                    offlinePayType = offlinePayTypeResult.offlinePayType;
                    if (offlinePayType != null && !offlinePayType.isEmpty()) {
                        checkedMapPayType.put("3", true);//默认选中现金
                        if (viewHolderConfirm.payWayGridView != null) {
                            PayWayAdapter popSkusAdapter = new PayWayAdapter(mActivity);
                            viewHolderConfirm.payWayGridView.setAdapter(popSkusAdapter);
                            popSkusAdapter.bindData(offlinePayType);
                        }
                    }
                    return api.getOfflineStateList();
                })
                .flatMap(offlineStateListResult -> {
                    rsCs = offlineStateListResult.RSCs;
                    return api.getOrderDetail(orderId);
                })
                .subscribe(orderDetailResult -> {  //order detail
                    OrderDetailResult.DatasBean datas = orderDetailResult.datas;
                    if (datas != null && datas.order != null) {
                        //收货人和金额
                        if (viewHolderConfirm.recipientName != null && viewHolderConfirm.checkPrice != null) {
                            viewHolderConfirm.recipientName.setText(datas.consigneeName);

                            if (datas.order.payment != null) {
                                viewHolderConfirm.checkPrice.setText("¥" + StringUtil.toTwoString(datas.order.payment.price + "") + "元");
                                offlinePaymentId = datas.order.payment.id;
                            }
                        }
                    }
                    //默认一个付款网点
                    if (viewHolderConfirm.popPayState != null && viewHolderConfirm.popSave != null) {
                        if (datas != null && datas.RSCInfo != null) {
                            viewHolderConfirm.popPayState.setText(datas.RSCInfo.companyName);
                            if (StringUtil.checkStr(datas.RSCInfo.RSC)) {
                                checkedMapState.put(datas.RSCInfo.RSC, true);
                                viewHolderConfirm.popSave.setEnabled(true);
                            }
                        } else {
                            viewHolderConfirm.popPayState.setText("");
                            viewHolderConfirm.popSave.setEnabled(false);
                        }
                    }
                }, Throwable::printStackTrace);

    }






    @Override
    public void detachView() {
        super.detachView();
        if (subscription!=null&&!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    class ViewHolderConfirm {
        @BindView(R.id.pop_title)
        TextView popTitle;
        @BindView(R.id.pop_close)
        ImageView popClose;
        @BindView(R.id.recipient_name)
        TextView recipientName;
        @BindView(R.id.check_price)
        TextView checkPrice;
        @BindView(R.id.pop_pay_state)
        TextView popPayState;
        @BindView(R.id.pop_pay_state_ll)
        LinearLayout popPayStateLl;
        @BindView(R.id.pay_way_gridView)
        UnSwipeGridView payWayGridView;
        @BindView(R.id.pop_lin_step1)
        LinearLayout popLinStep1;
        @BindView(R.id.pop_lin_step2_listView)
        ListView popLinStep2ListView;
        @BindView(R.id.pop_lin_step2)
        LinearLayout popLinStep2;
        @BindView(R.id.pop_save)
        TextView popSave;

        ViewHolderConfirm(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.pop_close)
        void pop_close() {
            if (null != popupWindowConfirm && popupWindowConfirm.isShowing()) {
                popupWindowConfirm.dismiss();
            }
        }

        @OnClick(R.id.pop_pay_state_ll)
        void pop_pay_state_ll() {
            if (popLinStep2.getVisibility() == View.GONE) {
                ShowHideUtils.hideLeftFadeIn(popLinStep1);
                ShowHideUtils.showRightFadeOut(popLinStep2);
                popLinStep1.setVisibility(View.GONE);
                popLinStep2.setVisibility(View.VISIBLE);
                popTitle.setText("选择付款网点");
                confirm_pay_tag = true;
            }

            if (rsCs != null) {
                StateSpinnerAdapter adapter = new StateSpinnerAdapter(mActivity);
                popLinStep2ListView.setAdapter(adapter);
                adapter.bindData(rsCs);
            }
        }

        @OnClick(R.id.pop_save)
        void setPopClose() {
            if (confirm_pay_tag) {
                ShowHideUtils.hideLeftFadeIn(popLinStep2);
                ShowHideUtils.showRightFadeOut(popLinStep1);
                popLinStep2.setVisibility(View.GONE);
                popLinStep1.setVisibility(View.VISIBLE);
                popTitle.setText("审核付款");
                confirm_pay_tag = false;
            } else {
                if (checkedMapPayType != null && !checkedMapPayType.isEmpty()
                        && checkedMapState != null && !checkedMapState.isEmpty()) {
                    String offlinePayType = "3";
                    for (String key : checkedMapPayType.keySet()) {
                        if (checkedMapPayType.get(key)) {
                            offlinePayType = key;
                        }
                    }
                    String RscId = "";
                    for (String key : checkedMapState.keySet()) {
                        if (checkedMapState.get(key)) {
                            RscId = key;
                        }
                    }
                    //审核付款
                    api.confirmOfflinePay(offlinePaymentId, offlinePayType, RscId)
                            .subscribe(baseResult -> {
                                T.showCustomToast("审核成功", R.mipmap.toast_success_icon);
                                mView.operateSuccess();
                            }, throwable -> {
                            });
                }
                if (null != popupWindowConfirm && popupWindowConfirm.isShowing()) {
                    popupWindowConfirm.dismiss();
                }
            }
        }
    }


     class ViewHolderDelivery {
        @BindView(R.id.pop_order_title)
        TextView popOrderTitle;
        @BindView(R.id.pop_close)
        ImageView popClose;
        @BindView(R.id.pop_listView)
        ListView popListView;
        @BindView(R.id.save)
        TextView save;

        ViewHolderDelivery(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.pop_close)
        void pop_close(){
            if (null != popupWindowDelivery && popupWindowDelivery.isShowing()) {
                popupWindowDelivery.dismiss();
            }
        }
         @OnClick(R.id.save)
         void save(){
             if (checkedDelivery != null && !checkedDelivery.isEmpty()) {
                 List<Object> list = new ArrayList<>();
                 for (String key : checkedDelivery.keySet()) {
                     if (checkedDelivery.get(key)) {
                         Map<String, Object> map = new HashMap<>();
                         map.put("ref", key);
                         map.put("deliverStatus", 4);
                         list.add(map);
                     }
                 }
                 if (StringUtil.checkStr(deliveringOrderId)) {
                   api.skuDelivery(list,deliveringOrderId)
                           .subscribe(baseResult -> {
                               T.showCustomToast("发货成功", R.mipmap.toast_success_icon);
                               mView.operateSuccess();
                           }, throwable -> {});
                 }
             }
             if (null != popupWindowDelivery && popupWindowDelivery.isShowing()) {
                 popupWindowDelivery.dismiss();
             }

         }

    }



    /**
     * 线下支付方式
     */
    class PayWayAdapter extends CommonAdapter<OfflinePayTypeResult.OfflinePayTypeBean> {
        public PayWayAdapter(Activity context) {
            super(R.layout.item_rsc_pay_way_gird_layout, context);
        }


        @Override
        public void convert(final CommonViewHolder holder, final OfflinePayTypeResult.OfflinePayTypeBean offlinePayTypeEntity) {
            if (offlinePayTypeEntity != null) {

                final CheckBox checkBox = holder.getView(R.id.offline_pay_way_checkBox);
                checkBox.setText(offlinePayTypeEntity.name);
                //如果选中，集合中的值为true 否则为false
                checkBox.setOnClickListener(v -> {
                    // 重置，确保最多只有一项被选中
                    for (String key : checkedMapPayType.keySet()) {
                        checkedMapPayType.put(key, false);
                    }
                    checkedMapPayType.put(offlinePayTypeEntity.type + "", true);
                    PayWayAdapter.this.notifyDataSetChanged();
                });

                //根据map刷新适配器的选中状态
                Boolean res = false;
                if (checkedMapPayType.get(offlinePayTypeEntity.type + "") != null) {
                    res = checkedMapPayType.get(offlinePayTypeEntity.type + "");
                }
                checkBox.setChecked(res);
            }
        }
    }

    /**
     * 线下支付网点
     */
    class StateSpinnerAdapter extends CommonAdapter<OfflineStateListResult.RSCsBean> {
        public StateSpinnerAdapter(Activity context) {
            super(R.layout.item_for_spinner, context);
        }

        @Override
        public void convert(CommonViewHolder holder, final OfflineStateListResult.RSCsBean rsCsBean) {

            if (rsCsBean != null && rsCsBean.RSCInfo != null) {
                if (StringUtil.checkStr(rsCsBean.RSCInfo.companyName)) {
                    holder.setText(R.id.spinner_text, rsCsBean.RSCInfo.companyName);
                } else {
                    holder.setText(R.id.spinner_text, "");
                }

                final CheckBox checkBox = holder.getView(R.id.btn_consignees_item);
                //如果选中，集合中的值为true 否则为false
                holder.getConvertView().setOnClickListener(v -> {
                    // 重置，确保最多只有一项被选中
                    for (String key : checkedMapState.keySet()) {
                        checkedMapState.put(key, false);
                    }
                    checkedMapState.put(rsCsBean._id, true);
                    StateSpinnerAdapter.this.notifyDataSetChanged();
                    viewHolderConfirm.popPayState.setText(rsCsBean.RSCInfo.companyName);
                    viewHolderConfirm.popSave.setEnabled(true);
                });

                //根据map刷新适配器的选中状态
                Boolean res = false;
                if (checkedMapState.get(rsCsBean._id) != null) {
                    res = checkedMapState.get(rsCsBean._id);
                }
                checkBox.setChecked(res);
            }
        }
    }

    /**
     * 发货到服务站
     */
    class PopSkusDeliveryAdapter extends CommonAdapter<OrderListResult.DatasBean.ItemsBean.SKUsBean> {
        private List<OrderListResult.DatasBean.ItemsBean.SKUsBean> list;

        public PopSkusDeliveryAdapter(Context context, List<OrderListResult.DatasBean.ItemsBean.SKUsBean> data) {
            super( R.layout.item_pop_sureorder_layout,context);
            this.list = data;
            bindData(data);
        }

        @Override
        public void convert(CommonViewHolder holder, final OrderListResult.DatasBean.ItemsBean.SKUsBean skus) {

            if (skus != null) {
                //商品个数
                holder.setText(R.id.sku_count, "X " + skus.count + "");
                //商品名
                if (StringUtil.checkStr(skus.productName)) {
                    holder.setText(R.id.sku_name, skus.productName);
                } else {
                    holder.setText(R.id.sku_name, "");
                }
                //Sku属性
                StringBuilder stringBuilder = new StringBuilder();
                if (skus.attributes != null && !skus.attributes.isEmpty()) {
                    for (int k = 0; k < skus.attributes.size(); k++) {
                        if (StringUtil.checkStr(skus.attributes.get(k).name)
                                && StringUtil.checkStr(skus.attributes.get(k).value)) {
                            stringBuilder.append(skus.attributes.get(k).name).append(":").append(skus.attributes.get(k).value).append(";");
                        }
                    }
                    String car_attr = stringBuilder.substring(0, stringBuilder.length() - 1);
                    if (StringUtil.checkStr(car_attr)) {
                        holder.setText(R.id.sku_attr, car_attr);
                    } else {
                        holder.setText(R.id.sku_attr, "");
                    }
                } else {
                    holder.setText(R.id.sku_attr, "");
                }

                //附加选项
                TextView sku_addiction =  holder.getView(R.id.sku_addiction);

                StringBuilder stringAdditions = new StringBuilder();
                if (skus.additions != null && !skus.additions.isEmpty()) {
                    stringAdditions.append("附加项目:");
                    for (int k = 0; k < skus.additions.size(); k++) {
                        if (StringUtil.checkStr(skus.additions.get(k).name)) {
                            stringAdditions.append(skus.additions.get(k).name).append(";");
                        }
                    }
                    String car_additions = stringAdditions.substring(0, stringAdditions.length() - 1);
                    if (StringUtil.checkStr(car_additions)) {
                        sku_addiction.setVisibility(View.VISIBLE);
                        sku_addiction.setText(car_additions);
                    } else {
                        sku_addiction.setText("");
                    }
                } else {
                    sku_addiction.setVisibility(View.GONE);
                }


                //CheckBox
                final CheckBox checkBox = holder.getView(R.id.btn_surr_order_item);

                holder.getConvertView().setOnClickListener(v -> {
                    if (checkBox.isChecked()) {
                        checkedDelivery.put(skus.ref,
                                false);
                    } else {
                        checkedDelivery.put(skus.ref,
                                true);
                    }
                    //刷新适配器中的选中状态
                    notifyDataSetChanged();
                    //刷新确定按钮的选中数量
                    int count = setCheckedGoodsCount(list);
                    if (count != 0) {
                        viewHolderDelivery.save.setText("确定(" + count + ")");
                        viewHolderDelivery.save.setEnabled(true);
                    } else {
                        viewHolderDelivery.save.setText("确定");
                        viewHolderDelivery.save.setEnabled(false);
                    }
                });
                Boolean res = checkedDelivery.get(skus.ref);
                if (res != null && res) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }

        }
    }


    //确定选中按钮的数量
    private int setCheckedGoodsCount(List<OrderListResult.DatasBean.ItemsBean.SKUsBean> list) {
        int count = 0;
        List<String> refList = new ArrayList<>();
        for (String key : checkedDelivery.keySet()) {
            if (checkedDelivery.get(key)) {
                refList.add(key);
            }
        }
        for (OrderListResult.DatasBean.ItemsBean.SKUsBean skus : list) {
            if (refList.contains(skus.ref)) {
                count += skus.count;
            }
        }
        return count;
    }


}

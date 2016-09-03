package com.app.xxnr.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.xxnr.Constants;
import com.app.xxnr.R;
import com.app.xxnr.bean.OrderDetailResult;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.xxnr.WidgetUtil;
import com.app.xxnr.widget.RecyclerImageView;
import com.app.xxnr.widget.UnSwipeListView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/24 18:27
 * 邮箱：hepeng@xinxinnongren.com
 */
//商品列表(方法特殊 暂时继承BaseAdapter)
public class OrderCarAdapter extends BaseAdapter {

    private List<OrderDetailResult.DatasBean.ProductBean> goodsList;
    private List<OrderDetailResult.DatasBean.SKUsBean> SKUsList;
    private boolean flag;
    private Context context;

    public OrderCarAdapter(Context context, List<OrderDetailResult.DatasBean.ProductBean> goodsList, boolean flag) {
        this.goodsList = goodsList;
        this.flag = flag;
        this.context = context;
    }

    public OrderCarAdapter(Context context, boolean flag, List<OrderDetailResult.DatasBean.SKUsBean> SKUsList) {
        this.flag = flag;
        this.SKUsList = SKUsList;
        this.context = context;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (flag) {
            return SKUsList.size() > 0 ? SKUsList.size() : 0;
        } else {
            return goodsList.size() > 0 ? goodsList.size() : 0;
        }
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (flag) {
            return SKUsList.get(position);
        } else {
            return goodsList.get(position);
        }

    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    class ViewHolder {
        private LinearLayout goods_car_bar;
        private RecyclerImageView ordering_item_img;
        private TextView ordering_now_pri, ordering_item_name, goods_car_deposit,
                goods_car_weikuan, ordering_item_geshu, ordering_item_attr, ordering_item_orderType;
        private UnSwipeListView additions_listView;


        public ViewHolder(View convertView) {
            goods_car_bar = (LinearLayout) convertView.findViewById(R.id.goods_car_item_bar);
            ordering_item_img = (RecyclerImageView) convertView//商品图
                    .findViewById(R.id.ordering_item_img);
            ordering_item_geshu = (TextView) convertView//商品个数
                    .findViewById(R.id.ordering_item_geshu);
            ordering_now_pri = (TextView) convertView//商品价格
                    .findViewById(R.id.ordering_now_pri);
            ordering_item_name = (TextView) convertView//商品名
                    .findViewById(R.id.ordering_item_name);
            ordering_item_attr = (TextView) convertView  //商品sku属性
                    .findViewById(R.id.ordering_item_attr);
            ordering_item_orderType = (TextView) convertView//商品发货状态
                    .findViewById(R.id.ordering_item_orderType);
            goods_car_deposit = (TextView) convertView//汽车定金
                    .findViewById(R.id.goods_car_item_bar_deposit);
            goods_car_weikuan = (TextView) convertView//汽车尾款
                    .findViewById(R.id.goods_car_item_bar_weikuan);
            additions_listView = (UnSwipeListView) convertView//附加选项
                    .findViewById(R.id.additions_listView);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_item_order_list, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();


        if (flag) {
            //商品图片
            if (StringUtil.checkStr(SKUsList.get(position).thumbnail)) {
                Glide.with(context)
                        .load(Constants.BASE_URL + SKUsList.get(position).thumbnail)
                        .error(R.mipmap.error)
                        .placeholder(R.mipmap.zhanweitu)
                        .into(holder.ordering_item_img);
            }
            //商品个数
            holder.ordering_item_geshu.setText("X " + SKUsList.get(position).count + "");
            //商品名
            if (StringUtil.checkStr(SKUsList.get(position).productName)) {
                holder.ordering_item_name.setEms(10);
                holder.ordering_item_name.setText(SKUsList.get(position).productName);
            }
            //商品发货状态
            if (SKUsList.get(position).deliverStatus != 0) {
                holder.ordering_item_orderType.setVisibility(View.VISIBLE);
                if (SKUsList.get(position).deliverStatus == 1) {
                    holder.ordering_item_orderType.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.ordering_item_orderType.setText("未发货");
                } else if (SKUsList.get(position).deliverStatus == 2) {
                    holder.ordering_item_orderType.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.ordering_item_orderType.setText("配送中");
                } else if (SKUsList.get(position).deliverStatus == 4) {
                    holder.ordering_item_orderType.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.ordering_item_orderType.setText("已到服务站");
                } else if (SKUsList.get(position).deliverStatus == 5) {
                    holder.ordering_item_orderType.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.ordering_item_orderType.setText("已收货");
                }
            }
            //附加选项的总价
            double car_additions_price = 0;
            List<OrderDetailResult.DatasBean.SKUsBean.Additions> additions = SKUsList.get(position).additions;
            if (additions != null && !additions.isEmpty()) {
                for (int k = 0; k < additions.size(); k++) {
                    if (StringUtil.checkStr(additions.get(k).name)) {
                        car_additions_price += additions.get(k).price;
                    }
                }
            }

            if (SKUsList.get(position).deposit == 0) {
                holder.goods_car_bar.setVisibility(View.GONE);
            } else {
                holder.goods_car_bar.setVisibility(View.VISIBLE);
                String deposit = StringUtil.toTwoString(SKUsList
                        .get(position).deposit * SKUsList.get(position).count + "");
                if (StringUtil.checkStr(deposit)) {
                    holder.goods_car_deposit.setText("¥" + deposit);
                }
                String weiKuan = StringUtil.toTwoString((SKUsList.get(position).price + car_additions_price - SKUsList
                        .get(position).deposit) * SKUsList.get(position).count + "");
                if (StringUtil.checkStr(weiKuan)) {
                    holder.goods_car_weikuan.setText("¥" + weiKuan);
                }
            }
            holder.ordering_now_pri.setText("¥" + StringUtil.toTwoString(SKUsList
                    .get(position).price + ""));

            //Sku属性
            StringBuilder stringBuilder = new StringBuilder();
            if (SKUsList.get(position).attributes != null && !SKUsList.get(position).attributes.isEmpty()) {
                for (int k = 0; k < SKUsList.get(position).attributes.size(); k++) {
                    if (StringUtil.checkStr(SKUsList.get(position).attributes.get(k).name)
                            && StringUtil.checkStr(SKUsList.get(position).attributes.get(k).value)) {
                        stringBuilder.append(SKUsList.get(position).attributes.get(k).name).append(":").append(SKUsList.get(position).attributes.get(k).value).append(";");
                    }
                }
                String car_attr = stringBuilder.substring(0, stringBuilder.length() - 1);
                if (StringUtil.checkStr(car_attr)) {
                    holder.ordering_item_attr.setText(car_attr);
                }
            }
            //附加选项
            if (additions != null && !additions.isEmpty()) {
                holder.additions_listView.setVisibility(View.VISIBLE);
                AdditionsAdapter adapter = new AdditionsAdapter(context);
                holder.additions_listView.setAdapter(adapter);
                adapter.bindData(additions);
                WidgetUtil.setListViewHeightBasedOnChildren(holder.additions_listView);
            } else {
                holder.additions_listView.setVisibility(View.GONE);
            }


        } else {
            //商品图片
            if (StringUtil.checkStr(goodsList.get(position).thumbnail)) {

                Glide.with(context)
                        .load(Constants.BASE_URL + goodsList.get(position).thumbnail)
                        .error(R.mipmap.error)
                        .placeholder(R.mipmap.zhanweitu)
                        .into(holder.ordering_item_img);
            }
            //商品个数
            holder.ordering_item_geshu.setText("X " + goodsList.get(position).count + "");
            //商品名
            if (StringUtil.checkStr(goodsList.get(position).name)) {
                holder.ordering_item_name.setEms(10);
                holder.ordering_item_name.setText(goodsList.get(position).name);
            }
            //商品发货状态
            if (StringUtil.checkStr(goodsList.get(position).deliverStatus)) {
                holder.ordering_item_orderType.setVisibility(View.VISIBLE);
                if (goodsList.get(position).deliverStatus.equals("1")) {
                    holder.ordering_item_orderType.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.ordering_item_orderType.setText("未发货");
                } else if (goodsList.get(position).deliverStatus.equals("2")) {
                    holder.ordering_item_orderType.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.ordering_item_orderType.setText("配送中");
                } else if (goodsList.get(position).deliverStatus.equals("4")) {
                    holder.ordering_item_orderType.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.ordering_item_orderType.setText("已到服务站");
                } else if (goodsList.get(position).deliverStatus.equals("5")) {
                    holder.ordering_item_orderType.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.ordering_item_orderType.setText("已收货");
                }
            }

            if (goodsList.get(position).deposit == 0) {
                holder.goods_car_bar.setVisibility(View.GONE);
            } else {
                holder.goods_car_bar.setVisibility(View.VISIBLE);
                String deposit = StringUtil.toTwoString(goodsList
                        .get(position).deposit * goodsList.get(position).count + "");
                if (StringUtil.checkStr(deposit)) {
                    holder.goods_car_deposit.setText("¥" + deposit);
                    String weiKuan = StringUtil.toTwoString((goodsList.get(position).price - goodsList
                            .get(position).deposit) * goodsList.get(position).count + "");
                    if (StringUtil.checkStr(weiKuan)) {
                        holder.goods_car_weikuan.setText("¥" + weiKuan);
                    }
                }
                String now_pri = StringUtil.toTwoString(goodsList
                        .get(position).price + "");
                if (StringUtil.checkStr(now_pri)) {
                    holder.ordering_now_pri.setText("¥" + now_pri);
                }
            }

        }
        return convertView;
    }

    class AdditionsAdapter extends CommonAdapter<OrderDetailResult.DatasBean.SKUsBean.Additions> {


        public AdditionsAdapter(Context context) {
            super(R.layout.item_for_additions_layout, context);
        }


        @Override
        public void convert(CommonViewHolder holder, OrderDetailResult.DatasBean.SKUsBean.Additions additions) {
            if (additions != null) {
                if (StringUtil.checkStr(additions.name)) {
                    holder.setText(R.id.item_additions_name, additions.name);
                    holder.setText(R.id.item_additions_price, "¥" + StringUtil.toTwoString(additions.price + ""));
                } else {
                    holder.setText(R.id.item_additions_name, "");
                    holder.setText(R.id.item_additions_price, "");
                }
            }

        }
    }


}
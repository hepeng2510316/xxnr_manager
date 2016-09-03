package com.app.xxnr.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 14:48
 * 邮箱：hepeng@xinxinnongren.com
 */
public class DataCenterQuestionFragment extends BaseFragment {


    @BindView(R.id.title_tv1)
    TextView titleTv1;
    @BindView(R.id.content_tv1)
    TextView contentTv1;
    @BindView(R.id.title_tv2)
    TextView titleTv2;
    @BindView(R.id.content_tv2)
    TextView contentTv2;


    String dailyTitle1 = "1. 每日统计指标";
    String dailyContent1 = "" +
            "- 注册用户数：当日新注册的用户数\n" +
            "- 新增经纪人：当日被认证为经纪人的用户数\n" +
            "- 新增订单数：当日新下单的订单数\n" +
            "- 付款订单数：当日新订单中，部分付款或已付款的订单\n" +
            "- 已支付金额：当日新订单中，合计共支付的金额";
    String dailyTitle2 = "2. 累计数据指标";
    String dailyContent2 = "" +
            "- 总用户数：累计注册用户数\n" +
            "- 总经纪人数：当前用户中，被认证为经纪人的用户数\n" +
            "- 总订单数：累计订单数\n" +
            "- 已完成订单数：累计已完成的订单数量\n" +
            "- 已完成金额：累计已支付的金额（元）";


    String weekTitle1 = "1. 本周统计指标（本周一~当天实时；每小时实时更新）";
    String weekContent1 = "" +
            "- 注册用户数：本周新注册的用户数\n" +
            "- 新增经纪人：本周被认证为经纪人的用户数\n" +
            "- 新增订单数：本周新下单的订单数\n" +
            "- 付款订单数：本周新订单中，部分付款或已付款的订单\n" +
            "- 已支付金额：本周新订单中，合计共支付的金额";
    String weekTitle2 = "2. 每周统计指标（周一 ~ 周日）";
    String weekContent2 = "" +
            "- 注册用户数：本周新注册的用户数\n" +
            "- 新增经纪人：本周被认证为经纪人的用户数\n" +
            "- 新增订单数：本周新下单的订单数\n" +
            "- 付款订单数：本周新订单中，部分付款或已付款的订单\n" +
            "- 已支付金额：本周新订单中，合计共支付的金额";


    String agentTitle1 = "1. 昨日排行指标";
    String agentContent1 = "" +
            "a) 前一天的数据（每天早上4点更新）\n" +
            "- 昨日新增客户：新绑定该经纪人为代表的用户数\n" +
            "- 昨日登记客户：新登记的潜在客户数\n" +
            "\n" +
            "b) 累计统计数据（每天早上4点更新）\n" +
            "- 总客户：该经纪人共绑定的客户数\n" +
            "- 总经纪人数：该经纪人的总客户中，被认证为经纪人的用户数\n" +
            "- 总登记客户：该经纪人共登记的客户数\n" +
            "- 已完成订单：该经纪人的客户所下订单中，累计已完成的订单数\n" +
            "- 已支付金额：该经纪人的客户所下订单中，累计已完成的订单的总支付金额";
    String agentTitle2 = "2. 汇总排行指标";
    String agentContent2 = "" +
            "a) 业绩进度汇总指标：\n" +
            "- 新增客户：选择时间段内，新绑定该经纪人为代表的用户数\n" +
            "- 新增经纪人：选择时间段内，新增客户中，被认证为经纪人的用户数\n" +
            "- 新登记客户：选择时间段内，新登记的潜在客户数\n" +
            "- 新增订单数：选择时间段内，该经纪人的客户新增的订单数（客户只要下了订单，就计算）\n" +
            "- 新增订单完成数：上述新增订单中，已完成的订单数\n" +
            "\n" +
            "b) 完成订单汇总指标：\n" +
            "- 完成订单数：选择时间段内，被完成的订单数（与下单时间无关）\n" +
            "- 完成订单金额：选择时间段内，被完成的订单中，订单的总支付金额";


    private int position;

    public static DataCenterQuestionFragment newInstance(int position) {
        DataCenterQuestionFragment mFragment = new DataCenterQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_question_layout;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void getBundle(Bundle bundle) {
        position = bundle.getInt("position");
    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        showContent(true);
        switch (position) {
            case 0:
                titleTv1.setText(dailyTitle1);
                titleTv2.setText(dailyTitle2);
                contentTv1.setText(dailyContent1);
                contentTv2.setText(dailyContent2);
                break;
            case 1:
                titleTv1.setText(weekTitle1);
                titleTv2.setText(weekTitle2);
                contentTv1.setText(weekContent1);
                contentTv2.setText(weekContent2);
                break;
            case 2:
                titleTv1.setText(agentTitle1);
                titleTv2.setText(agentTitle2);
                contentTv1.setText(agentContent1);
                contentTv2.setText(agentContent2);
                break;
        }
    }

    @Override
    public void initData() {

    }


}

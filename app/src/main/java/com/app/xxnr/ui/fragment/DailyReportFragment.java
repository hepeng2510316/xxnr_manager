package com.app.xxnr.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.DailyReportResult;
import com.app.xxnr.bean.StatisticReportResult;
import com.app.xxnr.contract.DailyReportContract;
import com.app.xxnr.event.DailyDateSelectChange;
import com.app.xxnr.presenter.DailyReportPresenter;
import com.app.xxnr.ui.BaseFragment;
import com.app.xxnr.ui.activity.datacenter.DailyDetailActivity;
import com.app.xxnr.ui.activity.datacenter.DailyPickerActivity;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;
import com.app.xxnr.utils.xxnr.BgSelectorUtils;
import com.app.xxnr.utils.xxnr.DataCenterUtils;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 16:27
 * 邮箱：hepeng@xinxinnongren.com
 */
public class DailyReportFragment extends BaseFragment implements DailyReportContract.View{
    @BindView(R.id.date_before)
    TextView dateBefore;
    @BindView(R.id.date_picker)
    TextView datePicker;
    @BindView(R.id.date_after)
    TextView dateAfter;
    @BindView(R.id.reg_count_1)
    TextView regCount1;
    @BindView(R.id.order_count_1)
    TextView orderCount1;
    @BindView(R.id.order_paid_count_1)
    TextView orderPaidCount1;
    @BindView(R.id.pay_price_1)
    TextView payPrice1;
    @BindView(R.id.reg_count_2)
    TextView regCount2;
    @BindView(R.id.agent_reg_count_2)
    TextView agentRegCount2;
    @BindView(R.id.order_count_2)
    TextView orderCount2;
    @BindView(R.id.order_paid_count_2)
    TextView orderPaidCount2;
    @BindView(R.id.pay_price_2)
    TextView payPrice2;
    @BindView(R.id.statistic_ll)
    LinearLayout statisticLl;

    @Inject
    DailyReportPresenter mPresenter;

    @Inject
    EventBus mBus;

    private ColorStateList colorStateList;


    @Override
    public int initContentView() {
        return R.layout.fragment_daily_report;
    }

    @Override
    public void initInjector() {
        mFragmentComponet.inject(this);
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mBus.register(this);
        mPresenter.attachView(this);
        colorStateList = BgSelectorUtils.createColorStateList(activity.getResources().getColor(R.color.default_black), activity.getResources().getColor(R.color.deep_black));
        if (colorStateList != null) {
            dateBefore.setTextColor(colorStateList);
            dateAfter.setTextColor(colorStateList);
        }

    }

    @Override
    public void initData() {
        //默认展示今天的日期
        String today = DataCenterUtils.getCurrDateStr(DataCenterUtils.CHINESE_DATE_FORMAT);
        datePicker.setText(today);
        dateAfter.setTextColor(getResources().getColor(R.color.date_unable));//默认当天不可点击

        RxTextView.textChangeEvents(datePicker)
                .compose(this.bindToLifecycle())
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewTextChangeEvent -> {
                    String s = textViewTextChangeEvent.text().toString().trim();
                    if (s.equals(DataCenterUtils.getCurrDateStr(DataCenterUtils.CHINESE_DATE_FORMAT))) {
                        statisticLl.setVisibility(View.VISIBLE);
                        dateAfter.setTextColor(getResources().getColor(R.color.date_unable));
                    } else {
                        statisticLl.setVisibility(View.GONE);
                        if (colorStateList != null) {
                            dateAfter.setTextColor(colorStateList);
                        }
                    }
                    if (s.equals(DataCenterUtils.startDateStr)) {
                        dateBefore.setTextColor(getResources().getColor(R.color.date_unable));
                    } else {
                        if (colorStateList != null) {
                            dateBefore.setTextColor(colorStateList);
                        }

                    }
                });

        mPresenter.getDailyReport(DataCenterUtils.changeDateFormat(datePicker.getText().toString()));
        mPresenter.getStatistic();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dateSelectChange(DailyDateSelectChange change){
        String dateToString = DataCenterUtils.dateToString(change.getDate(), DataCenterUtils.CHINESE_DATE_FORMAT);
        if (StringUtil.checkStr(dateToString)) {
            datePicker.setText(dateToString);
            mPresenter.getDailyReport(DataCenterUtils.changeDateFormat(dateToString));
        }
    }


    @OnClick(R.id.date_picker)
    void date_picker() {
        Intent intent = DailyPickerActivity.startActivity(activity, false, true);
        intent.putExtra("selectDate", datePicker.getText().toString());
        startActivity(intent);
    }

    @OnClick(R.id.date_before)
    void date_before() {
        if (StringUtil.checkStr(datePicker.getText().toString())) {
            String res = DataCenterUtils.dateAddOrDec(datePicker.getText().toString(), -1);
            if (res != null) {
                if (DataCenterUtils.stringtoDate(res, DataCenterUtils.CHINESE_DATE_FORMAT).getTime()
                        < DataCenterUtils.stringtoDate(DataCenterUtils.startDateStr, DataCenterUtils.CHINESE_DATE_FORMAT).getTime()) {
                    T.showShort("已经是第一天");
                } else {
                    datePicker.setText(res);
                    mPresenter.getDailyReport(DataCenterUtils.changeDateFormat(res));
                }
            }
        }
    }

    @OnClick(R.id.date_after)
    void date_after() {
        if (StringUtil.checkStr(datePicker.getText().toString())) {
            String res = DataCenterUtils.dateAddOrDec(datePicker.getText().toString(), 1);
            if (res != null) {
                if (DataCenterUtils.stringtoDate(res, DataCenterUtils.CHINESE_DATE_FORMAT).getTime()
                        >= DataCenterUtils.getCurrDate().getTime()) {
                    T.showShort("已经是最后一天");
                } else {
                    datePicker.setText(res);
                    mPresenter.getDailyReport(DataCenterUtils.changeDateFormat(res));
                }
            }
        }
    }

    @OnClick(R.id.reg_count_ll_1)
    void reg_count_ll_1() {
        DailyDetailActivity.startActivity(activity, "用户及经纪人",datePicker.getText().toString());
    }

    @OnClick(R.id.order_count_ll_1)
    void order_count_ll_1() {
        DailyDetailActivity.startActivity(activity, "订单数",datePicker.getText().toString());
    }

    @OnClick(R.id.order_paid_count_ll_1)
    void order_paid_count_ll_1() {
        DailyDetailActivity.startActivity(activity, "付款订单数",datePicker.getText().toString());
    }

    @OnClick(R.id.pay_price_ll_1)
    void pay_price_ll_1() {
        DailyDetailActivity.startActivity(activity, "已支付金额",datePicker.getText().toString());
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showContent(true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void renderDailyReport(DailyReportResult result) {
        regCount1.setText(result.registeredUserCount + "");
        orderCount1.setText(result.orderCount + "");
        orderPaidCount1.setText(result.paidOrderCount + "");
        payPrice1.setText("¥" + StringUtil.toTwoString(result.paidAmount + ""));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void renderStatistic(StatisticReportResult result) {
        regCount2.setText(result.registeredUserCount + "");
        agentRegCount2.setText(result.agentVerifiedCount+"");
        orderCount2.setText(result.orderCount + "");
        orderPaidCount2.setText(result.completedOrderCount + "");
        payPrice2.setText("¥" + StringUtil.toTwoString(result.completedOrderPaidAmount + ""));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
        mPresenter.detachView();
    }
}

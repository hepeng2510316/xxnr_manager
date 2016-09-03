package com.app.xxnr.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.api.ApiService;
import com.app.xxnr.event.WeekDateSelectChange;
import com.app.xxnr.ui.BaseFragment;
import com.app.xxnr.ui.activity.datacenter.WeekDetailActivity;
import com.app.xxnr.ui.activity.datacenter.WeekPickerActivity;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;
import com.app.xxnr.utils.xxnr.BgSelectorUtils;
import com.app.xxnr.utils.xxnr.DataCenterUtils;
import com.app.xxnr.utils.xxnr.WeekBean;
import com.trello.rxlifecycle.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 18:09
 * 邮箱：hepeng@xinxinnongren.com
 */
public class WeekReportFragment extends BaseFragment {
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

    private ColorStateList colorStateList;
    private List<WeekBean> weekList;
    private int index;

    @Inject
    ApiService api;

    @Inject
    EventBus mbus;

    @Override
    public int initContentView() {
        return R.layout.fragment_week_report;
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
        mbus.register(this);

        //设置selector
        colorStateList = BgSelectorUtils.createColorStateList(activity.getResources().getColor(R.color.default_black), activity.getResources().getColor(R.color.deep_black));
        if (colorStateList != null) {
            dateBefore.setTextColor(colorStateList);
            dateAfter.setTextColor(colorStateList);
        }
    }

    @Override
    public void initData() {
        //初始化列表
        weekList = DataCenterUtils.getWeekList();
        if (weekList != null && !weekList.isEmpty()) {
            index = weekList.size() - 1;
        }
        //获得本周
        setDate_picker();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dateSelectChange(WeekDateSelectChange change) {
        index = change.getIndex();
        setDate_picker();
    }

    @SuppressLint("SetTextI18n")
    private void setDate_picker() {
        if (weekList != null && !weekList.isEmpty()) {
            if (index >= 0 && index < weekList.size()) {

                WeekBean weekBean = weekList.get(index);
                String subStart = DataCenterUtils.dateToString(weekBean.dateBegin, DataCenterUtils.SHORT_DATE_FORMAT);
                String subEnd = DataCenterUtils.dateToString(weekBean.dateEnd, DataCenterUtils.SHORT_DATE_FORMAT);
                datePicker.setText(subStart + "-" + subEnd);
                if (index == 0) {
                    dateBefore.setTextColor(getResources().getColor(R.color.date_unable));
                } else if (index == weekList.size() - 1) {
                    dateAfter.setTextColor(getResources().getColor(R.color.date_unable));
                } else {
                    dateBefore.setTextColor(colorStateList);
                    dateAfter.setTextColor(colorStateList);
                }
                //获得周数据
                getData(weekBean.dateBegin);
            }
        }

    }

    private void getData(Date inWeekDate) {

        if (inWeekDate != null) {
            String dateToString = DataCenterUtils.dateToString(inWeekDate, DataCenterUtils.UNDERLINE_DATE_FORMAT);
            if (!StringUtil.checkStr(dateToString)) {
                return;
            }
            showProgress(true);
            api.getWeeklyReport(dateToString)
                    .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribe(everyWeekReportResult -> {
                        showContent(true);
                        regCount1.setText(everyWeekReportResult.registeredUserCount + "");
                        orderCount1.setText(everyWeekReportResult.orderCount + "");
                        orderPaidCount1.setText(everyWeekReportResult.paidOrderCount + "");
                        payPrice1.setText("¥" + StringUtil.toTwoString(everyWeekReportResult.paidAmount + ""));
                    }, throwable -> {
                        showContent(true);
                    });
        }

    }


    @OnClick({R.id.date_before, R.id.date_picker, R.id.date_after, R.id.reg_count_ll_1, R.id.order_count_ll_1, R.id.order_paid_count_ll_1, R.id.pay_price_ll_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_before:
                if (index > 0) {
                    index--;
                    setDate_picker();
                } else {
                    T.showShort("已经是第一周");
                }
                break;
            case R.id.date_after:
                if (weekList != null) {
                    if (index < weekList.size() - 1) {
                        index++;
                        setDate_picker();
                    } else {
                        T.showShort("已经是最后一周");
                    }
                }
                break;
            case R.id.date_picker:
                if (weekList != null) {
                    if (index >= 0 && index < weekList.size()) {
                        Intent intent = WeekPickerActivity.startActivity(activity, false);
                        intent.putExtra("index", index);
                        intent.putExtra("weekList", (Serializable) weekList);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.reg_count_ll_1:
                if (weekList != null) {
                    if (index >= 0 && index < weekList.size()) {
                        WeekDetailActivity.startActivity(activity,"用户及经纪人",index,weekList);
                    }
                }
                break;
            case R.id.order_count_ll_1:
                if (weekList != null) {
                    if (index >= 0 && index < weekList.size()) {
                        WeekDetailActivity.startActivity(activity,"订单数",index,weekList);
                    }
                }
                break;
            case R.id.order_paid_count_ll_1:
                if (weekList != null) {
                    if (index >= 0 && index < weekList.size()) {
                        WeekDetailActivity.startActivity(activity,"付款订单数",index,weekList);
                    }
                }
                break;
            case R.id.pay_price_ll_1:
                if (weekList != null) {
                    if (index >= 0 && index < weekList.size()) {
                        WeekDetailActivity.startActivity(activity,"已支付金额",index,weekList);
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mbus.unregister(this);
    }
}

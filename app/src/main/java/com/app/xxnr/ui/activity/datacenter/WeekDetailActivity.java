package com.app.xxnr.ui.activity.datacenter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.api.ApiService;
import com.app.xxnr.bean.SomeWeekReportResult;
import com.app.xxnr.bean.WeekReportResult;
import com.app.xxnr.event.DailyDateSelectRange;
import com.app.xxnr.event.WeekRangeSelect;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.CommonAdapter;
import com.app.xxnr.ui.adapter.CommonViewHolder;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.xxnr.DataCenterUtils;
import com.app.xxnr.utils.xxnr.WeekBean;
import com.app.xxnr.widget.UnSwipeListView;
import com.trello.rxlifecycle.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by 何鹏 on 2016/5/23.
 */
public class WeekDetailActivity extends BaseSwipeBackActivity {
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.chart)
    ColumnChartView chart;
    @BindView(R.id.column_tip_ll)
    LinearLayout columnTipLl;
    @BindView(R.id.list_title1_tv)
    TextView listTitle1Tv;
    @BindView(R.id.list_title2_tv)
    TextView listTitle2Tv;
    @BindView(R.id.list_title3_tv)
    TextView listTitle3Tv;
    @BindView(R.id.total_count_tv1)
    TextView totalCountTv1;
    @BindView(R.id.total_count_tv2)
    TextView totalCountTv2;
    @BindView(R.id.unSwipeListView)
    UnSwipeListView unSwipeListView;
    @BindView(R.id.data_detail_scrollView)
    ScrollView dataDetailScrollView;

    @Inject
    ApiService api;
    @Inject
    EventBus mBus;


    private String title;
    private String endDateStr;
    private String startStr;

    private int startIndex = -1;
    private int endIndex = -1;

    public static void startActivity(Context mContext, String title, int index, List<WeekBean> weekList) {
        Intent intent = new Intent(mContext, WeekDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("index", index);
        intent.putExtra("weekList", (Serializable) weekList);
        mContext.startActivity(intent);
    }


    @Override
    public int initContentView() {
        return R.layout.activity_day_week_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mBus.register(this);
        listTitle1Tv.setText("日期");
        dataDetailScrollView.setVisibility(View.GONE);

        chart.setZoomEnabled(false);
        chart.setValueSelectionEnabled(true);
        chart.setViewportCalculationEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unchecked")
    @Override
    public void initData() {

        title = getIntent().getStringExtra("title");
        endIndex = getIntent().getIntExtra("index", 0);

        //设置标题
        setTitle(title);
        if (title.equals("用户及经纪人")) {
            columnTipLl.setVisibility(View.VISIBLE);
            listTitle2Tv.setVisibility(View.VISIBLE);
            totalCountTv1.setVisibility(View.VISIBLE);
            listTitle2Tv.setText("注册用户数");
            listTitle3Tv.setText("新经纪人数");

        } else {
            if (title.equals("已支付金额")) {
                listTitle3Tv.setText("已支付金额(元)");
            } else {
                listTitle3Tv.setText(title);
            }
            columnTipLl.setVisibility(View.GONE);
            listTitle2Tv.setVisibility(View.GONE);
            totalCountTv1.setVisibility(View.GONE);
        }

        List<WeekBean> weekList = (List<WeekBean>) getIntent().getSerializableExtra("weekList");
        if (weekList != null && weekList.size() >= 6) {
            WeekBean weekBeanEnd = weekList.get(endIndex);
            WeekBean weekBeanStart;
            if (endIndex >= 6) {
                weekBeanStart = weekList.get(endIndex - 6);
                startIndex = endIndex - 6;
            } else {
                weekBeanStart = weekList.get(0);
                startIndex = 0;
            }

            startStr = DataCenterUtils.dateToString(weekBeanStart.dateBegin, DataCenterUtils.SHORT_DATE_FORMAT);
            endDateStr = DataCenterUtils.dateToString(weekBeanEnd.dateEnd, DataCenterUtils.SHORT_DATE_FORMAT);

            dateTv.setText(startStr + "-" + endDateStr);
            //获取一周数据
            getData(weekBeanStart.dateBegin, weekBeanEnd.dateEnd);
        }


    }


    /**
     * 获取并处理数据
     */
    private void getData(Date inWeekDateStart, Date inWeekDateEnd) {

        if (inWeekDateStart == null) {
            return;
        }
        if (inWeekDateEnd == null) {
            return;
        }
        String inWeekDateStartStr = DataCenterUtils.dateToString(inWeekDateStart, DataCenterUtils.UNDERLINE_DATE_FORMAT);
        String inWeekDateEndStr = DataCenterUtils.dateToString(inWeekDateEnd, DataCenterUtils.UNDERLINE_DATE_FORMAT);
        if (!StringUtil.checkStr(inWeekDateStartStr)) {
            return;
        }
        if (!StringUtil.checkStr(inWeekDateEndStr)) {
            return;
        }
        showProgress();
        api.queryWeeklyReport(inWeekDateStartStr, inWeekDateEndStr)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(someWeekReportResult -> {
                    dismissProgress();
                    dataDetailScrollView.setVisibility(View.VISIBLE);
                    List<SomeWeekReportResult.WeeklyReportsBean> weeklyReports = someWeekReportResult.weeklyReports;
                    if (weeklyReports != null && !weeklyReports.isEmpty()) {
                        //计算总和
                        int total_count_1 = 0;
                        int total_count_2 = 0;
                        double total_count_2f = 0;

                        for (int i = 0; i < weeklyReports.size(); i++) {
                            SomeWeekReportResult.WeeklyReportsBean reportsBean = weeklyReports.get(i);
                            if (reportsBean != null) {
                                switch (title) {
                                    case "用户及经纪人":
                                        total_count_1 += reportsBean.registeredUserCount;
                                        total_count_2 += reportsBean.agentVerifiedCount;
                                        break;
                                    case "订单数":
                                        total_count_2 += reportsBean.orderCount;
                                        break;
                                    case "付款订单数":
                                        total_count_2 += reportsBean.paidOrderCount;
                                        break;
                                    case "已支付金额":
                                        total_count_2f += reportsBean.paidAmount;
                                        break;
                                }
                            }
                        }

                        totalCountTv1.setText(total_count_1 + "");
                        if (total_count_2f != 0) {
                            totalCountTv2.setText(StringUtil.toTwoString(total_count_2f + ""));
                        } else {
                            totalCountTv2.setText(total_count_2 + "");
                        }

                        Collections.reverse(weeklyReports);
                        List<Column> columns = new ArrayList<>();
                        List<SubcolumnValue> values;
                        List<AxisValue> axisValues = new ArrayList<>();
                        for (int i = 0; i < weeklyReports.size(); i++) {
                            SomeWeekReportResult.WeeklyReportsBean reportsBean = weeklyReports.get(i);
                            if (reportsBean != null) {
                                values = new ArrayList<>();
                                SubcolumnValue subcolumnValue1 = null;
                                SubcolumnValue subcolumnValue2 = null;
                                switch (title) {
                                    case "用户及经纪人":
                                        subcolumnValue1 = new SubcolumnValue();
                                        subcolumnValue1.setValue(reportsBean.registeredUserCount);
                                        subcolumnValue2 = new SubcolumnValue(reportsBean.agentVerifiedCount);
                                        break;
                                    case "订单数":
                                        subcolumnValue1 = new SubcolumnValue();
                                        subcolumnValue1.setValue(reportsBean.orderCount);
                                        break;
                                    case "付款订单数":
                                        subcolumnValue1 = new SubcolumnValue();
                                        subcolumnValue1.setValue(reportsBean.paidOrderCount);
                                        break;
                                    case "已支付金额":
                                        subcolumnValue1 = new SubcolumnValue();
                                        subcolumnValue1.setValue((float) reportsBean.paidAmount);
                                        break;
                                }
                                values.add(subcolumnValue1);
                                if (subcolumnValue2 != null) {
                                    subcolumnValue2.setColor(getResources().getColor(R.color.column_orange));
                                    values.add(subcolumnValue2);
                                }
                                Column column = new Column(values);
                                column.setHasLabelsOnlyForSelected(true);
                                columns.add(column);
                                int weekNumOfYear = DataCenterUtils.getWeekNumOfYear
                                        (DataCenterUtils.stringtoDate(reportsBean.week, DataCenterUtils.UN_SEPARATOR_SHORT_DATE_FORMAT));
                                axisValues.add(new AxisValue(i).setLabel(weekNumOfYear + "周"));
                            }
                        }
                        ColumnChartData data = new ColumnChartData(columns);
                        Axis axisX = new Axis(axisValues);
                        Axis axisY = new Axis().setHasLines(true);
                        if (title.equals("已支付金额")) {
                            axisY.setMaxLabelChars(7);
                        } else {
                            axisY.setMaxLabelChars(3);
                        }
                        data.setAxisXBottom(axisX);
                        data.setAxisYLeft(axisY);
                        data.setValueLabelBackgroundEnabled(false);
                        data.setValueLabelsTextColor(getResources().getColor(R.color.deep_black));
                        chart.setColumnChartData(data);
                        //适配列表
                        ContentAdapter adapter = new ContentAdapter(instance);
                        unSwipeListView.setAdapter(adapter);
                        adapter.bindData(weeklyReports);
                    }
                }, throwable -> {
                    dismissProgress();
                });
    }


    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dateSelectRange(WeekRangeSelect range) {
        List<WeekBean> weekList = DataCenterUtils.getWeekList();
        startIndex = weekList.size() - 1 - range.getStartIndex();
        endIndex = weekList.size() - 1 - range.getEndIndex();
        WeekBean weekBeanStart = weekList.get(startIndex);
        WeekBean weekBeanEnd = weekList.get(endIndex);

        startStr = DataCenterUtils.dateToString(weekBeanStart.dateBegin, DataCenterUtils.SHORT_DATE_FORMAT);
        endDateStr = DataCenterUtils.dateToString(weekBeanEnd.dateEnd, DataCenterUtils.SHORT_DATE_FORMAT);

        dateTv.setText(startStr + "-" + endDateStr);
        //获取一周数据
        getData(weekBeanStart.dateBegin, weekBeanEnd.dateEnd);
    }


    @OnClick(R.id.date_tv_ll)
    void date_tv_ll() {
        Intent intent = WeekPickerActivity.startActivity(instance, true);
        intent.putExtra("startIndex", startIndex);
        intent.putExtra("endIndex", endIndex);
        startActivity(intent);
    }


    class ContentAdapter extends CommonAdapter<SomeWeekReportResult.WeeklyReportsBean> {

        public ContentAdapter(Context context) {
            super(R.layout.item_data_detail, context);
        }

        @Override
        public void convert(CommonViewHolder holder, SomeWeekReportResult.WeeklyReportsBean weeklyReportsBean) {
            if (weeklyReportsBean != null) {
                if (holder.getPosition() % 2 != 0) {
                    holder.getView(R.id.item_data_detail_ll).setBackgroundColor(getResources().getColor(R.color.order_title_bg));
                } else {
                    holder.getView(R.id.item_data_detail_ll).setBackgroundColor(getResources().getColor(R.color.white));
                }

                StringBuilder builder = new StringBuilder();
                Date dateBegin = DataCenterUtils.stringtoDate(weeklyReportsBean.week, DataCenterUtils.UN_SEPARATOR_SHORT_DATE_FORMAT);
                int weekNumOfYear = DataCenterUtils.getWeekNumOfYear(dateBegin);//第多少个周
                builder.append(weekNumOfYear).append("周");
                builder.append("(").append(DataCenterUtils.dateToString(dateBegin, DataCenterUtils.SHORT_DATE_FORMAT_DOT));//开始时间
                Date dateEnd = DataCenterUtils.dateAddOrDec(dateBegin, 6);
                if (dateEnd.getTime() > DataCenterUtils.getCurrDate().getTime()) {
                    builder.append("-").append(DataCenterUtils.dateToString(DataCenterUtils.getCurrDate(), DataCenterUtils.SHORT_DATE_FORMAT_DOT)).append(")");//结束时间
                } else {
                    builder.append("-").append(DataCenterUtils.dateToString(dateEnd, DataCenterUtils.SHORT_DATE_FORMAT_DOT)).append(")");//结束时间
                }

                TextView item_data_detail_content = holder.getView(R.id.item_data_detail_count);
                item_data_detail_content.setVisibility(View.GONE);

                holder.setText(R.id.item_data_detail_date, builder.toString());
                switch (title) {
                    case "用户及经纪人":
                        holder.setText(R.id.item_data_detail_content, weeklyReportsBean.agentVerifiedCount + "");
                        item_data_detail_content.setVisibility(View.VISIBLE);
                        item_data_detail_content.setText(weeklyReportsBean.registeredUserCount + "");
                        break;
                    case "订单数":
                        holder.setText(R.id.item_data_detail_content, weeklyReportsBean.orderCount + "");
                        break;
                    case "付款订单数":
                        holder.setText(R.id.item_data_detail_content, weeklyReportsBean.paidOrderCount + "");
                        break;
                    case "已支付金额":
                        holder.setText(R.id.item_data_detail_content, StringUtil.toTwoString(weeklyReportsBean.paidAmount + ""));
                        break;
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }


}

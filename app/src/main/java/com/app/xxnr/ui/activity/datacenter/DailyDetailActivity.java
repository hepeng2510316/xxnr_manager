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
import com.app.xxnr.bean.WeekReportResult;
import com.app.xxnr.event.DailyDateSelectRange;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.CommonAdapter;
import com.app.xxnr.ui.adapter.CommonViewHolder;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.xxnr.DataCenterUtils;
import com.app.xxnr.widget.UnSwipeListView;
import com.trello.rxlifecycle.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
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
public class DailyDetailActivity extends BaseSwipeBackActivity {
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


    public static void startActivity(Context mContext, String title, String dateStr) {
        Intent intent = new Intent(mContext, DailyDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("dateStr", dateStr);
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
    @Override
    public void initData() {
        title = getIntent().getStringExtra("title");
        endDateStr = getIntent().getStringExtra("dateStr");
        startStr = DataCenterUtils.dateAddOrDec(endDateStr, -6);
        if (StringUtil.checkStr(endDateStr) && StringUtil.checkStr(startStr)) {
            if (endDateStr.length() > 5 && startStr.length() > 5) {
                String subEndStr = endDateStr.substring(5);
                String subStartStr = startStr.substring(5);
                dateTv.setText(subStartStr + "-" + subEndStr);
            }
        }
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

        getData();

    }

    /**
     * 获取并处理数据
     */
    private void getData() {
        showProgress();
        api.queryDailyReport(DataCenterUtils.changeDateFormat(startStr), DataCenterUtils.changeDateFormat(endDateStr))
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(weekReportResult -> {
                    dismissProgress();
                    dataDetailScrollView.setVisibility(View.VISIBLE);
                    List<WeekReportResult.DailyReportsBean> dailyReports = weekReportResult.dailyReports;
                    if (dailyReports != null && !dailyReports.isEmpty()) {
                        //计算总和
                        int total_count_1 = 0;
                        int total_count_2 = 0;
                        double total_count_2f=0;

                        for (int i = 0; i < dailyReports.size(); i++) {
                            WeekReportResult.DailyReportsBean reportsBean = dailyReports.get(i);
                            if (reportsBean!=null){
                                switch (title) {
                                    case "用户及经纪人":
                                        total_count_1+=reportsBean.registeredUserCount;
                                        total_count_2+=reportsBean.agentVerifiedCount;
                                        break;
                                    case "订单数":
                                        total_count_2+=reportsBean.orderCount;
                                        break;
                                    case "付款订单数":
                                        total_count_2+=reportsBean.paidOrderCount;
                                        break;
                                    case "已支付金额":
                                        total_count_2f+=reportsBean.paidAmount;
                                        break;
                                }
                            }
                        }

                        totalCountTv1.setText(total_count_1+"");
                        if (total_count_2f!=0){
                            totalCountTv2.setText(StringUtil.toTwoString(total_count_2f+""));
                        }else {
                            totalCountTv2.setText(total_count_2+"");
                        }
                        Collections.reverse(dailyReports);
                        List<Column> columns = new ArrayList<>();
                        List<SubcolumnValue> values;
                        List<AxisValue> axisValues = new ArrayList<>();
                        for (int i = 0; i < dailyReports.size(); i++) {
                            WeekReportResult.DailyReportsBean reportsBean = dailyReports.get(i);
                            if (reportsBean != null) {
                                values = new ArrayList<>();
                                SubcolumnValue subcolumnValue1 = null;
                                SubcolumnValue subcolumnValue2 = null;

                                switch (title) {
                                    case "用户及经纪人":
                                        subcolumnValue1 = new SubcolumnValue();
                                        subcolumnValue1.setValue(reportsBean.registeredUserCount);
                                        subcolumnValue2 = new SubcolumnValue(reportsBean.agentVerifiedCount);
                                        subcolumnValue2.setColor(getResources().getColor(R.color.column_orange));
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

                                    values.add(subcolumnValue2);
                                }
                                Column column = new Column(values);
                                column.setHasLabelsOnlyForSelected(true);
                                columns.add(column);
                                if (reportsBean.day.length() > 4) {
                                    String substring = reportsBean.day.substring(4);//去年份
                                    StringBuilder builder = new StringBuilder(substring);
                                    builder.insert(2, "/");//月日之间插入斜杠
                                    String day;
                                    if (builder.charAt(0) == '0') {//去掉月前面的0
                                        day = builder.toString().substring(1);
                                    } else {
                                        day = builder.toString();
                                    }
                                    axisValues.add(new AxisValue(i).setLabel(day));
                                }
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
                        adapter.bindData(dailyReports);
                    }

                }, throwable -> {
                    dismissProgress();
                });
    }


    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dateSelectRange(DailyDateSelectRange range) {
        if (range.getDateList() != null && !range.getDateList().isEmpty()) {
            startStr = DataCenterUtils.dateToString(range.getDateList().get(0), DataCenterUtils.CHINESE_DATE_FORMAT);
            endDateStr = DataCenterUtils.dateToString(range.getDateList().get(range.getDateList().size() - 1), DataCenterUtils.CHINESE_DATE_FORMAT);

            String subStartStr = DataCenterUtils.dateToString(range.getDateList().get(0), DataCenterUtils.SHORT_DATE_FORMAT);
            String subEndStr = DataCenterUtils.dateToString(range.getDateList().get(range.getDateList().size() - 1), DataCenterUtils.SHORT_DATE_FORMAT);

            dateTv.setText(subStartStr + "-" + subEndStr);
            getData();
        }
    }


    @OnClick(R.id.date_tv_ll)
    void date_tv_ll() {
        Intent intent = DailyPickerActivity.startActivity(instance, true, true);
        intent.putExtra("starDateStr", startStr);
        intent.putExtra("endDateStr", endDateStr);
        startActivity(intent);
    }


    class ContentAdapter extends CommonAdapter<WeekReportResult.DailyReportsBean> {

        public ContentAdapter(Context context) {
            super(R.layout.item_data_detail, context);
        }

        @Override
        public void convert(CommonViewHolder holder, WeekReportResult.DailyReportsBean dailyReportsBean) {
            if (dailyReportsBean != null) {
                if (holder.getPosition() % 2 != 0) {
                    holder.getView(R.id.item_data_detail_ll).setBackgroundColor(getResources().getColor(R.color.order_title_bg));
                } else {
                    holder.getView(R.id.item_data_detail_ll).setBackgroundColor(getResources().getColor(R.color.white));
                }
                holder.setText(R.id.item_data_detail_date, DataCenterUtils.changeFormat(dailyReportsBean.day, "yyyyMMdd", "yyyy.MM.dd"));

                TextView item_data_detail_content = holder.getView(R.id.item_data_detail_count);
                item_data_detail_content.setVisibility(View.GONE);
                switch (title) {
                    case "用户及经纪人":
                        holder.setText(R.id.item_data_detail_content, dailyReportsBean.agentVerifiedCount + "");
                        item_data_detail_content.setVisibility(View.VISIBLE);
                        item_data_detail_content.setText(dailyReportsBean.registeredUserCount + "");
                        break;
                    case "订单数":
                        holder.setText(R.id.item_data_detail_content, dailyReportsBean.orderCount + "");
                        break;
                    case "付款订单数":
                        holder.setText(R.id.item_data_detail_content, dailyReportsBean.paidOrderCount + "");
                        break;
                    case "已支付金额":
                        holder.setText(R.id.item_data_detail_content, StringUtil.toTwoString(dailyReportsBean.paidAmount + ""));
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

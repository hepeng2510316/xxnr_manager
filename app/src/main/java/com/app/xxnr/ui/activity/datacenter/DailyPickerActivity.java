package com.app.xxnr.ui.activity.datacenter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.app.xxnr.R;
import com.app.xxnr.event.AgentDateSelectRange;
import com.app.xxnr.event.DailyDateSelectChange;
import com.app.xxnr.event.DailyDateSelectRange;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.utils.T;
import com.app.xxnr.utils.xxnr.DataCenterUtils;
import com.squareup.timessquare.CalendarPickerView;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 类描述：
 * 作者：何鹏 on 2016/5/18 17:47
 * 邮箱：hepeng@xinxinnongren.com
 */
public class DailyPickerActivity extends BaseSwipeBackActivity {

    @BindView(R.id.calendar_view)
    CalendarPickerView calendarView;


    private boolean isRange = false;
    private boolean isLimit = true;

    private int limitDay = 7;

    public static Intent startActivity(Context mContext, boolean isRange, boolean isLimit) {
        Intent intent = new Intent(mContext, DailyPickerActivity.class);
        intent.putExtra("isRange", isRange);
        intent.putExtra("isLimit", isLimit);
        return intent;
    }


    @Override
    public int initContentView() {
        return R.layout.activity_date_picker;
    }

    @Override
    public void initInjector() {
        setTitle("选择日期");
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        calendarView.setOnInvalidDateSelectedListener(null);
        calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if (isRange) {
                    List<Date> selectedDates = calendarView.getSelectedDates();
                    if (selectedDates != null && !selectedDates.isEmpty()) {
                        if (selectedDates.size() > limitDay) {
                            showToast("不能大于" + limitDay + "天");
                            Date dateStart = selectedDates.get(0);//保存第一个
                            calendarView.reset();
                            calendarView.selectDate(dateStart);
                        } else if (selectedDates.size() == 1) {
                            showToast("请再选择一个日期");
                            Date dateStart = selectedDates.get(0);//保存第一个
                            calendarView.reset();
                            calendarView.selectDate(dateStart);
                        } else {
                            if (isLimit){
                                EventBus.getDefault().post(new DailyDateSelectRange(selectedDates));
                            }else {
                                EventBus.getDefault().post(new AgentDateSelectRange(selectedDates));
                            }
                            finish();
                        }
                    }
                } else {
                    EventBus.getDefault().post(new DailyDateSelectChange(date));
                    finish();
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }

    @Override
    public void initData() {
        isRange = getIntent().getBooleanExtra("isRange", false);
        isLimit = getIntent().getBooleanExtra("isLimit", true);

        if (isLimit) {
            limitDay = 7;
        } else {
            limitDay = Integer.MAX_VALUE;
        }
        T.showShort("请选择日期");
        if (isRange) {
            String starDateStr = getIntent().getStringExtra("starDateStr");
            String endDateStr = getIntent().getStringExtra("endDateStr");
            initPicker(starDateStr, endDateStr, null, calendarView);
        } else {
            String selectDate = getIntent().getStringExtra("selectDate");
            initPicker(null, null, selectDate, calendarView);
        }
    }


    /**
     * 单选的情况
     */
    private void initPicker(String starDateStr, String endDateStr, String selectDate, CalendarPickerView pickerView) {

        Calendar calendar = Calendar.getInstance();//获得日历对象
        calendar.setTime(DataCenterUtils.getCurrDate());//当前时间
        calendar.add(Calendar.DATE, 1);//把日期往后增加一天.正数往后推,负数往前移动
        Date tomorrow = calendar.getTime(); //这个时间就是日期往后推一天的结果
        try {
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(DataCenterUtils.CHINESE_DATE_FORMAT);
            Date begin = df.parse(DataCenterUtils.startDateStr);
            if (isRange) {
                List<Date> listFormDateStr = DataCenterUtils.getListFormDateStr(starDateStr, endDateStr, DataCenterUtils.CHINESE_DATE_FORMAT);
                if (listFormDateStr != null && !listFormDateStr.isEmpty()) {
                    pickerView.init(begin, tomorrow)
                            .inMode(CalendarPickerView.SelectionMode.RANGE)
                            .withSelectedDates(listFormDateStr);//此处的init包含begin 不包含tomorrow;
                }
            } else {
                Date select = df.parse(selectDate);
                pickerView.init(begin, tomorrow)
                        .inMode(CalendarPickerView.SelectionMode.SINGLE)
                        .withSelectedDate(select);//此处的init包含begin 不包含tomorrow

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}

package com.app.xxnr.contract;

import com.app.xxnr.bean.DailyReportResult;
import com.app.xxnr.bean.StatisticReportResult;
import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/25 17:03
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface DailyReportContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void renderDailyReport(DailyReportResult result);

        void renderStatistic(StatisticReportResult result);

    }

    interface Presenter extends BasePresenter<View> {


        void getDailyReport(String date);

        void getStatistic();

    }
}

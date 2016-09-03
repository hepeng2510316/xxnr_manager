package com.app.xxnr.contract;

import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.bean.AgentReportTotalResult;
import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/26 11:19
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface AgentAchievementContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void renderList(List<AgentReportTotalResult.AgentReportsBean> agentReports);

        void onError();

        void onEmpty();

        void onRefreshCompleted();
    }

    interface Presenter extends BasePresenter<View> {

        void onLoad(String sort, int sortOrder, String search, int type, String dateStart, String dateEnd);

        void onLoadMore();

    }
}

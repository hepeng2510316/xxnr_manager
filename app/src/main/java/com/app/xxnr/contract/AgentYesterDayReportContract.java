package com.app.xxnr.contract;

import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.ui.BasePresenter;
import com.app.xxnr.ui.BaseView;

import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/26 11:19
 * 邮箱：hepeng@xinxinnongren.com
 */
public interface AgentYesterDayReportContract {

    interface View extends BaseView {
        void showLoading();

        void hideLoading();

        void renderResult(AgentReportResult result);

        void renderList(List<AgentReportResult.AgentReportYesterdayBean> BeanList);

        void onError();

        void onEmpty();

        void onRefreshCompleted();
    }

    interface Presenter extends BasePresenter<View> {

        void onLoad(String sort,int sortOrder);

        void onLoadMore();

    }
}

package com.app.xxnr.presenter;

import com.app.xxnr.api.ApiService;
import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.bean.AgentReportTotalResult;
import com.app.xxnr.contract.AgentAchievementContract;
import com.app.xxnr.contract.AgentYesterDayReportContract;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;
import com.app.xxnr.utils.xxnr.DataCenterUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/26 11:31
 * 邮箱：hepeng@xinxinnongren.com
 */
public class AgentAchievementPresenter extends BasicPresenter<AgentAchievementContract.View> implements AgentAchievementContract.Presenter {


    private List<AgentReportTotalResult.AgentReportsBean> lists = new ArrayList<>();

    private String search;
    private int type;
    private String dateStart;
    private String dateEnd;
    private int page = 1;
    private String sort;
    private int sortOrder;

    @Inject
    public AgentAchievementPresenter(ApiService api) {
        super(api);
    }


    @Override
    public void onLoad(String sort, int sortOrder, String search, int type, String dateStart, String dateEnd) {
        this.page = 1;
        this.sort = sort;
        this.sortOrder = sortOrder;
        this.type = type;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.search = search;
        if (sortOrder != 0) {
            mView.showLoading();
            getData();
        }
        if (StringUtil.checkStr(search)) {
            mView.showLoading();
            getData();
        }
    }


    private void getData() {

        api.queryAgentReports(
                sort,
                sortOrder,
                search,
                type,
                DataCenterUtils.changeDateFormat(dateStart),
                DataCenterUtils.changeDateFormat(dateEnd),
                page
        )
                .subscribe(totalResult -> {
                    mView.hideLoading();
                    mView.onRefreshCompleted();
                    page = totalResult.page;
                    handleLists(totalResult.agentReports);
                }, throwable -> {
                    handleError();
                });
    }


    @Override
    public void onLoadMore() {
        page++;
        getData();
    }


    private void handleLists(List<AgentReportTotalResult.AgentReportsBean> agentReports) {
        if (page == 1) {
            lists.clear();
        }
        if (agentReports != null && !agentReports.isEmpty()) {
            lists.addAll(agentReports);
            mView.renderList(lists);
        } else {
            if (lists.isEmpty()) {
                mView.onEmpty();
            } else {
                if (page != 1) {
                    page--;
                    T.showShort("没有更多了");
                }
            }
        }
    }

    private void handleError() {
        mView.hideLoading();
        mView.onRefreshCompleted();
        if (lists.isEmpty()) {
            mView.onError();
        }
        if (page != 1) {
            page--;
        }
    }
}

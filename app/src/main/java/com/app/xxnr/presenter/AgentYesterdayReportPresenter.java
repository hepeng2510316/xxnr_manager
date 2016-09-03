package com.app.xxnr.presenter;

import com.app.xxnr.api.ApiService;
import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.contract.AgentYesterDayReportContract;
import com.app.xxnr.presenter.base.BasicPresenter;
import com.app.xxnr.utils.T;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * 类描述：
 * 作者：何鹏 on 2016/8/26 11:31
 * 邮箱：hepeng@xinxinnongren.com
 */
public class AgentYesterdayReportPresenter extends BasicPresenter<AgentYesterDayReportContract.View> implements AgentYesterDayReportContract.Presenter {
    private int page = 1;
    private String sort;
    private int sortOrder;

    private List<AgentReportResult.AgentReportYesterdayBean> lists = new ArrayList<>();


    @Inject
    public AgentYesterdayReportPresenter(ApiService api) {
        super(api);
    }

    @Override
    public void onLoad(String sort, int sortOrder) {
        page = 1;
        this.sort = sort;
        this.sortOrder = sortOrder;
        mView.showLoading();
        getData();
    }

    private void getData() {
        api.queryAgentReportYesterday(sort, sortOrder, page)
                .subscribe(agentReportResult -> {
                    mView.hideLoading();
                    mView.onRefreshCompleted();
                    mView.renderResult(agentReportResult);
                    page = agentReportResult.page;
                    handleLists(agentReportResult.agentReportYesterday);
                }, throwable -> {
                    handleError();
                });

    }


    @Override
    public void onLoadMore() {
        page++;
        getData();
    }


    private void handleLists(List<AgentReportResult.AgentReportYesterdayBean> agentReportYesterday) {
        if (page == 1) {
            lists.clear();
        }
        if (agentReportYesterday != null && !agentReportYesterday.isEmpty()) {
            lists.addAll(agentReportYesterday);
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

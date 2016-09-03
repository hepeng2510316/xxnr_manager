package com.app.xxnr.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.contract.AgentYesterDayReportContract;
import com.app.xxnr.event.AgentPageSelect;
import com.app.xxnr.presenter.AgentYesterdayReportPresenter;
import com.app.xxnr.ui.BaseFragment;
import com.app.xxnr.ui.adapter.AgentYesterdayAdapter;
import com.app.xxnr.ui.adapter.AgentYesterdayNameAdapter;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.xxnr.DataCenterUtils;
import com.app.xxnr.utils.xxnr.DateFormatUtils;
import com.app.xxnr.widget.UnSwipeListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/26 10:26
 * 邮箱：hepeng@xinxinnongren.com
 */
public class AgentYesterdayReportFragment extends BaseFragment implements AgentYesterDayReportContract.View, PullToRefreshBase.OnRefreshListener {
    @BindView(R.id.updateTime)
    TextView updateTime;
    @BindView(R.id.select_agent_page_tv)
    TextView selectAgentPageTv;
    @BindView(R.id.unSwipeListView_name)
    UnSwipeListView unSwipeListViewName;
    @BindView(R.id.unSwipeListView)
    UnSwipeListView unSwipeListView;
    @BindView(R.id.yesterday_scrollView)
    PullToRefreshScrollView scrollView;

    @Inject
    AgentYesterdayReportPresenter mPresenter;
    @Inject
    AgentYesterdayAdapter agentAdapter;
    @Inject
    AgentYesterdayNameAdapter nameAgentAdapter;

    private TitleViewHolder titleViewHolder;

    private String SORT = "";

    private int SORTORDER;

    @Override
    public int initContentView() {
        return R.layout.fragment_agent_yesterday_report;
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
        mPresenter.attachView(this);

        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        scrollView.setOnRefreshListener(this);
        scrollView.getRefreshableView().scrollTo(10, 10);


        View header_unSwipeListView_name = inflater.inflate(R.layout.head_agent_report_name, null);
        View header_unSwipeListView = inflater.inflate(R.layout.head_agent_yesterday_report, null);
        unSwipeListViewName.addHeaderView(header_unSwipeListView_name);
        unSwipeListView.addHeaderView(header_unSwipeListView);
        unSwipeListViewName.setAdapter(nameAgentAdapter);
        unSwipeListView.setAdapter(agentAdapter);

        titleViewHolder = new TitleViewHolder(header_unSwipeListView);
        titleViewHolder.reset();

        titleViewHolder.title_new_customer_count_icon.setVisibility(View.VISIBLE);
        titleViewHolder.title_new_customer_count_icon.setBackgroundResource(R.mipmap.sort_down);
        titleViewHolder.title_new_customer_count_ll.setTag(false);

    }

    @Override
    public void initData() {
        SORT = "NEWINVITEE";
        SORTORDER = -1;
        getData();
    }

    private void getData() {
        mPresenter.onLoad(SORT, SORTORDER);
    }


    @OnClick(R.id.select_agent_page_tv)
    public void select_agent_page_tv() {
        EventBus.getDefault().post(new AgentPageSelect(1));
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showContent(true);
    }

    @Override
    public void renderResult(AgentReportResult result) {
        if (StringUtil.checkStr(result.lastUpdateTime)) {
            updateTime.setText(DataCenterUtils.changeFormat
                    (DateFormatUtils.convertTime(result.lastUpdateTime),
                            "yyyy-MM-dd HH:mm", DataCenterUtils.CHINESE_DATE_FORMAT));
        } else {
            updateTime.setText(DataCenterUtils.getCurrDateStr(DataCenterUtils.CHINESE_DATE_FORMAT));
        }
    }

    @Override
    public void renderList(List<AgentReportResult.AgentReportYesterdayBean> BeanList) {
        agentAdapter.bindData(BeanList);
        nameAgentAdapter.bindData(BeanList);
    }

    @Override
    public void onError() {
        showError(true);
    }

    @Override
    public void onEmpty() {
        showEmpty(true);
        setEmptyText("暂无排行");
    }


    @Override
    public void onRefreshCompleted() {
        if (scrollView.isRefreshing()){
            scrollView.onRefreshComplete();
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        mPresenter.onLoadMore();
    }

    @Override
    public void onReloadClicked() {
        super.onReloadClicked();
        mPresenter.onLoad(SORT,SORTORDER);
    }

    class TitleViewHolder {
        @BindView(R.id.title_new_customer_count)
        TextView title_new_customer_count;
        @BindView(R.id.title_new_customer_count_icon)
        ImageView title_new_customer_count_icon;
        @BindView(R.id.title_new_customer_count_ll)
        LinearLayout title_new_customer_count_ll;
        @BindView(R.id.title_total_customer_count)
        TextView title_total_customer_count;
        @BindView(R.id.title_total_customer_count_icon)
        ImageView title_total_customer_count_icon;
        @BindView(R.id.title_total_customer_count_ll)
        LinearLayout title_total_customer_count_ll;
        @BindView(R.id.title_total_agent_count)
        TextView title_total_agent_count;
        @BindView(R.id.title_total_agent_count_icon)
        ImageView title_total_agent_count_icon;
        @BindView(R.id.title_total_agent_count_ll)
        LinearLayout title_total_agent_count_ll;
        @BindView(R.id.title_reg_customer_count)
        TextView title_reg_customer_count;
        @BindView(R.id.title_reg_customer_count_icon)
        ImageView title_reg_customer_count_icon;
        @BindView(R.id.title_reg_customer_count_ll)
        LinearLayout title_reg_customer_count_ll;
        @BindView(R.id.title_total_reg_customer_count)
        TextView title_total_reg_customer_count;
        @BindView(R.id.title_total_reg_customer_count_icon)
        ImageView title_total_reg_customer_count_icon;
        @BindView(R.id.title_total_reg_customer_count_ll)
        LinearLayout title_total_reg_customer_count_ll;
        @BindView(R.id.title_order_count)
        TextView title_order_count;
        @BindView(R.id.title_order_count_icon)
        ImageView title_order_count_icon;
        @BindView(R.id.title_order_count_ll)
        LinearLayout title_order_count_ll;
        @BindView(R.id.title_price_amount)
        TextView title_price_amount;
        @BindView(R.id.title_price_amount_icon)
        ImageView title_price_amount_icon;
        @BindView(R.id.title_price_amount_ll)
        LinearLayout title_price_amount_ll;
        @BindView(R.id.item_agent_report_ll)
        LinearLayout item_agent_report_ll;

        TitleViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


        /**
         * OnClick
         */
        @OnClick({R.id.title_new_customer_count_ll,
                R.id.title_total_customer_count_ll,
                R.id.title_total_agent_count_ll,
                R.id.title_reg_customer_count_ll,
                R.id.title_total_reg_customer_count_ll,
                R.id.title_order_count_ll,
                R.id.title_price_amount_ll})
        void click(View v) {
            switch (v.getId()) {
                case R.id.title_new_customer_count_ll:
                    SORT = "NEWINVITEE";
                    sortOrder(v);
                    break;
                case R.id.title_total_customer_count_ll:
                    SORT = "TOTALINVITEE";
                    sortOrder(v);
                    break;
                case R.id.title_total_agent_count_ll:
                    SORT = "NEWAGENT";
                    sortOrder(v);
                    break;
                case R.id.title_reg_customer_count_ll:
                    SORT = "NEWPOTENTIALCUSTOMER";
                    sortOrder(v);
                    break;
                case R.id.title_total_reg_customer_count_ll:
                    SORT = "TOTALPOTENTIALCUSTOMER";
                    sortOrder(v);
                    break;
                case R.id.title_order_count_ll:
                    SORT = "TOTALCOMPLETEDORDER";
                    sortOrder(v);
                    break;
                case R.id.title_price_amount_ll:
                    SORT = "TOTALPAIDAMOUT";
                    sortOrder(v);
                    break;
            }
        }

        // reset
        public void reset() {
            this.title_new_customer_count_icon.setVisibility(View.GONE);
            this.title_new_customer_count_ll.setTag(true);

            this.title_total_customer_count_icon.setVisibility(View.GONE);
            this.title_total_customer_count_ll.setTag(true);

            this.title_total_agent_count_icon.setVisibility(View.GONE);
            this.title_total_agent_count_ll.setTag(true);

            this.title_reg_customer_count_icon.setVisibility(View.GONE);
            this.title_reg_customer_count_ll.setTag(true);

            this.title_total_reg_customer_count_icon.setVisibility(View.GONE);
            this.title_total_reg_customer_count_ll.setTag(true);

            this.title_order_count_icon.setVisibility(View.GONE);
            this.title_order_count_ll.setTag(true);

            this.title_price_amount_icon.setVisibility(View.GONE);
            this.title_price_amount_ll.setTag(true);
        }


        //sort
        private void sortOrder(View v) {
            if (titleViewHolder != null) {
                if ((v.getTag()) != null && (Boolean) v.getTag()) {
                    reset();
                    SORTORDER = -1;
                    getData();
                    v.setTag(false);
                    try {
                        ((LinearLayout) v).getChildAt(1).setBackgroundResource(R.mipmap.sort_down);
                        ((LinearLayout) v).getChildAt(1).setVisibility(View.VISIBLE);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                } else {
                    reset();
                    SORTORDER = 1;
                    getData();
                    v.setTag(true);
                    try {
                        ((LinearLayout) v).getChildAt(1).setBackgroundResource(R.mipmap.sort_up);
                        ((LinearLayout) v).getChildAt(1).setVisibility(View.VISIBLE);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        unSwipeListView.setFocusable(false);
        unSwipeListViewName.setFocusable(false);
        scrollView.requestFocus();
        scrollView.getRefreshableView().smoothScrollTo(0, 10);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}

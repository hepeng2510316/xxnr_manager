package com.app.xxnr.ui.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.AgentReportTotalResult;
import com.app.xxnr.contract.AgentAchievementContract;
import com.app.xxnr.event.AgentGroupDateEvent;
import com.app.xxnr.event.AgentGroupRefresh;
import com.app.xxnr.event.AgentGroupRefreshComplete;
import com.app.xxnr.event.AgentGroupSearch;
import com.app.xxnr.event.AgentGroupUnSearch;
import com.app.xxnr.presenter.AgentAchievementPresenter;
import com.app.xxnr.ui.BaseFragment;
import com.app.xxnr.ui.adapter.AgentAchievementAdapter;
import com.app.xxnr.ui.adapter.AgentGroupNameAgentAdapter;
import com.app.xxnr.widget.UnSwipeListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：
 * 作者：何鹏 on 2016/5/24 15:14
 * 邮箱：hepeng@xinxinnongren.com
 */
public class AgentGroupReportAchievementFragment extends BaseFragment implements AgentAchievementContract.View {

    @BindView(R.id.unSwipeListView_name)
    UnSwipeListView unSwipeListViewName;
    @BindView(R.id.unSwipeListView)
    UnSwipeListView unSwipeListView;

    @Inject
    AgentAchievementPresenter mPresenter;
    @Inject
    AgentAchievementAdapter agentAdapter;
    @Inject
    AgentGroupNameAgentAdapter nameAgentAdapter;
    @Inject
    EventBus mBus;

    private ViewHolder headViewHolder;
    private View cacheView;

    private String startStr;
    private String endDateStr;
    private String SORT;
    private int SORTORDER;
    private String SEARCH;


    public static AgentGroupReportAchievementFragment newInstance(String startStr, String endDateStr) {
        AgentGroupReportAchievementFragment mFragment = new AgentGroupReportAchievementFragment();
        Bundle bundle = new Bundle();
        bundle.putString("startStr", startStr);
        bundle.putString("endDateStr", endDateStr);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_agent_group_report_achievement;
    }

    @Override
    public void initInjector() {
        mFragmentComponet.inject(this);
    }

    @Override
    public void getBundle(Bundle bundle) {
        startStr = bundle.getString("startStr");
        endDateStr = bundle.getString("endDateStr");

    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        mBus.register(this);
        mPresenter.attachView(this);

        View header_unSwipeListView_name = inflater.inflate(R.layout.head_agent_report_name, null);
        View header_unSwipeListView = inflater.inflate(R.layout.head_agent_group_report_achievement, null);
        unSwipeListViewName.addHeaderView(header_unSwipeListView_name);
        unSwipeListView.addHeaderView(header_unSwipeListView);
        unSwipeListViewName.setAdapter(nameAgentAdapter);
        unSwipeListView.setAdapter(agentAdapter);

        headViewHolder = new ViewHolder(header_unSwipeListView);
        headViewHolder.reset();

        headViewHolder.title_new_customer_count_icon.setVisibility(View.VISIBLE);
        headViewHolder.title_new_customer_count_icon.setBackgroundResource(R.mipmap.sort_down);
        headViewHolder.title_new_customer_count_ll.setTag(false);
    }


    @Override
    public void initData() {
        SORT = "NEWINVITEE";
        SORTORDER = -1;
        mPresenter.onLoad(SORT, SORTORDER, SEARCH, 1, startStr, endDateStr);
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
    public void renderList(List<AgentReportTotalResult.AgentReportsBean> agentReports) {
        agentAdapter.bindData(agentReports);
        nameAgentAdapter.bindData(agentReports);
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
    public void onReloadClicked() {
        super.onReloadClicked();
        mPresenter.onLoad(SORT, SORTORDER, SEARCH, 1, startStr, endDateStr);
    }

    @Override
    public void onRefreshCompleted() {
        EventBus.getDefault().post(new AgentGroupRefreshComplete());
    }


    //上拉加载更多
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(AgentGroupRefresh event) {
        if (!event.isCurrent_page_isOrder()) {
            mPresenter.onLoadMore();
        }
    }


    //接收选中时间
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveDate(AgentGroupDateEvent event) {
        startStr = event.getDateStart();
        endDateStr = event.getDateEnd();
        mPresenter.onLoad(SORT, SORTORDER, SEARCH, 1, startStr, endDateStr);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearch(AgentGroupSearch event) {
        if (SORTORDER == 0) {
            agentAdapter.clear();
            nameAgentAdapter.clear();

            SEARCH = event.getSearch();
            mPresenter.onLoad(null, SORTORDER, SEARCH, 1, startStr, endDateStr);
        } else {
            headViewHolder.resetOnSeach();
            SORTORDER = 0;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnSearch(AgentGroupUnSearch event) {
        if (cacheView != null) {
            Boolean tag = (Boolean) cacheView.getTag();
            if (tag != null && tag) {
                cacheView.setTag(false);
            } else {
                cacheView.setTag(true);
            }
            sortOrder(cacheView);
        } else {
            headViewHolder.reset();
            headViewHolder.title_new_customer_count_icon.setVisibility(View.VISIBLE);
            headViewHolder.title_new_customer_count_icon.setBackgroundResource(R.mipmap.sort_down);
            headViewHolder.title_new_customer_count_ll.setTag(false);
            SEARCH = null;
            SORT = "NEWINVITEE";
            SORTORDER = -1;
            mPresenter.onLoad(SORT, SORTORDER, null, 1, startStr, endDateStr);
        }
    }


    //sort
    public void sortOrder(View v) {
        cacheView = v;
        if (headViewHolder != null) {
            if ((v.getTag()) != null && (Boolean) v.getTag()) {
                headViewHolder.reset();
                SORTORDER = -1;
                mPresenter.onLoad(SORT, SORTORDER, SEARCH, 1, startStr, endDateStr);
                v.setTag(false);
                try {
                    ((LinearLayout) v).getChildAt(1).setBackgroundResource(R.mipmap.sort_down);
                    ((LinearLayout) v).getChildAt(1).setVisibility(View.VISIBLE);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            } else {
                headViewHolder.reset();
                SORTORDER = 1;
                mPresenter.onLoad(SORT, SORTORDER, SEARCH, 1, startStr, endDateStr);
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


    class ViewHolder {
        @BindView(R.id.title_new_customer_count)
        TextView title_new_customer_count;
        @BindView(R.id.title_new_customer_count_icon)
        ImageView title_new_customer_count_icon;
        @BindView(R.id.title_new_customer_count_ll)
        LinearLayout title_new_customer_count_ll;
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
        @BindView(R.id.title_new_order_count)
        TextView title_new_order_count;
        @BindView(R.id.title_new_order_count_icon)
        ImageView title_new_order_count_icon;
        @BindView(R.id.title_new_order_count_ll)
        LinearLayout title_new_order_count_ll;
        @BindView(R.id.title_order_count)
        TextView title_order_count;
        @BindView(R.id.title_order_count_icon)
        ImageView title_order_count_icon;
        @BindView(R.id.title_order_count_ll)
        LinearLayout title_order_count_ll;
        @BindView(R.id.item_agent_report_ll)
        LinearLayout item_agent_report_ll;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.title_new_customer_count_ll, R.id.title_total_agent_count_ll, R.id.title_reg_customer_count_ll, R.id.title_new_order_count_ll, R.id.title_order_count_ll})
        void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_new_customer_count_ll:
                    SORT = "NEWINVITEE";
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
                case R.id.title_new_order_count_ll:
                    SORT = "NEWORDER";
                    sortOrder(v);
                    break;
                case R.id.title_order_count_ll:
                    SORT = "NEWORDERCOMPLETED";
                    sortOrder(v);
                    break;
            }
        }


        public void reset() {
            title_new_customer_count_ll.setEnabled(true);
            title_total_agent_count_ll.setEnabled(true);
            title_reg_customer_count_ll.setEnabled(true);
            title_new_order_count_ll.setEnabled(true);
            title_order_count_ll.setEnabled(true);


            this.title_new_customer_count_icon.setVisibility(View.GONE);
            this.title_new_customer_count_ll.setTag(true);

            this.title_total_agent_count_icon.setVisibility(View.GONE);
            this.title_total_agent_count_ll.setTag(true);

            this.title_reg_customer_count_icon.setVisibility(View.GONE);
            this.title_reg_customer_count_ll.setTag(true);


            this.title_new_order_count_icon.setVisibility(View.GONE);
            this.title_new_order_count_ll.setTag(true);

            this.title_order_count_icon.setVisibility(View.GONE);
            this.title_order_count_ll.setTag(true);
        }


        public void resetOnSeach() {
            title_new_customer_count_ll.setEnabled(false);
            title_total_agent_count_ll.setEnabled(false);
            title_reg_customer_count_ll.setEnabled(false);
            title_new_order_count_ll.setEnabled(false);
            title_order_count_ll.setEnabled(false);

            this.title_new_customer_count_icon.setVisibility(View.GONE);
            this.title_total_agent_count_icon.setVisibility(View.GONE);
            this.title_reg_customer_count_icon.setVisibility(View.GONE);
            this.title_new_order_count_icon.setVisibility(View.GONE);
            this.title_order_count_icon.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        unSwipeListView.setFocusable(false);
        unSwipeListViewName.setFocusable(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
        mPresenter.detachView();
    }
}

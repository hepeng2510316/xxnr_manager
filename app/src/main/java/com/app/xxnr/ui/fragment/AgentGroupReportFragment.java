package com.app.xxnr.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.event.AgentGroupDateEvent;
import com.app.xxnr.event.AgentGroupRefresh;
import com.app.xxnr.event.AgentGroupRefreshComplete;
import com.app.xxnr.event.AgentGroupSearch;
import com.app.xxnr.event.AgentGroupUnSearch;
import com.app.xxnr.event.AgentPageSelect;
import com.app.xxnr.event.AgentDateSelectRange;
import com.app.xxnr.ui.BaseFragment;
import com.app.xxnr.ui.activity.datacenter.DailyPickerActivity;
import com.app.xxnr.ui.activity.datacenter.DataCenterActivity;
import com.app.xxnr.utils.KeyBoardUtils;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.xxnr.DataCenterUtils;
import com.app.xxnr.widget.ClearEditText;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/26 10:26
 * 邮箱：hepeng@xinxinnongren.com
 */
public class AgentGroupReportFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener, DataCenterActivity.OutTouchListener {

    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.select_agent_group_tv)
    TextView selectAgentGroupTv;
    @BindView(R.id.select_agent_group_icon)
    ImageView select_agent_group_icon;
    @BindView(R.id.clear_edit_text)
    ClearEditText clearEditText;
    @BindView(R.id.search_content_ll)
    LinearLayout searchContentLl;
    @BindView(R.id.search_icon_iv)
    ImageView searchIconIv;
    @BindView(R.id.pullToRefreshScrollView)
    PullToRefreshScrollView pullToRefreshScrollView;


    private String endDateStr;
    private String startStr;

    private AgentGroupReportAchievementFragment achievementFragment;
    private AgentGroupReportOrderFragment orderFragment;
    private boolean current_page_isOrder;
    private PopupWindow popupWindow;
    private PopViewHolder popViewHolder;

    @Override
    public int initContentView() {
        return R.layout.fragment_agent_group_report;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ((DataCenterActivity) getActivity()).registerOutTouchListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initUI(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        showContent(true);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullToRefreshScrollView.setOnRefreshListener(this);
        pullToRefreshScrollView.getRefreshableView().scrollTo(10, 10);
        searchIconIv.setVisibility(View.VISIBLE);
        searchContentLl.setVisibility(View.GONE);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        String day = DataCenterUtils.getCurrDateStr(DataCenterUtils.CHINESE_DATE_FORMAT);
        endDateStr = DataCenterUtils.dateAddOrDec(day, -1);
        startStr = DataCenterUtils.dateAddOrDec(endDateStr, -6);

        if (StringUtil.checkStr(endDateStr) && StringUtil.checkStr(startStr)) {
            if (endDateStr.length() > 5 && startStr.length() > 5) {
                String subEndStr = endDateStr.substring(5);
                String subStartStr = startStr.substring(5);
                dateTv.setText(subStartStr + "-" + subEndStr);
            }
        }

        achievementFragment = AgentGroupReportAchievementFragment.newInstance(startStr, endDateStr);
        orderFragment = AgentGroupReportOrderFragment.newInstance(startStr, endDateStr);

        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.agent_group_frameLayout, orderFragment)
                .add(R.id.agent_group_frameLayout, achievementFragment)
                .commitAllowingStateLoss();
        current_page_isOrder = false;
        changeFragment();
    }

    //上拉加载更多
    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        EventBus.getDefault().post(new AgentGroupRefresh(current_page_isOrder));
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if (searchContentLl != null && clearEditText != null) {
            if (!StringUtil.checkStr(clearEditText.getText().toString())
                    && searchContentLl.getVisibility() == View.VISIBLE) {
                searchIconIv.setVisibility(View.VISIBLE);
                searchContentLl.setVisibility(View.GONE);
                EventBus.getDefault().post(new AgentGroupUnSearch());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshComplete(AgentGroupRefreshComplete complete) {
        if (pullToRefreshScrollView != null) {
            pullToRefreshScrollView.onRefreshComplete();
        }
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dateSelectRange(AgentDateSelectRange range) {
        if (!range.getDateList().isEmpty()) {
            startStr = DataCenterUtils.dateToString(range.getDateList().get(0), DataCenterUtils.CHINESE_DATE_FORMAT);
            endDateStr = DataCenterUtils.dateToString(range.getDateList().get(range.getDateList().size() - 1), DataCenterUtils.CHINESE_DATE_FORMAT);
            String subStartStr = DataCenterUtils.dateToString(range.getDateList().get(0), DataCenterUtils.SHORT_DATE_FORMAT);
            String subEndStr = DataCenterUtils.dateToString(range.getDateList().get(range.getDateList().size() - 1), DataCenterUtils.SHORT_DATE_FORMAT);
            dateTv.setText(subStartStr + "-" + subEndStr);
            EventBus.getDefault().post(new AgentGroupDateEvent(startStr, endDateStr));
        }
    }


    @OnEditorAction(R.id.clear_edit_text)
    boolean rsc_search_editEditor(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//判断是否是“搜索”键
            KeyBoardUtils.closeKeyboard(clearEditText, activity); //隐藏软键盘
            String searchText = clearEditText.getText().toString().trim();
            EventBus.getDefault().post(new AgentGroupSearch(searchText));
            return true;
        }
        return false;
    }

    @OnClick(R.id.date_tv_ll)
    void date_tv_ll() {
        Intent intent = DailyPickerActivity.startActivity(activity, true, false);
        intent.putExtra("starDateStr", startStr);
        intent.putExtra("endDateStr", endDateStr);
        startActivity(intent);
    }

    //切换昨日订单
    @OnClick(R.id.select_agent_page_tv)
    void select_agent_page_tv() {
        EventBus.getDefault().post(new AgentPageSelect(0));
    }

    //选择汇总排行
    @OnClick(R.id.select_agent_group_ll)
    void select_agent_group_ll(View v) {
        showPopupWindow(v);
    }

    @OnClick(R.id.search_icon_iv)
    void search_icon_iv() {
        searchIconIv.setVisibility(View.GONE);
        searchContentLl.setVisibility(View.VISIBLE);
        startAnimation();
        EventBus.getDefault().post(new AgentGroupSearch(null));
    }

    private void startAnimation() {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(searchContentLl, "scaleY", 0.5f, 1.0f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(searchContentLl, "translationX", 200, 0);
        AnimatorSet animatorSetOpen = new AnimatorSet();
        animatorSetOpen.playTogether(animator1, animator2);
        animatorSetOpen.setDuration(500);
        animatorSetOpen.start();
        searchContentLl.requestFocus();
    }


    private void changeFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (current_page_isOrder) {
            selectAgentGroupTv.setText("完成订单汇总");
            transaction.hide(achievementFragment).show(orderFragment);
        } else {
            selectAgentGroupTv.setText("业绩进度汇总");
            transaction.hide(orderFragment).show(achievementFragment);
        }
        transaction.commitAllowingStateLoss();
    }


    /***
     * 获取PopupWindow实例
     */
    private void showPopupWindow(View v) {
        if (null == popupWindow) {
            initPopWindow();
        }
        popupWindow.dismiss();
        select_agent_group_icon.setBackgroundResource(R.mipmap.arrow_up);
        //当前展示哪个item
        if (current_page_isOrder) {
            popViewHolder.itemGroupOrderIv.setVisibility(View.VISIBLE);
            popViewHolder.itemGroupOrderTv.setTextColor(getResources().getColor(R.color.green));
            popViewHolder.itemGroupAchievementIv.setVisibility(View.INVISIBLE);
            popViewHolder.itemGroupAchievementTv.setTextColor(getResources().getColor(R.color.default_black));
        } else {
            popViewHolder.itemGroupOrderIv.setVisibility(View.INVISIBLE);
            popViewHolder.itemGroupOrderTv.setTextColor(getResources().getColor(R.color.default_black));
            popViewHolder.itemGroupAchievementIv.setVisibility(View.VISIBLE);
            popViewHolder.itemGroupAchievementTv.setTextColor(getResources().getColor(R.color.green));
        }
        popupWindow.showAsDropDown(v);
    }


    /**
     * 创建PopupWindow
     */
    private void initPopWindow() {
        View popupWindow_view = LayoutInflater.from(activity).inflate(R.layout.pop_select_agent_group_layout, null);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popViewHolder = new PopViewHolder(popupWindow_view);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimTop2);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(() -> select_agent_group_icon.setBackgroundResource(R.mipmap.arrow_down));
    }


    class PopViewHolder {
        @BindView(R.id.item_group_achievement_tv)
        TextView itemGroupAchievementTv;
        @BindView(R.id.item_group_achievement_iv)
        ImageView itemGroupAchievementIv;
        @BindView(R.id.item_group_order_tv)
        TextView itemGroupOrderTv;
        @BindView(R.id.item_group_order_iv)
        ImageView itemGroupOrderIv;


        PopViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_group_achievement_ll)
        void item_group_achievement_ll() {
            if (current_page_isOrder) {
                current_page_isOrder = false;
                changeFragment();
            }
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        }

        @OnClick(R.id.item_group_order_ll)
        void item_group_order_ll() {
            if (!current_page_isOrder) {
                current_page_isOrder = true;
                changeFragment();
            }
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pullToRefreshScrollView.requestFocus();
        pullToRefreshScrollView.getRefreshableView().smoothScrollTo(10, 10);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

package com.app.xxnr.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.OrderListResult;
import com.app.xxnr.contract.OrderListContract;
import com.app.xxnr.contract.OrderOperationContract;
import com.app.xxnr.event.OrderOperateEvent;
import com.app.xxnr.presenter.OrderListPresenter;
import com.app.xxnr.presenter.OrderOperationPresenter;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.OrderListAdapter;
import com.app.xxnr.utils.KeyBoardUtils;
import com.app.xxnr.utils.xxnr.PopWindowUtils;
import com.app.xxnr.widget.ClearEditText;
import com.app.xxnr.widget.LoadMoreScrollListener;
import com.app.xxnr.widget.LoadingFooter;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 类描述：
 * 作者：何鹏 on 2016/8/24 14:13
 * 邮箱：hepeng@xinxinnongren.com
 */
public class OrderSearchActivity extends BaseSwipeBackActivity implements
        OrderListContract.View,
        OrderOperationContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        OrderListAdapter.OnItemButtonListener,
        OrderListAdapter.OnItemClickListener {


    @BindView(R.id.rsc_search_edit)
    ClearEditText rscSearchEdit;
    @BindView(R.id.rsc_search_text)
    TextView rscSearchText;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pop_bg)
    RelativeLayout popBg;
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.empty_View)
    RelativeLayout emptyView;


    @Inject
    OrderListPresenter mPresenter;
    @Inject
    OrderOperationPresenter mOperationPresenter;
    @Inject
    OrderListAdapter adapter;
    @Inject
    LoadingFooter mLoadingFooter;


    @Override
    public int initContentView() {
        return R.layout.activity_search;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mOperationPresenter.attachView(this);

        rscSearchEdit.setHint("手机号/姓名/订单号");
        emptyView.setVisibility(View.GONE);
        emptyImage.setImageResource(R.mipmap.none_order_icon);
        emptyText.setText("未查找到符合条件的订单");

        RxTextView.textChangeEvents(rscSearchEdit)
                .compose(this.bindToLifecycle())
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewTextChangeEvent -> {
                    String s = textViewTextChangeEvent.text().toString().trim();
                    mPresenter.onSearch(s, false);
                });

        //软件盘自动弹出
        rscSearchEdit.requestFocus();
        Observable.timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    KeyBoardUtils.openKeyboard(rscSearchEdit, instance);
                });


        swipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnItemButtonListener(this);
        adapter.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        mLoadingFooter.bindView(listView);
        listView.setOnScrollListener(new LoadMoreScrollListener(mLoadingFooter) {
            @Override
            public void loadMore() {
                mPresenter.onLoadMore();
            }
        });

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.rsc_search_text)
    void rsc_search_text() {
        KeyBoardUtils.closeKeyboard(rscSearchEdit, instance);
        finish();
    }

    @OnEditorAction(R.id.rsc_search_edit)
    boolean rsc_search_editEditor(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//判断是否是“搜索”键
            KeyBoardUtils.closeKeyboard(rscSearchEdit, instance); //隐藏软键盘
            String searchText = rscSearchEdit.getText().toString().trim();
            mPresenter.onSearch(searchText, true);
            return true;
        }
        return false;
    }

    @Override
    public void onItemButtonClick(View view, boolean isComment, OrderListResult.DatasBean.ItemsBean itemsBean) {
        if (isComment) {
            mOperationPresenter.checkOffline(view, itemsBean);
        } else {
            mOperationPresenter.deliveryState(view, itemsBean);
        }
    }

    @Override
    public void OnItemClick(OrderListResult.DatasBean.ItemsBean itemsBean) {
        OrderDetailActivity.startActivity(this,itemsBean);
        finish();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        dismissProgress();
    }

    @Override
    public void renderList(List<OrderListResult.DatasBean.ItemsBean> itemsBean) {
        emptyView.setVisibility(View.GONE);
        mLoadingFooter.setState(LoadingFooter.State.Idle);
        adapter.bindData(itemsBean);
    }

    @Override
    public void onRefreshCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void allLoaded() {
        if (adapter.getCount() > 10) {
            mLoadingFooter.setState(LoadingFooter.State.TheEnd);
        } else {
            mLoadingFooter.setState(LoadingFooter.State.Deal);
        }
    }

    @Override
    public void onError() {
        mLoadingFooter.setState(LoadingFooter.State.Deal);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmpty() {
        adapter.clear();
        mLoadingFooter.setState(LoadingFooter.State.Deal);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDialogBg(boolean show) {
        PopWindowUtils.setBackgroundBlack(popBg, show);
    }

    @Override
    public void operateSuccess() {
        mPresenter.onReload();
        EventBus.getDefault().post(new OrderOperateEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyBoardUtils.closeKeyboard(rscSearchEdit, instance); //隐藏软键盘
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mOperationPresenter.detachView();
    }
}

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
import com.app.xxnr.bean.PotentialListResult;
import com.app.xxnr.contract.PotentialListContract;
import com.app.xxnr.presenter.PotentialListPresenter;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.ui.adapter.PotentialListAdapter;
import com.app.xxnr.utils.KeyBoardUtils;
import com.app.xxnr.widget.ClearEditText;
import com.app.xxnr.widget.LoadMoreScrollListener;
import com.app.xxnr.widget.LoadingFooter;
import com.jakewharton.rxbinding.widget.RxTextView;

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
 * 类描述：客户管理搜索
 * 作者：何鹏 on 2016/8/10 17:49
 * 邮箱：hepeng@xinxinnongren.com
 */
public class PotentialSearchActivity extends BaseSwipeBackActivity implements
        PotentialListContract.View,
        PotentialListAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rsc_search_edit)
    ClearEditText rscSearchEdit;
    @BindView(R.id.rsc_search_text)
    TextView rscSearchText;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.empty_View)
    RelativeLayout emptyView;


    @Inject
    PotentialListPresenter mPresenter;
    @Inject
    PotentialListAdapter adapter;
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

        emptyView.setVisibility(View.GONE);
        emptyImage.setImageResource(R.mipmap.none_customer_icon);
        emptyText.setText("未查找到相关用户");
        rscSearchText.setText("取消");
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
        adapter.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
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
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        dismissProgress();
    }

    @Override
    public void renderList(List<PotentialListResult.PotentialCustomersBean> itemsBean) {
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
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyBoardUtils.closeKeyboard(rscSearchEdit, instance); //隐藏软键盘
    }

    @Override
    public void onListClick(PotentialListResult.PotentialCustomersBean itemsBean) {
        PotentialDetailActivity.startActivity(this,itemsBean);
        finish();
    }


}

package com.app.xxnr.widget;

import android.widget.AbsListView;

/**
 * Created by CAI on 2016/6/23. 加载更多OnsrcollListener
 */
public abstract class LoadMoreScrollListener implements AbsListView.OnScrollListener {

    private LoadingFooter mLoadingFooter;

    public LoadMoreScrollListener(LoadingFooter mLoadingFooter) {
        this.mLoadingFooter = mLoadingFooter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            // 当不滚动时
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                // 判断滚动到底部
                if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    //加载更多
                    if (mLoadingFooter.getState() == LoadingFooter.State.Idle) {
                        mLoadingFooter.setState(LoadingFooter.State.Loading);
                        loadMore();
                    }
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public abstract void loadMore();
}

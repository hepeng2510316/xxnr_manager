package com.app.xxnr.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.xxnr.R;

import javax.inject.Inject;


/**
 * 底部加载更多
 */
public class LoadingFooter {

    protected View mLoadingFooter;

    protected TextView mLoadingText;
    protected TextView line_lift;
    protected TextView line_right;
    protected State mState = State.Deal;

    private ProgressBar mProgress;
    private ListView listView;


    public enum State {
        Idle, TheEnd, Loading, Deal
    }

    @Inject
    public LoadingFooter(Context context) {
        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.foot_load_more, null);
        mLoadingFooter.setOnClickListener(null);
        mProgress = (ProgressBar) mLoadingFooter.findViewById(R.id.progressBar);
        mLoadingText = (TextView) mLoadingFooter.findViewById(R.id.foot_load_text);
        line_right = (TextView) mLoadingFooter.findViewById(R.id.line_right);
        line_lift = (TextView) mLoadingFooter.findViewById(R.id.line_lift);
        mState = State.Deal;
    }

    public void bindView(ListView listView){
        this.listView = listView;
    }


    private View getView() {
        return mLoadingFooter;
    }

    public State getState() {
        return mState;
    }


    private void showEnd() {
        addFooter();
        mLoadingText.setText("已到最后");
        mProgress.setVisibility(View.GONE);
        line_right.setVisibility(View.VISIBLE);
        line_lift.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        addFooter();
        mLoadingText.setText("正在载入");
        line_right.setVisibility(View.INVISIBLE);
        line_lift.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);
        listView.postDelayed(() -> {
            if (getState() == State.Loading) {
                setState(State.Idle);
            }
        }, 300);
    }


    public void setText(String msg){
        mLoadingText.setText(msg);
        mProgress.setVisibility(View.GONE);
        line_right.setVisibility(View.GONE);
        line_lift.setVisibility(View.GONE);
    }


    private void showIdle() {
        addFooter();
        mLoadingText.setText("正在载入");
        line_right.setVisibility(View.INVISIBLE);
        line_lift.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);
    }

    private void removeFooter() {
        try {
            if (listView != null) {
                listView.removeFooterView(mLoadingFooter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFooter() {
        try {
            if (listView != null && listView.getFooterViewsCount() == 0) {
                listView.addFooterView(mLoadingFooter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setState(State status) {
        mState = status;
        switch (status) {
            case Loading:
                showLoading();
                break;
            case TheEnd:
                showEnd();
                break;
            case Idle:
                showIdle();
                break;
            case Deal:
                removeFooter();
                break;
            default:
                mLoadingFooter.setVisibility(View.GONE);
                break;
        }
    }
}

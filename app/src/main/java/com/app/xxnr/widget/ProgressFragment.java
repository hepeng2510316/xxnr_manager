package com.app.xxnr.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.app.xxnr.R;
import com.app.xxnr.ui.BaseActivity;
import com.app.xxnr.widget.state.ContentState;
import com.app.xxnr.widget.state.EmptyState;
import com.app.xxnr.widget.state.ErrorState;
import com.app.xxnr.widget.state.NonState;
import com.app.xxnr.widget.state.ProgressState;
import com.app.xxnr.widget.state.ShowState;
import com.trello.rxlifecycle.components.support.RxFragment;


/**
 * Created by sll on 2015/3/13.
 * error empty progress content的fragment
 */
public class ProgressFragment extends RxFragment {

    public boolean isPrepare = false;

    protected BaseActivity activity;
    protected LayoutInflater inflater;

    //Override this method to change content view
    public View onCreateContentView(LayoutInflater inflater) {
        return null;
    }

    //Override this method to change error view
    public View onCreateContentErrorView(LayoutInflater inflater) {
        return null;
    }

    //Override this method to Empty error view
    public View onCreateContentEmptyView(LayoutInflater inflater) {
        return null;
    }

    //Override this method to Empty Progress view
    public View onCreateProgressView(LayoutInflater inflater) {
        return null;
    }

    private View mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (BaseActivity) getActivity();
        this.inflater = inflater;
        ViewGroup main = (ViewGroup) inflater.inflate(R.layout.fragment_progress_layout, container, false);

        View content = onCreateContentView(inflater);
        View error = onCreateContentErrorView(inflater);
        View empty = onCreateContentEmptyView(inflater);
        View progress = onCreateProgressView(inflater);

        replaceViewById(main, R.id.epf_content, content);
        replaceViewById(main, R.id.epf_error, error);
        replaceViewById(main, R.id.epf_empty, empty);
        replaceViewById(main, R.id.epf_progress, progress);

        mContentView = main;

        mAnimIn = onCreateAnimationIn();
        mAnimOut = onCreateAnimationOut();

        initStates();
        isPrepare = true;
        return main;
    }

    private Animation onCreateAnimationIn() {
        return AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
    }

    private Animation onCreateAnimationOut() {
        return AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
    }

    private void replaceViewById(ViewGroup container, int viewId, View newView) {
        if (newView == null) {
            return;
        }
        newView.setId(viewId);
        View oldView = container.findViewById(viewId);
        int index = container.indexOfChild(oldView);
        container.removeView(oldView);
        container.addView(newView, index);
        newView.setVisibility(View.GONE);
    }

    private ShowState mEmptyState, mProgressState, mErrorState, mContentState;
    private Animation mAnimIn, mAnimOut;

    private void initStates() {

        mEmptyState = new EmptyState();
        mProgressState = new ProgressState();
        mErrorState = new ErrorState();
        mContentState = new ContentState();

        initState(mEmptyState);
        initState(mProgressState);
        initState(mErrorState);
        initState(mContentState);
    }

    private void initState(ShowState state) {
        state.setAnimIn(mAnimIn);
        state.setAnimOut(mAnimOut);
        state.setFragmentView(mContentView);
    }

    private ShowState mLastState = new NonState();

    public void showContent(boolean animate) {
        if (mLastState == mContentState) {
            return;
        }
        mContentState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mContentState;
    }

    public void showEmpty(boolean animate) {
        if (mLastState == mEmptyState) {
            return;
        }
        mEmptyState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mEmptyState;
    }

    public void showError(boolean animate) {
        if (mLastState == mErrorState) {
            return;
        }
        mErrorState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mErrorState;
    }

    public void showProgress(boolean animate) {
        if (mLastState == mProgressState) {
            return;
        }
        mProgressState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mProgressState;
    }
}

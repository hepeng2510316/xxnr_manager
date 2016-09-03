package com.app.xxnr.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.injector.HasComponent;
import com.app.xxnr.injector.components.DaggerFragmentComponent;
import com.app.xxnr.injector.components.FragmentComponent;
import com.app.xxnr.injector.module.FragmentModule;
import com.app.xxnr.widget.ProgressBarCircularIndeterminate;
import com.app.xxnr.widget.ProgressFragment;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;


/**
 * 类描述：
 * 作者：何鹏 on 2016/3/9 17:53
 * 邮箱：hepeng@xinxinnongren.com
 */
public abstract class BaseFragment extends ProgressFragment {

    //error  empty  loading
    private TextView tvError, tvEmpty, tvLoading;
    //button to reload
    private Button btnReload;

    protected FragmentComponent mFragmentComponet;


    public abstract int initContentView();

    /**
     * 初始化 注入器
     */
    public abstract void initInjector();

    /**
     * 得到Activity传进来的值
     */
    public abstract void getBundle(Bundle bundle);

    /**
     * 初始化控件
     */
    public abstract void initUI(View view);

    /**
     * 在监听器之前把数据准备好
     */
    public abstract void initData();


    private void initComponent() {
        mFragmentComponet = DaggerFragmentComponent
                .builder()
                .fragmentModule(new FragmentModule(this, activity))
                .appComponent(activity.getAppComponent())
                .build();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initComponent();
        initInjector();
        getBundle(getArguments());
        initUI(view);
        initData();
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public View onCreateContentView(LayoutInflater inflater) {
        return inflater.inflate(initContentView(), null);
    }

    @Override
    public View onCreateContentErrorView(LayoutInflater inflater) {
        View error = inflater.inflate(R.layout.error_view_layout, null);
        tvError = (TextView) error.findViewById(R.id.tvError);
        //click event skip 1s
        RxView.clicks(error.findViewById(R.id.btnReload))
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> {
                    onReloadClicked();
                });
        return error;
    }

    @Override
    public View onCreateContentEmptyView(LayoutInflater inflater) {
        View empty = inflater.inflate(R.layout.empty_view_layout, null);
        tvEmpty = (TextView) empty.findViewById(R.id.tvEmpty);
        btnReload = (Button) empty.findViewById(R.id.btnReload);
        //click event skip 1s
        RxView.clicks(btnReload)
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> {
                    onReloadClicked();
                });
        return empty;
    }

    @Override
    public View onCreateProgressView(LayoutInflater inflater) {
        @SuppressLint("InflateParams") View loading = inflater.inflate(R.layout.loading_view_layout, null);
        tvLoading = (TextView) loading.findViewById(R.id.tvLoading);
        ProgressBarCircularIndeterminate progressBar =
                (ProgressBarCircularIndeterminate) loading.findViewById(R.id.progress_view);
        progressBar.setBackgroundColor(getResources().getColor(R.color.green));
        return loading;
    }

    public void setErrorText(String text) {
        tvError.setText(text);
    }

    public void setErrorText(int textResId) {
        setErrorText(getString(textResId));
    }

    public void setEmptyText(String text) {
        tvEmpty.setText(text);
    }

    public void setEmptyText(int textResId) {
        setEmptyText(getString(textResId));
    }

    public void setEmptyButtonVisible(int visible) {
        btnReload.setVisibility(visible);
    }

    public void setLoadingText(String text) {
        tvLoading.setText(text);
    }

    public void setLoadingText(int textResId) {
        setLoadingText(getString(textResId));
    }

    //Override this to reload
    public void onReloadClicked() {

    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}

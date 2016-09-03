package com.app.xxnr.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * @param <T>
 * @author Stephen Huang
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected int layoutId;


    public CommonAdapter(int layoutId,Context mContext) {
        this.layoutId = layoutId;
        this.mContext = mContext;
    }


    public void bindData(List<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size() > 0 ? mDatas.size() : 0;
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public List<T> getData() {
        return mDatas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, mDatas.get(position));
        return holder.getConvertView();
    }

    public abstract void convert(CommonViewHolder holder, T t);

}
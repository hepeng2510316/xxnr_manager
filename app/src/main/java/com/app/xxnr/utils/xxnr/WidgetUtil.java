package com.app.xxnr.utils.xxnr;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.xxnr.utils.RLog;


/**
 * 控件相关的工具类
 *
 * @author Bruce.Wang
 */
public class WidgetUtil {

    final static String TAG = "WidgetUtil";

    /**
     * 解决Listview在scrollview中嵌套问题
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            RLog.w(TAG, "adapter have not seted of this listview.");
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem == null) {
                continue;
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * 解决Listview在scrollview中嵌套问题
     *
     * @param gridView
     */
    public static void setListViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            RLog.w(TAG, "adapter have not seted of this listview.");
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, gridView);
            if (listItem == null) {
                continue;
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight
                + (gridView.getHeight() * (listAdapter.getCount() - 1));
        gridView.setLayoutParams(params);
    }

}

package com.app.xxnr.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 类描述：公共的PagerAdapter
 * 作者：何鹏 on 2016/5/3 17:51
 * 邮箱：hepeng@xinxinnongren.com
 */
public class CommonPagerAdapter extends FragmentPagerAdapter {

    private List<String> titles;
    private List<Fragment> fragments;

    public CommonPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments=fragments;
    }


    @Override
    public Fragment getItem(int arg0) {
        return  fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles!=null){
            return titles.get(position);
        }else {
            return null;
        }
    }
    //no destroy
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}

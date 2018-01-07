package com.flyman.app.web4android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.flyman.app.web4android.ui.fragment.HomePageItemFragment;
import com.flyman.app.web4android.modle.constant.GlobalConstant;

import java.util.ArrayList;
import java.util.List;


public class HomePageAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private List<HomePageItemFragment> fragmentList;
    public HomePageAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        initFragmentList();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return GlobalConstant.HomePageChildConstant.TITLE.length - 1;
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < GlobalConstant.HomePageChildConstant.TITLE.length - 1; i++) {
            fragmentList.add(HomePageItemFragment.newInstance(i));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return GlobalConstant.HomePageChildConstant.TITLE[position];
    }
}

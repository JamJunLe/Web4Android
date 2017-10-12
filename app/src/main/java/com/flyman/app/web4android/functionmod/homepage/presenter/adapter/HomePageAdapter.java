package com.flyman.app.web4android.functionmod.homepage.presenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.flyman.app.web4android.functionmod.homepage.view.HomePageItemFragment;
import com.flyman.app.web4android.moudle.Constant;

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
        return Constant.HomePageChildConstant.TITLE.length - 1;
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < Constant.HomePageChildConstant.TITLE.length - 1; i++) {
            fragmentList.add(HomePageItemFragment.newInstance(i));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Constant.HomePageChildConstant.TITLE[position];
    }
}

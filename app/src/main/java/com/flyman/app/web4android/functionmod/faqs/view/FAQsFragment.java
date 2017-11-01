package com.flyman.app.web4android.functionmod.faqs.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyman.app.web4android.R;
import com.flyman.app.web4android.base.BaseFragment;
import com.flyman.app.web4android.functionmod.faqs.presenter.FAQPresenter;
import com.flyman.app.web4android.functionmod.faqs.presenter.FAQsAdapter;
import com.flyman.app.web4android.functionmod.faqs.contract.FAQsContract;
import com.flyman.app.web4android.functionmod.faqs.modle.api.FAQsConstant;
import com.flyman.app.web4android.modle.Constant;


public class FAQsFragment extends BaseFragment implements FAQsContract.View, ViewPager.OnPageChangeListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private View rootView;
    private FAQPresenter mPresenter;
    private FAQsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        findWidget();
        initVariable();
        mPresenter = new FAQPresenter();
        return rootView;
    }

    @Override
    protected void findWidget() {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tablayout_index);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_index);
    }

    @Override
    protected void initVariable() {
        mAdapter = new FAQsAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(FAQsConstant.TITLE.length >= 3 ? Constant.HomePageChildConstant.TITLE.length - 1 : 1);
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }


}

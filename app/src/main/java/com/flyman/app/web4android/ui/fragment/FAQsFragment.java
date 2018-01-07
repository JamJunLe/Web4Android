package com.flyman.app.web4android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyman.app.web4android.R;
import com.flyman.app.web4android.adapter.FAQsAdapter;
import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IBaseView;
import com.flyman.app.web4android.base.MVPBaseFragment;
import com.flyman.app.web4android.io.api.FAQsConstant;
import com.flyman.app.web4android.modle.constant.GlobalConstant;
import com.flyman.app.web4android.presenter.FAQPresenter;


public class FAQsFragment extends MVPBaseFragment<IBaseView,BasePresenter<IBaseView>> implements IBaseView, ViewPager.OnPageChangeListener {

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
        mViewPager.setOffscreenPageLimit(FAQsConstant.TITLE.length >= 3 ? GlobalConstant.HomePageChildConstant.TITLE.length - 1 : 1);
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


    @Override
    protected BasePresenter<IBaseView> createPresenter() {
        return null;
    }
}

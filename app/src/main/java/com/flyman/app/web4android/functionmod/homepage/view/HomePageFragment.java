package com.flyman.app.web4android.functionmod.homepage.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyman.app.web4android.R;
import com.flyman.app.web4android.base.BaseFragment;
import com.flyman.app.web4android.functionmod.homepage.contract.HomePageContract;
import com.flyman.app.web4android.functionmod.homepage.presenter.HomePagePresenter;
import com.flyman.app.web4android.functionmod.homepage.presenter.adapter.HomePageAdapter;
import com.flyman.app.web4android.moudle.Constant;
import com.flyman.app.web4android.moudle.bean.BaseTask;


public class HomePageFragment extends BaseFragment implements HomePageContract.View, ViewPager.OnPageChangeListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private View rootView;
    private HomePagePresenter presenter;
    private HomePageAdapter mPageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        findWidget();
        initVariable();
        presenter.start(new BaseTask());
        return rootView;
    }

    @Override
    protected void findWidget() {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tablayout_index);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_index);
    }

    @Override
    protected void initVariable() {
        mPageAdapter = new HomePageAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(Constant.HomePageChildConstant.TITLE.length >= 3 ? Constant.HomePageChildConstant.TITLE.length - 1 : 1);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        presenter = new HomePagePresenter(this);
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

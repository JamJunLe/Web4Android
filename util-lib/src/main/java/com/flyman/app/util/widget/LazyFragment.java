package com.flyman.app.util.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public abstract class LazyFragment extends Fragment {
    protected boolean isViewInit = false;//当前fragment的view是否被实例化
    protected boolean isLazyNeeded = false;//是否需要懒加载
    protected ViewGroup mContainer;
    protected View extraView;//额外的内容(可以是进度条或者其他内容)
    protected LayoutInflater mInflater;
    protected boolean isFirstTimeGetDate = true;//是否是第一次获取数据
    protected Bundle mSavedInstanceState;

    protected int contentLayout = 0;//真实内容layout

    protected int extraLazyLayout = 0;//额外的懒加载布局(可以是进度条或者其他内容)

    protected int getExtraViewLayout() {
        return extraLazyLayout;
    }

    protected void setExtraViewLayout(int extraLazyLayout) {
        this.extraLazyLayout = extraLazyLayout;
    }

    protected int getContentLayout() {
        return contentLayout;
    }

    /**
     * 要求子类必须调用方法 设置真实显示的内容
     *
     * @param contentLayout 真实内容的布局
     * @return
     */
    protected void setContentView(int contentLayout) {
        this.contentLayout = contentLayout;
    }

    /**
     * onCreateView()只会执行一次 所以作为第一次进行判断当前页面是否可见
     * 可见 直接实例化真实内容
     * 不可见 进行懒加载
     *
     * @param
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        mSavedInstanceState = savedInstanceState;
        mContainer = new FrameLayout(getActivity());
        mContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //
        if (getUserVisibleHint() == true) {
            Log.e("LazyFragment", "(initContentView())");
            initContentView();
            return mContainer;
        } else {
            Log.e("LazyFragment", "lazy ----------------");
            isLazyNeeded = true;
            isViewInit = false;
            return mContainer;
        }
    }

    /**
     * 实例化真正显示的view
     *
     * @param
     * @return View
     */
    protected View initContentView() {
        View view = getContentView();
        isLazyNeeded = false;
        isViewInit = true;
        mContainer.addView(view);
        addExtraView();
        onContenViewLoad(view);
        return mContainer;
    }


    /**
     * 添加额外的显示内容
     *
     * @param
     * @return View
     */
    protected View addExtraView() {
        if (getExtraViewLayout() != 0 && isFirstTimeGetDate == true) {
            extraView = mInflater.inflate(getExtraViewLayout(), mContainer, false);
            mContainer.addView(extraView);
        }
        return mContainer;
    }

    /**
     * 移除额外的内容
     *
     * @param
     * @return View
     */
    protected View removeExtraView() {
        if (extraView != null) {
            mContainer.removeView(extraView);
        }
        return mContainer;
    }

    /**
     * 获取真正显示的view
     *
     * @param
     * @return View
     */
    protected View getContentView() {
        if (getContentLayout() == 0) {
            throw new RuntimeException("setContentView must be called");
        }
        return mInflater.inflate(getContentLayout(), mContainer, false);
    }

    /**
     * 懒加载实现的主体方法
     * 状态 1.可见 真实内容未实例化 ---直接实例化内容
     * 2.可见 真实内容实例化 ---不用管
     * 3.不可见 真实内容实例化 --不存在这种情况 @see onCreteView
     * 2.不可见 真实内容未实例化 ---懒加载
     *
     * @param
     * @return nothing
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //fragment可见  真正内容未初始化
        if (getUserVisibleHint() == true && isLazyNeeded == true && isViewInit == false) {
            if (getContentView() != null) {
                mContainer.removeAllViews();
                initContentView();
                isLazyNeeded = false;
                isViewInit = true;
                Log.e("LazyFragment", "setUserVisibleHint 这是懒加载");
            }
        }
    }


    /**
     * 当真实内容加载完成后 提供给子类 View container 用以findViewId操作
     *
     * @param
     * @return nothing
     */
    protected abstract void onContenViewLoad(View container);


}

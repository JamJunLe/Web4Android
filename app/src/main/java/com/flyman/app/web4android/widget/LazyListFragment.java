package com.flyman.app.web4android.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyman.app.web4android.R;
import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.MVPBaseFragment;

import java.util.List;

/**
 * FileName    : LazyListFragment
 * Description :懒加载,含下拉刷新列表
 *
 * @author : flyman
 * @version : 1.0
 * @Date :  2017/12/16 22:44
 **/

public abstract class LazyListFragment<V, P extends BasePresenter<V>> extends MVPBaseFragment implements SuperSwipeRefreshLayout.OnPullRefreshListener, SuperSwipeRefreshLayout.OnPushLoadMoreListener {
    protected boolean isViewInit = false;//当前fragment的view是否被实例化
    protected boolean isLazyNeeded = false;//是否需要懒加载
    protected ViewGroup mContainer;
    protected View extraView;//额外的内容(可以是进度条或者其他内容)
    protected LayoutInflater mInflater;
    protected boolean isFirstTimeGetDate = true;//是否是第一次获取数据
    protected Bundle mSavedInstanceState;
    protected int contentLayout = 0;//真实内容layout
    protected int extraLazyLayout = 0;//额外的懒加载布局(可以是进度条或者其他内容)
    //下拉刷新列表
    protected View headView;
    protected View footerView;
    protected TextView tv_head;//下拉头信息
    protected ProgressBar pb_head;//下拉头进度条
    protected TextView tv_footer;//上拉头信息
    protected ProgressBar pb_footer;//上拉头进度条
    protected ImageView iv_footer;//上拉进度条图片
    protected SuperSwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected List list;
    private ImageView iv_head;//下拉进度条图片

    protected int getExtraViewLayout() {
        return extraLazyLayout;
    }

    /**
     * 往布局文件中添加其他控件(在这边一般进行添加加载框或者进度条)
     *
     * @return nothing
     */
    protected void setExtraViewLayout(int extraLazyLayout) {
        this.extraLazyLayout = extraLazyLayout;
    }

    protected int getContentLayout() {
        if (contentLayout == 0) {
            setContentView(R.layout.reuse_swiperefresh_recyclerview);
            setExtraViewLayout(R.layout.layout_lazy_extra);
        }
        return contentLayout;
    }

    /**
     * onCreateView()只会执行一次 所以作为第一次进行判断当前页面是否可见<br/>
     * 可见:直接实例化<br/>
     * 不可见:进行懒加载
     *
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        mSavedInstanceState = savedInstanceState;
        mContainer = new FrameLayout(getActivity());
        mContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //
        if (getUserVisibleHint() == true) {
            Log.e("LazyListFragment", "(initContentView())");
            initContentView();
            return mContainer;
        } else {
            Log.e("LazyListFragment", "lazy ----------------");
            isLazyNeeded = true;
            isViewInit = false;
            return mContainer;
        }
    }

    /**
     * 实例化真正显示的view
     *
     * @return View
     */
    protected View initContentView() {
        View view = getContentView();
        isLazyNeeded = false;
        isViewInit = true;
        mContainer.addView(view);
        addExtraView();
        onContentViewLoad(view);
        return mContainer;
    }

    /**
     * 往布局文件中添加其他控件
     *
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
     * @return View
     */
    protected View getContentView() {
        if (getContentLayout() == 0) {
            throw new RuntimeException("setContentView must be called");
        }
        return mInflater.inflate(getContentLayout(), mContainer, false);
    }

    /**
     * 要求子类必须调用方法<br/>
     * 设置真正显示的内容布局
     *
     * @param contentLayout 真正显示的内容布局
     */
    protected void setContentView(int contentLayout) {
        this.contentLayout = contentLayout;
    }

    /**
     * 懒加载实现的主体方法
     * 状态 1.可见 真实内容未实例化 ---直接实例化内容
     * 2.可见 真实内容实例化 ---不用管
     * 3.不可见 真实内容实例化 --不存在这种情况 @see onCreteView
     * 2.不可见 真实内容未实例化 ---懒加载
     *
     * @return nothing
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //fragment可见  真正内容未初始化
        if (getUserVisibleHint() == true
                && isLazyNeeded == true
                && isViewInit == false
                && getContentView() != null) {
            mContainer.removeAllViews();
            initContentView();
            isLazyNeeded = false;
            isViewInit = true;
            Log.e("LazyListFragment", "setUserVisibleHint 这是懒加载");
        }
    }

    /**
     * 当真实内容加载完成后 提供给子类 View container 用以findViewId操作
     *
     * @return nothing
     */
    protected void onContentViewLoad(View container) {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) container.findViewById(R.id.swipe_view);
        recyclerView = (RecyclerView) container.findViewById(R.id.recy_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new LineItemDecortion(getActivity()));
        recyclerView.setAdapter(createAdapter());
        //设置swipeRefreshLayout
        swipeRefreshLayout.setHeaderView(createHeadView());
        swipeRefreshLayout.setFooterView(createFootView());
        swipeRefreshLayout.setOnPullRefreshListener(this);
        swipeRefreshLayout.setOnPushLoadMoreListener(this);
        swipeRefreshLayout.setEnabled(true);
    }

    protected abstract RecyclerView.Adapter createAdapter();

    /**
     * 下拉刷新的头部
     *
     * @return nothing
     */
    protected View createHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_head, null);
        tv_head = (TextView) headView.findViewById(R.id.tv_head);
        pb_head = (ProgressBar) headView.findViewById(R.id.pb_head);
        iv_head = (ImageView) headView.findViewById(R.id.iv_head);
        return headView;
    }

    /**
     * 下拉刷新的底部
     *
     * @return nothing
     */
    protected View createFootView() {
        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_footer, null);
        tv_footer = (TextView) footerView.findViewById(R.id.tv_footer);
        pb_footer = (ProgressBar) footerView.findViewById(R.id.pb_footer);
        iv_footer = (ImageView) footerView.findViewById(R.id.iv_footer);
        return footerView;
    }

    @Override
    public void onRefresh() {
        tv_head.setText("正在刷新");
        tv_head.setVisibility(View.VISIBLE);
        pb_head.setVisibility(View.GONE);
        pb_head.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPullDistance(int distance) {

    }

    @Override
    public void onPullEnable(boolean enable) {
        tv_head.setText(enable ? "松开刷新" : "下拉刷新");
        iv_head.setVisibility(View.VISIBLE);
        pb_head.setVisibility(View.GONE);
        iv_head.setRotation(enable ? 180 : 0);
    }

    @Override
    public void onLoadMore() {
        tv_footer.setText("正在加载...");
        tv_footer.setVisibility(View.VISIBLE);
        iv_footer.setVisibility(View.GONE);
        pb_footer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPushDistance(int distance) {

    }

    @Override
    public void onPushEnable(boolean enable) {
        tv_footer.setText(enable ? "松开加载" : "上拉加载");
        iv_footer.setVisibility(View.VISIBLE);
        pb_footer.setVisibility(View.GONE);
        iv_footer.setRotation(enable ? 0 : 180);
    }
    /**
     *  Convenience method to scroll to a certain position. RecyclerView does not implement scrolling logic, rather forwards the call to
     *
     *  @return nothing
     *  @param  position Convenience method to scroll to a certain position. RecyclerView does not implement scrolling logic, rather forwards the call to
     */
    protected void scrollToPosition(int position){
        recyclerView.scrollToPosition(position);
    }


}

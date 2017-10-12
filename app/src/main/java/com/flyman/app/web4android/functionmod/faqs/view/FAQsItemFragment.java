package com.flyman.app.web4android.functionmod.faqs.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.R;
import com.flyman.app.web4android.functionmod.faqs.presenter.FAQsItemAdapter;
import com.flyman.app.web4android.functionmod.faqs.presenter.FAQsItemPresenter;
import com.flyman.app.web4android.functionmod.faqs.contract.FAQsItemContract;
import com.flyman.app.web4android.functionmod.faqs.modle.FAQsItemTask;
import com.flyman.app.web4android.functionmod.faqs.modle.api.FAQsConstant;
import com.flyman.app.web4android.functionmod.faqs.modle.bean.FAQs;
import com.flyman.app.web4android.functionmod.homepage.view.ArticleDetails;
import com.flyman.app.web4android.moudle.Constant;
import com.flyman.app.web4android.widget.LazyFragment;
import com.flyman.app.web4android.widget.LineItemDecortion;
import com.flyman.app.web4android.widget.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class FAQsItemFragment extends LazyFragment implements FAQsItemContract.View, SuperSwipeRefreshLayout.OnPullRefreshListener, SuperSwipeRefreshLayout.OnPushLoadMoreListener, View.OnClickListener {
    private View headView;
    private View footerView;
    private TextView tv_head;//下拉头信息
    private ProgressBar pb_head;//下拉头进度条
    private ImageView iv_head;//下拉进度条图片

    private TextView tv_footer;//上拉头信息
    private ProgressBar pb_footer;//上拉头进度条
    private ImageView iv_footer;//上拉进度条图片
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FAQsItemPresenter mPresenter;
    private FAQsItemAdapter mAdapter;
    private List<FAQs> mList;
    private int args;

    public static FAQsItemFragment getInstance(int args) {
        Bundle mBundle = new Bundle();
        mBundle.putInt(FAQsConstant.ARGS_FAQS, args);
        FAQsItemFragment mFaQsItemFragment = new FAQsItemFragment();
        mFaQsItemFragment.setArguments(mBundle);
        return mFaQsItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments().getInt(FAQsConstant.ARGS_FAQS);
        setContentView(R.layout.reuse_swiperefresh_recyclerview);
        setExtraViewLayout(R.layout.layout_lazy_extra);
    }

    @Override
    protected void onContenViewLoad(View container) {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) container.findViewById(R.id.swipe_view);
        recyclerView = (RecyclerView) container.findViewById(R.id.recy_view);
        //设置swipeRefreshLayout
        swipeRefreshLayout.setHeaderView(createHeadView());
        swipeRefreshLayout.setFooterView(createFootView());
        swipeRefreshLayout.setOnPullRefreshListener(this);
        swipeRefreshLayout.setOnPushLoadMoreListener(this);
        swipeRefreshLayout.setEnabled(true);
        //设置adapter
        mList = new ArrayList<>();
        mAdapter = new FAQsItemAdapter(mList, getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new LineItemDecortion(getActivity()));
        mPresenter = new FAQsItemPresenter(this, args);
        onStartPresent();

    }

    private View createHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_head, null);
        tv_head = (TextView) headView.findViewById(R.id.tv_head);
        pb_head = (ProgressBar) headView.findViewById(R.id.pb_head);
        iv_head = (ImageView) headView.findViewById(R.id.iv_head);
        iv_head.setVisibility(View.VISIBLE);
        pb_head.setVisibility(View.VISIBLE);
        return headView;

    }

    private View createFootView() {
        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_footer, null);
        tv_footer = (TextView) footerView.findViewById(R.id.tv_footer);
        pb_footer = (ProgressBar) footerView.findViewById(R.id.pb_footer);
        iv_footer = (ImageView) footerView.findViewById(R.id.iv_footer);
        return footerView;

    }

    @Override
    public void onStartPresent() {
        mPresenter.start(new FAQsItemTask(FAQsItemTask.Id.PULL_REFRESH));
    }


    @Override
    public void cleanViewState() {
        removeExtraView();
    }


    @Override
    public <T extends ArrayList> void showPullRefreshData(T data) {
        mList.clear();
        mList.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public <T extends ArrayList> void showPushLoadmoreData(T data) {
        int size = mList.size();
        mList.addAll(data);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(size);
    }

    @Override
    public void setRefreshEnable(boolean isEnable) {
        swipeRefreshLayout.setEnabled(isEnable);
    }

    @Override
    public void setRefreshing(boolean isReFreshing) {
        swipeRefreshLayout.setRefreshing(isReFreshing);
    }

    @Override
    public void setLoadMOreRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setLoadMore(isRefreshing);
    }

    @Override
    public <T> void showErrorMsg(T t) {
        showLongToast(t.toString());
    }

    //点击某item时的回调
    @Override
    public void onClick(View v) {
        LogUtils.e("onClick", "onClick " + v.getTag());
        if (mList != null && mList.size() > 0) {
            int position = (int) v.getTag();
            String url = mList.get(position).getHref();
            Intent mIntent = new Intent(getContext(), ArticleDetails.class);
            mIntent.putExtra(Constant.IntentExtraKeyConstant.KEY_LIST_2_DETAILS, url);
            startActivity(mIntent);
        }

    }


    @Override
    public void onRefresh() {
        mPresenter.start(new FAQsItemTask(FAQsItemTask.Id.PULL_REFRESH));
        ;//刷新当前页
        tv_head.setText("正在刷新");
        tv_head.setVisibility(View.VISIBLE);
        pb_head.setVisibility(View.GONE);
        pb_head.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPullEnable(boolean enable) {
        tv_head.setText(enable ? "松开刷新" : "下拉刷新");
        iv_head.setVisibility(View.VISIBLE);
        pb_head.setVisibility(View.GONE);
        iv_head.setRotation(enable ? 180 : 0);
    }

    @Override
    public void onPullDistance(int distance) {

    }

    //下拉加载更多
    @Override
    public void onLoadMore() {
        tv_footer.setText("正在加载...");
        tv_footer.setVisibility(View.VISIBLE);
        iv_footer.setVisibility(View.GONE);
        pb_footer.setVisibility(View.VISIBLE);
        mPresenter.start(new FAQsItemTask(FAQsItemTask.Id.PUSH_LOAD_MORE_REFRESH));
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


}

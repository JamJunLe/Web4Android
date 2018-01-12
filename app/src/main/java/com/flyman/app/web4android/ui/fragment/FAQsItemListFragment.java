package com.flyman.app.web4android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.R;
import com.flyman.app.web4android.adapter.FAQsItemAdapter;
import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IListView;
import com.flyman.app.web4android.io.api.FAQsConstant;
import com.flyman.app.web4android.modle.bean.FAQs;
import com.flyman.app.web4android.modle.constant.GlobalConstant;
import com.flyman.app.web4android.modle.task.NewsTask;
import com.flyman.app.web4android.presenter.FAQPresenter;
import com.flyman.app.web4android.presenter.FAQsItemPresenter;
import com.flyman.app.web4android.ui.activity.ArticleDetails;
import com.flyman.app.web4android.widget.LazyListFragment;

import java.util.ArrayList;
import java.util.List;

public class FAQsItemListFragment extends LazyListFragment<IListView, FAQPresenter> implements IListView, View.OnClickListener {
    private FAQsItemPresenter mPresenter;
    private FAQsItemAdapter mAdapter;
    private List<FAQs> mList;
    private int args;

    public static FAQsItemListFragment getInstance(int args) {
        Bundle mBundle = new Bundle();
        mBundle.putInt(FAQsConstant.ARGS_FAQS, args);
        FAQsItemListFragment mFaQsItemFragment = new FAQsItemListFragment();
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
    protected BasePresenter createPresenter() {
        return mPresenter = new FAQsItemPresenter(this, args);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        //设置adapter
        mList = new ArrayList<>();
        mAdapter = new FAQsItemAdapter(mList, getActivity(), this);
        return mAdapter;
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
    public <T extends ArrayList> void showPushLoadMoreData(T data) {
        int size = mList.size();
        mList.addAll(data);
        mAdapter.notifyDataSetChanged();
        scrollToPosition(size);
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
            mIntent.putExtra(GlobalConstant.IntentExtraKeyConstant.KEY_LIST_2_DETAILS, url);
            startActivity(mIntent);
        }

    }


    @Override
    public void onRefresh() {
        mPresenter.doTask(new NewsTask(NewsTask.Type.PULL_REFRESH));
    }


    //下拉加载更多
    @Override
    public void onLoadMore() {
        mPresenter.doTask(new NewsTask(NewsTask.Type.PUSH_LOAD_MORE_REFRESH));
    }


}

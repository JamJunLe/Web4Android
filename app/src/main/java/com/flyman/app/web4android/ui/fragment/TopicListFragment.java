package com.flyman.app.web4android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.flyman.app.web4android.adapter.TopicAdapter;
import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IListView;
import com.flyman.app.web4android.modle.bean.Topic;
import com.flyman.app.web4android.modle.constant.GlobalConstant;
import com.flyman.app.web4android.modle.task.TopicNewsTask;
import com.flyman.app.web4android.presenter.NewsPresenter;
import com.flyman.app.web4android.ui.activity.ArticleDetails;
import com.flyman.app.web4android.widget.LazyListFragment;

import java.util.ArrayList;

public class TopicListFragment extends LazyListFragment<IListView, NewsPresenter> implements IListView, View.OnClickListener {
    private NewsPresenter mTopicPresenter;
    private TopicAdapter mTopicAdapter;
    private ArrayList<Topic> mTopicList;
    private Bundle mBundle;
    private int args;
    public final String TAG = this.getClass().getName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        args = mBundle.getInt(TAG);
        mTopicPresenter.doTask(new TopicNewsTask(TopicNewsTask.Id.PULL_REFRESH));
    }

    @Override
    protected BasePresenter createPresenter() {
        return mTopicPresenter = new NewsPresenter();
    }

    @Override
    public <T extends ArrayList> void showPullRefreshData(T data) {
        mTopicList.clear();
        mTopicList.addAll(data);
        mTopicAdapter.notifyDataSetChanged();
    }

    @Override
    public <T extends ArrayList> void showPushLoadMoreData(T data) {
        int size = mTopicList.size();
        mTopicList.addAll(data);
        mTopicAdapter.notifyDataSetChanged();
        scrollToPosition(size);
    }

    //设置刷新控件的可用状态
    @Override
    public void setRefreshEnable(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
        Log.e("setRefreshing", "isRefreshing =" + isRefreshing);
    }

    @Override
    public void setLoadMOreRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setLoadMore(isRefreshing);
    }

    @Override
    public <T> void showErrorMsg(T t) {
        showLongToast(t.toString());
    }

    /**
     * 清除第一次加载是显示的进度条
     *
     * @return nothing
     */
    @Override
    public void cleanViewState() {
        removeExtraView();
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        mTopicList = new ArrayList<>();
        return mTopicAdapter = new TopicAdapter(mTopicList, getActivity(), this);
    }

    @Override
    public void onRefresh() {
        mTopicPresenter.doTask(new TopicNewsTask(TopicNewsTask.Id.PULL_REFRESH));
    }

    @Override
    public void onLoadMore() {
        mTopicPresenter.doTask(new TopicNewsTask(TopicNewsTask.Id.PUSH_LOAD_MORE_REFRESH));
    }

    @Override
    public void onClick(View v) {
        if (mTopicList != null && mTopicList.size() > 0) {
            int position = (int) v.getTag();
            String url = mTopicList.get(position).getHref();
            Intent mIntent = new Intent(getContext(), ArticleDetails.class);
            mIntent.putExtra(GlobalConstant.IntentExtraKeyConstant.KEY_LIST_2_DETAILS, url);
            startActivity(mIntent);
        }
    }
}


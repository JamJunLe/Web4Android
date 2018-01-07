package com.flyman.app.web4android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.flyman.app.web4android.adapter.CodeAdapter;
import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IListView;
import com.flyman.app.web4android.io.api.NetUrl;
import com.flyman.app.web4android.modle.bean.CodeArticle;
import com.flyman.app.web4android.modle.constant.GlobalConstant;
import com.flyman.app.web4android.modle.task.CodeNewsTask;
import com.flyman.app.web4android.presenter.CodePresenter;
import com.flyman.app.web4android.ui.activity.ArticleDetails;
import com.flyman.app.web4android.widget.LazyListFragment;
import com.flyman.app.web4android.widget.SuperSwipeRefreshLayout;

import java.util.ArrayList;


public class CodeListFragment extends LazyListFragment<IListView, CodePresenter> implements IListView, SuperSwipeRefreshLayout.OnPullRefreshListener, View.OnClickListener, SuperSwipeRefreshLayout.OnPushLoadMoreListener {

    private CodePresenter mCodePresenter;
    private CodeAdapter mCodeAdapter;
    private ArrayList<CodeArticle> mCodeArticlesList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter createPresenter() {
        return mCodePresenter = new CodePresenter(this);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        mCodeArticlesList = new ArrayList<>();
        mCodeAdapter = new CodeAdapter(mCodeArticlesList, getActivity(), this);
        return null;
    }
//
//    @Override
//    public void onStartPresent() {
//        mCodePresenter.doTask(new CodeNewsTask(CodeNewsTask.Id.PULL_REFRESH));
//    }

    @Override
    public <T extends ArrayList> void showPullRefreshData(T data) {
        mCodeArticlesList.clear();
        mCodeArticlesList.addAll(data);
        mCodeAdapter.notifyDataSetChanged();
    }

    @Override
    public <T extends ArrayList> void showPushLoadMoreData(T data) {
        int size = mCodeArticlesList.size();
        mCodeArticlesList.addAll((ArrayList) data);
        mCodeAdapter.notifyDataSetChanged();
        scrollToPosition(size);
    }


    //设置刷新控件的可用状态
    @Override
    public void setRefreshEnable(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void setRefreshing(boolean isReFreshing) {
        swipeRefreshLayout.setRefreshing(isReFreshing);
        Log.e("setRefreshing", "isReFreshing =" + isReFreshing);
    }

    @Override
    public void setLoadMOreRefreshing(boolean isReFreshing) {
        swipeRefreshLayout.setLoadMore(isReFreshing);
    }


    @Override
    public <T> void showErrorMsg(T t) {
        showLongToast(t.toString());
    }

    /**
     * 清除第一次加载是显示的进度条
     *
     * @param
     * @return nothing
     */
    @Override
    public void cleanViewState() {
        removeExtraView();
    }


    @Override
    public void onRefresh() {
        mCodePresenter.doTask(new CodeNewsTask(CodeNewsTask.Id.PULL_REFRESH));
    }

    @Override
    public void onLoadMore() {
        mCodePresenter.doTask(new CodeNewsTask(CodeNewsTask.Id.PUSH_LOAD_MORE_REFRESH));

    }

    @Override
    public void onClick(View v) {
        if (mCodeArticlesList != null && mCodeArticlesList.size() > 0) {
            int position = (int) v.getTag();
            String url = NetUrl.BASE_URL + mCodeArticlesList.get(position).getCodeId();
            Intent mIntent = new Intent(getContext(), ArticleDetails.class);
            mIntent.putExtra(GlobalConstant.IntentExtraKeyConstant.KEY_LIST_2_DETAILS, url);
            startActivity(mIntent);
        }

    }
}

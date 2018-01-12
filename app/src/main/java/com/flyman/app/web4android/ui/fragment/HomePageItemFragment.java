package com.flyman.app.web4android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.adapter.HomePageItemAdapter;
import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IListView;
import com.flyman.app.web4android.io.api.NetUrl;
import com.flyman.app.web4android.modle.bean.Article;
import com.flyman.app.web4android.modle.constant.GlobalConstant;
import com.flyman.app.web4android.modle.task.NewsTask;
import com.flyman.app.web4android.presenter.HomePageItemPresenter;
import com.flyman.app.web4android.presenter.NewsPresenter;
import com.flyman.app.web4android.ui.activity.ArticleDetails;
import com.flyman.app.web4android.widget.LazyListFragment;
import com.flyman.app.web4android.widget.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static com.flyman.app.web4android.modle.constant.GlobalConstant.HomePageChildConstant.ARGS_HOMEPAGE_ITEM_FRAGMENT;

/**
 * @author Flyman
 * @ClassName HomePageChildConstant
 * @description 首页fragment中的子页, 可复用
 * @date 2017/4/6 2:14
 */
public class HomePageItemFragment extends LazyListFragment<IListView, NewsPresenter> implements IListView, SuperSwipeRefreshLayout.OnPullRefreshListener, SuperSwipeRefreshLayout.OnPushLoadMoreListener, View.OnClickListener {
    private int args;
    private HomePageItemAdapter mAdapter;
    private List<Article> mList;

    public static HomePageItemFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARGS_HOMEPAGE_ITEM_FRAGMENT, position);
        HomePageItemFragment fragment = new HomePageItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments().getInt(ARGS_HOMEPAGE_ITEM_FRAGMENT);
    }

    @Override
    protected BasePresenter createPresenter() {
        mPresenter = new HomePageItemPresenter(this, args);
        return mPresenter;
    }


    @Override
    protected RecyclerView.Adapter createAdapter() {
        mList = new ArrayList<>();
        mAdapter = new HomePageItemAdapter(mList, getActivity(), this);
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
    public void setRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
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
            String url = NetUrl.BASE_URL + mList.get(position).getArticleId();
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

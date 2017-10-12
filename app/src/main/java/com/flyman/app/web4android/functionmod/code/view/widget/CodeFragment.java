package com.flyman.app.web4android.functionmod.code.view.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyman.app.web4android.R;
import com.flyman.app.web4android.functionmod.code.contract.CodeContract;
import com.flyman.app.web4android.functionmod.code.modle.CodeTask;
import com.flyman.app.web4android.functionmod.code.modle.bean.CodeArticle;
import com.flyman.app.web4android.functionmod.code.presenter.CodeAdapter;
import com.flyman.app.web4android.functionmod.code.presenter.CodePresenter;
import com.flyman.app.web4android.functionmod.homepage.view.ArticleDetails;
import com.flyman.app.web4android.io.api.NetUrl;
import com.flyman.app.web4android.moudle.Constant;
import com.flyman.app.web4android.widget.LazyFragment;
import com.flyman.app.web4android.widget.LineItemDecortion;
import com.flyman.app.web4android.widget.SuperSwipeRefreshLayout;

import java.util.ArrayList;


public class CodeFragment extends LazyFragment implements CodeContract.View, SuperSwipeRefreshLayout.OnPullRefreshListener, View.OnClickListener, SuperSwipeRefreshLayout.OnPushLoadMoreListener {

    private View headView;
    private View footerView;
    private TextView tv_head;//下拉头信息
    private ProgressBar pb_head;//下拉头进度条
    private ImageView iv_head;//下拉进度条图片

    private TextView tv_footer;//上拉头信息
    private ProgressBar pb_footer;//上拉头进度条
    private ImageView iv_footer;//上拉进度条图片
    private ProgressBar pb_loading;//页面中央的进度条,用以显示第一次的加载进度
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CodePresenter mCodePresenter;
    private CodeAdapter mCodeAdapter;
    private ArrayList<CodeArticle> mCodeArticlesList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reuse_swiperefresh_recyclerview);
        setExtraViewLayout(R.layout.layout_lazy_extra);
    }

    @Override
    protected void onContenViewLoad(View container) {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) container.findViewById(R.id.swipe_view);
        recyclerView = (RecyclerView) container.findViewById(R.id.recy_view);
        pb_loading = (ProgressBar) container.findViewById(R.id.pb_loading);
        //设置swipeRefreshLayout
        swipeRefreshLayout.setHeaderView(createHeadView());
        swipeRefreshLayout.setFooterView(createFootView());
        swipeRefreshLayout.setOnPullRefreshListener(this);
        swipeRefreshLayout.setOnPushLoadMoreListener(this);
        swipeRefreshLayout.setEnabled(true);
        //设置adapter
        mCodeArticlesList = new ArrayList<>();
        mCodeAdapter = new CodeAdapter(mCodeArticlesList, getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mCodeAdapter);
        recyclerView.addItemDecoration(new LineItemDecortion(getActivity()));
        mCodePresenter = new CodePresenter(this);
        onStartPresent();

    }

    private View createHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_head, null);
        tv_head = (TextView) headView.findViewById(R.id.tv_head);
        pb_head = (ProgressBar) headView.findViewById(R.id.pb_head);
        iv_head = (ImageView) headView.findViewById(R.id.iv_head);
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
        mCodePresenter.start(new CodeTask(CodeTask.Id.PULL_REFRESH));
    }

    @Override
    public <T extends ArrayList> void showPullRefreshData(T data) {
        mCodeArticlesList.clear();
        mCodeArticlesList.addAll(data);
        mCodeAdapter.notifyDataSetChanged();
    }


    @Override
    public <T extends ArrayList> void showPushLoadmoreData(T data) {
        int size = mCodeArticlesList.size();
        mCodeArticlesList.addAll((ArrayList) data);
        mCodeAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(size);
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
        tv_head.setText("正在刷新");
        tv_head.setVisibility(View.VISIBLE);
        pb_head.setVisibility(View.GONE);
        pb_head.setVisibility(View.VISIBLE);
        mCodePresenter.start(new CodeTask(CodeTask.Id.PULL_REFRESH));
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
        mCodePresenter.start(new CodeTask(CodeTask.Id.PUSH_LOAD_MORE_REFRESH));

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

    @Override
    public void onClick(View v) {
        if (mCodeArticlesList != null && mCodeArticlesList.size() > 0) {
            int position = (int) v.getTag();
            String url = NetUrl.BASE_URL + mCodeArticlesList.get(position).getCodeId();
            Intent mIntent = new Intent(getContext(), ArticleDetails.class);
            mIntent.putExtra(Constant.IntentExtraKeyConstant.KEY_LIST_2_DETAILS, url);
            startActivity(mIntent);
        }

    }
}

package com.flyman.app.web4android.functionmod.homepage.presenter;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.functionmod.homepage.contract.HomePageChildContract;
import com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildModle;
import com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask;
import com.flyman.app.web4android.functionmod.homepage.modle.bean.Article;
import com.flyman.app.web4android.functionmod.homepage.view.HomePageItemFragment;
import com.flyman.app.web4android.moudle.bean.BaseTask;

import java.util.ArrayList;

public class HomePageItemPresenter implements HomePageChildContract.Presenter, IModuleCallback {

    private HomePageItemFragment mView;
    private HomePageChildModle mModle;
    private HomePageChildTask mTask;
    private int mTaskId;
    private int currentPage = 1;
    private int totalPage = 0;
    private int totalArticle = 0;
    private int args;

    public HomePageItemPresenter(HomePageItemFragment mView, int args) {
        this.mView = mView;
        this.args = args;
        mModle = new HomePageChildModle(this, args);
    }
    @Override
    public <T extends BaseTask> void start(T t) {
        mTask = (HomePageChildTask) t;
        mTaskId = mTask.getTaskId();
        switch (mTaskId) {
            case HomePageChildTask.Id.PULL_REFRESH: {
                getPullRefreshData();
                break;
            }
            case HomePageChildTask.Id.PUSH_LOAD_MORE_REFRESH: {
                getLoadMoreData();
                break;
            }
            default: {
            }
        }

    }

    @Override
    public void getPullRefreshData() {
        currentPage = 1;
        mModle.start(mTask);
    }

    @Override
    public void getLoadMoreData() {
        //已经加载完全
        if (totalPage > 0 && currentPage >= totalPage) {
            mView.showErrorMsg(HomePageChildTask.Message.MSG_PUSH_LOAD_MORE_REFRESH_FINISH);//已经加载了所有的数据
            mView.setRefreshEnable(true);
            mView.setLoadMOreRefreshing(false);
            return;
        }
        currentPage = currentPage + 1;
        mTask.setPageNum(currentPage);
        mTask.setTotalCodes(totalArticle);
        mModle.start(mTask);
    }
    @Override
    public void onLoadTaskCallback() {
        switch (mTaskId) {
            //刷新操作
            case HomePageChildTask.Id.PULL_REFRESH: {
                mView.setRefreshing(true);
                break;
            }
            //加载分页
            case HomePageChildTask.Id.PUSH_LOAD_MORE_REFRESH: {
                mView.setLoadMOreRefreshing(true);
                break;
            }
            default: {

            }
        }
    }

    @Override
    public <T> void onTaskFailCallback(T result) {
        LogUtils.e("onTaskFailCallback", "" + result.toString());
        mView.cleanViewState();//清除第一次加载的进度条
        switch (mTaskId) {
            //刷新操作
            case HomePageChildTask.Id.PULL_REFRESH: {
                currentPage = 1;
                mView.showErrorMsg(result);//刷新失败
                mView.setRefreshing(false);
                break;
            }
            //加载分页
            case HomePageChildTask.Id.PUSH_LOAD_MORE_REFRESH: {
                currentPage = currentPage - 1;
                mView.showErrorMsg(result);//上拉加载失败
                mView.setLoadMOreRefreshing(false);
                break;
            }
            default: {

            }
        }
    }


    @Override
    public <T> void onTaskSucessCallback(T result) {
        switch (mTaskId) {
            //刷新操作
            case HomePageChildTask.Id.PULL_REFRESH: {
                ArrayList<Article> list = (ArrayList) result;
                currentPage = 1;
                Article mArticle = list.get(1);
                totalPage = mArticle.getTotalPage();//总页数
                totalArticle = mArticle.getTotalArticles();//总文章数
                mView.showPullRefreshData(list);
                mView.setRefreshing(false);
                break;
            }
            //加载分页
            case HomePageChildTask.Id.PUSH_LOAD_MORE_REFRESH: {
                mView.showPushLoadmoreData((ArrayList) result);
                mView.setLoadMOreRefreshing(false);
                break;
            }
            default: {

            }
        }

    }

    @Override
    public void onTaskCompleteCallback() {
        mView.setRefreshing(false);
        mView.setLoadMOreRefreshing(false);
        mView.setRefreshEnable(true);
        mView.cleanViewState();//清除第一次加载的进度条
    }

    @Override
    public void onTaskCancellCallback() {

    }

}
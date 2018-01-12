package com.flyman.app.web4android.presenter;

import android.util.Log;

import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IListPresenter;
import com.flyman.app.web4android.base.IListView;
import com.flyman.app.web4android.base.IModelCallback;
import com.flyman.app.web4android.modle.CodeModule;
import com.flyman.app.web4android.modle.bean.CodeArticle;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.modle.task.NewsTask;

import java.util.ArrayList;

public class CodePresenter extends BasePresenter<IListView> implements IListPresenter, IModelCallback {

    private IListView mView;
    private CodeModule mCodeModule;
    private NewsTask mCodeTask;
    private int mTaskId;
    private int currentPage = 1;
    private int totalPage = 0;
    private int totalCodes = 0;

    public CodePresenter(IListView mView) {
        this.mView = mView;
        mCodeModule = new CodeModule(this);
    }

    @Override
    public void getPullRefreshData() {
        currentPage = 1;
        mCodeModule.doTask(mCodeTask, this);
    }

    @Override
    public void getLoadMoreData() {
        //已经加载完全
        if (totalPage > 0 && currentPage >= totalPage) {
            mView.showErrorMsg(NewsTask.Message.MSG_PUSH_LOAD_MORE_REFRESH_FINISH);//已经加载了所有的数据
            mView.setRefreshEnable(true);
            mView.setLoadMOreRefreshing(false);
            return;
        }
        currentPage = currentPage + 1;
        mCodeTask.setPageIndex(currentPage);
        mCodeTask.setNewsAmount(totalCodes);
        mCodeModule.doTask(mCodeTask, this);
    }


    @Override
    public <T extends BaseTask> void doTask(T task) {
        mCodeTask = (NewsTask) task;
        mTaskId = mCodeTask.getTaskId();
        switch (mTaskId) {
            case NewsTask.Type.PULL_REFRESH: {
                getPullRefreshData();
                break;
            }
            case NewsTask.Type.PUSH_LOAD_MORE_REFRESH: {
                getLoadMoreData();
                break;
            }
            default: {
            }
        }
    }

    @Override
    public <T extends BaseTask> void parseFinishedTask(T task) {

    }

    @Override
    public void onPreTask(BaseTask task) {
        switch (mTaskId) {
            //刷新操作
            case NewsTask.Type.PULL_REFRESH: {
                mView.setRefreshing(true);
                break;
            }
            //加载分页
            case NewsTask.Type.PUSH_LOAD_MORE_REFRESH: {
                mView.setLoadMOreRefreshing(true);
                break;
            }
            default: {

            }
        }
    }

    @Override
    public void onTaskFail(Object data, BaseTask task) {
        mView.cleanViewState();//清除第一次加载的进度条
        switch (mTaskId) {
            //刷新操作
            case NewsTask.Type.PULL_REFRESH: {
                currentPage = 1;
                mView.showErrorMsg(data);//刷新失败
                mView.setRefreshing(false);
                break;
            }
            //加载分页
            case NewsTask.Type.PUSH_LOAD_MORE_REFRESH: {
                currentPage = currentPage - 1;
                mView.showErrorMsg(data);//上拉加载失败
                mView.setLoadMOreRefreshing(false);
                break;
            }
            default: {

            }
        }
    }

    @Override
    public void onTaskCancel(BaseTask task) {

    }

    @Override
    public void onTaskSuccess(Object result, BaseTask task) {
        switch (mTaskId) {
            //刷新操作
            case NewsTask.Type.PULL_REFRESH: {
                ArrayList<CodeArticle> list = (ArrayList) result;
                currentPage = 1;
                CodeArticle mCodeCodeArticle = list.get(1);
                totalPage = mCodeCodeArticle.getTotalPage();//总页数
                totalCodes = mCodeCodeArticle.getTotalCodes();//总文章数
                mView.showPullRefreshData(list);
                mView.setRefreshing(false);
                Log.e("onTaskSuccess", "mCodeCodeArticle.toString() =" + mCodeCodeArticle.toString());
                break;
            }
            //加载分页
            case NewsTask.Type.PUSH_LOAD_MORE_REFRESH: {
                mView.showPushLoadMoreData((ArrayList) result);
                mView.setLoadMOreRefreshing(false);
                break;
            }
            default: {

            }
        }
    }

    @Override
    public void onTaskFinish(BaseTask task) {
        mView.setRefreshing(false);
        mView.setLoadMOreRefreshing(false);
        mView.setRefreshEnable(true);
        mView.cleanViewState();//清除第一次加载的进度条
    }
}

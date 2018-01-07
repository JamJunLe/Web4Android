package com.flyman.app.web4android.presenter;

import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IListPresenter;
import com.flyman.app.web4android.base.IListView;
import com.flyman.app.web4android.base.IModelCallback;
import com.flyman.app.web4android.modle.FAQsItemModle;
import com.flyman.app.web4android.modle.bean.FAQs;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.modle.task.CodeNewsTask;
import com.flyman.app.web4android.modle.task.FAQsItemNewsTask;

import java.util.ArrayList;

public class FAQsItemPresenter extends BasePresenter<IListView> implements IListPresenter, IModelCallback {
    private IListView mView;
    private FAQsItemModle mModle;
    private FAQsItemNewsTask mTask;
    private int mTaskId;
    private int currentPage = 1;
    private int totalPage = 0;
    private int totalArticle = 0;
    private int args;

    public FAQsItemPresenter(IListView mView, int args) {
        this.mView = mView;
        this.args = args;
        mModle = new FAQsItemModle(this, args);
    }

    @Override
    public void getPullRefreshData() {
        currentPage = 1;
        mModle.doTask(mTask,this);
    }

    @Override
    public void getLoadMoreData() {
        //已经加载完全
        if (totalPage > 0 && currentPage >= totalPage) {
            mView.showErrorMsg(CodeNewsTask.Message.MSG_PUSH_LOAD_MORE_REFRESH_FINISH);//已经加载了所有的数据
            mView.setRefreshEnable(true);
            mView.setLoadMOreRefreshing(false);
            return;
        }
        currentPage = currentPage + 1;
        mTask.setPageNum(currentPage);
        mTask.setTotalCodes(totalArticle);
        mModle.doTask(mTask,this);
    }

    @Override
    public void onPreTask(BaseTask task) {
        switch (mTaskId) {
            //刷新操作
            case CodeNewsTask.Id.PULL_REFRESH: {
                mView.setRefreshing(true);
                break;
            }
            //加载分页
            case CodeNewsTask.Id.PUSH_LOAD_MORE_REFRESH: {
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
            case CodeNewsTask.Id.PULL_REFRESH: {
                currentPage = 1;
                mView.showErrorMsg(data);//刷新失败
                mView.setRefreshing(false);
                break;
            }
            //加载分页
            case CodeNewsTask.Id.PUSH_LOAD_MORE_REFRESH: {
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
            case CodeNewsTask.Id.PULL_REFRESH: {
                ArrayList<FAQs> list = (ArrayList) result;
                currentPage = 1;
                FAQs mFaQs = list.get(0);
                totalPage = mFaQs.getTotalPage();//总页数
                totalArticle = mFaQs.getTotalFaqs();//总文章数
                if (totalArticle == 0) {
                    mView.setRefreshing(false);
                    mView.showErrorMsg("暂无问题");
                } else {
                    mView.showPullRefreshData(list);
                    mView.setRefreshing(false);
                }
                break;
            }
            //加载分页
            case CodeNewsTask.Id.PUSH_LOAD_MORE_REFRESH: {
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

    @Override
    public <T extends BaseTask> void doTask(T task) {
        mTask = (FAQsItemNewsTask) task;
        mTaskId = mTask.getTaskId();
        switch (mTaskId) {
            case FAQsItemNewsTask.Id.PULL_REFRESH: {
                getPullRefreshData();
                break;
            }
            case FAQsItemNewsTask.Id.PUSH_LOAD_MORE_REFRESH: {
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
}


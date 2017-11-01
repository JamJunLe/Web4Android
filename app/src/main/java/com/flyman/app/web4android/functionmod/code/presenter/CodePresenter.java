package com.flyman.app.web4android.functionmod.code.presenter;

import android.util.Log;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.functionmod.code.contract.CodeContract;
import com.flyman.app.web4android.functionmod.code.modle.CodeModule;
import com.flyman.app.web4android.functionmod.code.modle.CodeTask;
import com.flyman.app.web4android.functionmod.code.modle.bean.CodeArticle;
import com.flyman.app.web4android.modle.bean.BaseTask;

import java.util.ArrayList;

public class CodePresenter implements CodeContract.Presenter, IModuleCallback {

    private CodeContract.View mView;
    private CodeModule mCodeModule;
    private CodeTask mCodeTask;
    private int mTaskId;
    private int currentPage = 1;
    private int totalPage = 0;
    private int totalCodes = 0;
    public CodePresenter(CodeContract.View mView) {
        this.mView = mView;
        mCodeModule = new CodeModule(this);
    }

    @Override
    public <T extends BaseTask> void start(T t) {
        mCodeTask = (CodeTask) t;
        mTaskId = mCodeTask.getTaskId();
        switch (mTaskId) {
            case CodeTask.Id.PULL_REFRESH: {
                getPullRefreshData();
                break;
            }
            case CodeTask.Id.PUSH_LOAD_MORE_REFRESH: {
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
        mCodeModule.start(mCodeTask);
    }

    @Override
    public void getLoadMoreData() {
        //已经加载完全
        if (totalPage > 0 && currentPage >= totalPage) {
            mView.showErrorMsg(CodeTask.Message.MSG_PUSH_LOAD_MORE_REFRESH_FINISH);//已经加载了所有的数据
            mView.setRefreshEnable(true);
            mView.setLoadMOreRefreshing(false);
            return;
        }
        currentPage = currentPage + 1;
        mCodeTask.setPageNum(currentPage);
        mCodeTask.setTotalCodes(totalCodes);
        mCodeModule.start(mCodeTask);
    }
    @Override
    public void onLoadTaskCallback() {
        switch (mTaskId) {
            //刷新操作
            case CodeTask.Id.PULL_REFRESH: {
                mView.setRefreshing(true);
                break;
            }
            //加载分页
            case CodeTask.Id.PUSH_LOAD_MORE_REFRESH: {
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
            case CodeTask.Id.PULL_REFRESH: {
                currentPage = 1;
                mView.showErrorMsg(result);//刷新失败
                mView.setRefreshing(false);
                break;
            }
            //加载分页
            case CodeTask.Id.PUSH_LOAD_MORE_REFRESH: {
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
            case CodeTask.Id.PULL_REFRESH: {
                ArrayList<CodeArticle> list = (ArrayList) result;
                currentPage = 1;
                CodeArticle mCodeCodeArticle = list.get(1);
                totalPage = mCodeCodeArticle.getTotalPage();//总页数
                totalCodes = mCodeCodeArticle.getTotalCodes();//总文章数
                mView.showPullRefreshData(list);
                mView.setRefreshing(false);
                Log.e("onTaskSucessCallback", "mCodeCodeArticle.toString() =" + mCodeCodeArticle.toString());
                break;
            }
            //加载分页
            case CodeTask.Id.PUSH_LOAD_MORE_REFRESH: {
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

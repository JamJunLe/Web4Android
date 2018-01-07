package com.flyman.app.web4android.presenter;

import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IModelCallback;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.ui.view.IMainView;


public class MainPresenter extends BasePresenter<IMainView> implements IModelCallback {

    @Override
    public <E extends BaseTask> void doTask(E task) {
//        mModel.doTask(task, this);
    }

    @Override
    public <T extends BaseTask> void parseFinishedTask(T task) {

    }

    @Override
    public void onPreTask(BaseTask task) {

    }

    @Override
    public void onTaskFail(Object data, BaseTask task) {

    }

    @Override
    public void onTaskCancel(BaseTask task) {

    }

    @Override
    public void onTaskSuccess(Object result, BaseTask task) {

    }

    @Override
    public void onTaskFinish(BaseTask task) {

    }
}

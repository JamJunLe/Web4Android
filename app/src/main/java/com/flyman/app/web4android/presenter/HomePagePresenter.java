package com.flyman.app.web4android.presenter;

import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IModelCallback;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.ui.fragment.HomePageFragment;

public class HomePagePresenter extends BasePresenter implements IModelCallback {
    private HomePageFragment view;

    public HomePagePresenter(HomePageFragment view) {
        this.view = view;
    }



    @Override
    public void doTask(BaseTask task) {

    }

    @Override
    public void parseFinishedTask(BaseTask task) {

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

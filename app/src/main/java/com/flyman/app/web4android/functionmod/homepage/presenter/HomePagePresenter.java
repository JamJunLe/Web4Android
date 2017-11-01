package com.flyman.app.web4android.functionmod.homepage.presenter;

import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.functionmod.homepage.contract.HomePageContract;
import com.flyman.app.web4android.functionmod.homepage.view.HomePageFragment;
import com.flyman.app.web4android.modle.bean.BaseTask;

public class HomePagePresenter implements HomePageContract.Presenter, IModuleCallback {
    private HomePageFragment view;

    public HomePagePresenter(HomePageFragment view) {
        this.view = view;
    }


    @Override
    public void onLoadTaskCallback() {

    }

    @Override
    public <T> void onTaskFailCallback(T data) {

    }

    @Override
    public void onTaskCancellCallback() {

    }

    @Override
    public <T> void onTaskSucessCallback(T result) {

    }

    @Override
    public void onTaskCompleteCallback() {

    }

    @Override
    public <T extends BaseTask> void start(T t) {

    }
}

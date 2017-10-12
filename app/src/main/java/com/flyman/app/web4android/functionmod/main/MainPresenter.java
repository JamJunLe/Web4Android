package com.flyman.app.web4android.functionmod.main;

import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.functionmod.main.contract.MainContract;
import com.flyman.app.web4android.moudle.bean.BaseTask;

public class MainPresenter implements MainContract.Presenter, IModuleCallback {
    private MainContract.View view;
    private MainModule module;
    public static final int TASK_COMMON = 0;
    public MainPresenter(MainContract.View view) {
        this.view = view;
        module = new MainModule(this);
    }

    @Override
    public void onLoadTaskCallback() {

    }

    @Override
    public <T> void onTaskFailCallback(T result) {

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
        module.start(t);
    }
}

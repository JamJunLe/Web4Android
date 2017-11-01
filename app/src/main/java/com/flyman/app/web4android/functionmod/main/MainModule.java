package com.flyman.app.web4android.functionmod.main;

import com.flyman.app.web4android.base.BaseModule;
import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.modle.bean.BaseTask;

public class MainModule extends BaseModule {
    private IModuleCallback moduleClallback;

    public MainModule(IModuleCallback moduleClallback) {
        this.moduleClallback = moduleClallback;
    }


    @Override
    public <T extends BaseTask> void start(T t) {

    }

    @Override
    public void visitNet() {

    }

    @Override
    public void visitLocal() {

    }
}

package com.flyman.app.web4android.base;

import com.flyman.app.web4android.moudle.bean.BaseTask;

public abstract class BaseModule {
    public abstract <T extends BaseTask> void start(T t);

    public abstract void visitNet();

    public abstract void visitLocal();
}

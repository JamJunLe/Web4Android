package com.flyman.app.web4android.base;

import com.flyman.app.web4android.modle.task.BaseTask;

public interface IBaseModel<T extends BaseTask> {
    void request(T task);

    void cancell(T task);

}

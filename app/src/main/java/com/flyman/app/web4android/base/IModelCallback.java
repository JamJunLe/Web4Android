package com.flyman.app.web4android.base;

import com.flyman.app.web4android.modle.task.BaseTask;

public interface IModelCallback<D extends Object,T extends BaseTask> {
    /**
     * 当任务开始之前

     * @param
     * @return nothing
     */
    void onPreTask(T task);

    /**
     * 获取数据不可用的回调
     *
     * @param
     * @return nothing
     */
    void onTaskFail(D data,T task);

    /**
     * 当任务取消时的回调
     *
     * @param
     * @return nothing
     */
    void onTaskCancel(T task);

    /**
     * 当任务成功时
     *
     * @param
     * @return nothing
     */
     void onTaskSuccess(D result,T task);
    /**
     * 当任务结束时
     *
     * @param
     * @return nothing
     */
    void onTaskFinish(T task);

}

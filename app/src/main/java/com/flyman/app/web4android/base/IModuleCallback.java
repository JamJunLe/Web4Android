package com.flyman.app.web4android.base;

public interface IModuleCallback {
    /**
     * 获取数据进行时的回调
     *
     * @param
     * @return nothing
     */
    void onLoadTaskCallback();

    /**
     * 获取数据不可用的回调
     *
     * @param
     * @return nothing
     */
    <T> void onTaskFailCallback(T data);

    /**
     * 当任务取消时的回调
     *
     * @param
     * @return nothing
     */
    void onTaskCancellCallback();

    <T> void onTaskSucessCallback(T result);

    void onTaskCompleteCallback();

}

package com.flyman.app.web4android.modle.bean;

import java.io.Serializable;

/**
 * @author Flyman
 * @ClassName Task
 * @description 任务
 * @date 2017-4-14 19:52
 */
public class Task extends BaseTask implements Serializable {
    protected int taskId;//任务信息

    public Task(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}

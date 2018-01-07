package com.flyman.app.web4android.modle.task;

import java.io.Serializable;

public class BaseTask implements Serializable {
    protected int taskId;

    public BaseTask(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * FileName    : BaseTask
     * Description : 任务状态
     *
     * @author : flyman
     * @version : 1.0
     * @Date :  2017/12/10 23:08
     **/
    public interface Status {
        String CANCEL = "CANCEL";//取消
        String FAIL = "FAIL";//失败
        String SUCCESS = "SUCCESS";//成功
        String NO_NET = "NO_NET";//无网络
        String NO_REASON = "NO_REASON";//无原因错误

    }
}

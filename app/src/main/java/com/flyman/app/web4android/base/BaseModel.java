package com.flyman.app.web4android.base;

import com.flyman.app.util.common.NetUtil;
import com.flyman.app.web4android.modle.task.BaseTask;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class BaseModel<T extends BaseTask> {
    protected ConcurrentMap<BaseTask, IModelCallback> mCallbackConcurrentHashMap = new ConcurrentHashMap<>();
    protected final String TAG = this.getClass().getName();

    public IModelCallback getTaskCallBack(T task) {
        return mCallbackConcurrentHashMap.get(task);
    }

    public void doTask(T task, IModelCallback IModelCallback) {
        mCallbackConcurrentHashMap.put(task, IModelCallback);
        getData(task);
    }

    public void cancelTask(T task) {
        mCallbackConcurrentHashMap.remove(task);
    }

    protected void getData(T task) {
        //网络不可用
        if (!isNetworkAvailable() && mCallbackConcurrentHashMap.containsKey(task)) {
            mCallbackConcurrentHashMap.get(task).onTaskFail(BaseTask.Status.NO_NET, task);
            return;
        }
        //缓存数据不存在
        if (getDataFromCache(task) == null) {
            //从网络取数据
            getDataFromNet(task);
        }
    }

    protected abstract Object getDataFromNet(T task);

    protected abstract Object getDataFromCache(T task);

    protected abstract <L extends List> boolean saveData(L list, T task);

    protected abstract <L extends List> boolean deleteData(L list, T task);

    /**
     * 判断网络是否可用
     *
     * @param
     * @return boolean true网络可用
     */
    protected boolean isNetworkAvailable() {
        return NetUtil.isNetworkAvailable(BaseApplication.getAppContext());
    }

}

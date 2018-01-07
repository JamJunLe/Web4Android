package com.flyman.app.web4android.base;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.modle.task.BaseTask;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * FileName    : BasePresenter
 * Description :
 * 所有Presenter的基类(泛型类)<br/>
 * 通过弱引用和Activity以及Fragment的生命周期预防内存泄露的问题
 *
 * @author : flyman
 * @version : 1.0
 * @Date :  2017/12/7 23:41
 **/
public abstract class BasePresenter<V> {
    protected Reference<V> mReference;
    protected V view;

    /**
     * 移除View 防止内存泄漏
     *
     * @param
     * @rturn nothing
     */
    public void detachView() {
        if (getView() != null) {
            mReference.clear();
            view = null;
            LogUtils.e("detachView()");
        }
    }

    /**
     * 判定是否绑定view
     *
     * @param
     * @return nothing
     */
    public boolean isAttachView() {
        return mReference != null && getView() != null;
    }

    /**
     * 绑定View并使用弱引用管理View<br/>
     * 疑问：如果在onDestroy中解除了对Activity的引用，那么就没有必要再用弱引用了<br/>
     * 解惑：并不是在任何情况下Activity的onDestroy都会被调用（其它原因导致Activity对象还在被引用，就不会回调onDestroy方法），<br/>
     * 一旦这种情况发生，弱引用也能够保证不会造成内存泄露。而通过MVPBaseActivity的封装维护Presenter与View关联关系的代码，使得子类可以避免重复的代码。<br/>
     *
     * @param
     * @param view
     * @return nothing
     */
    public void attachView(V view) {
        this.view = view;
        mReference = new WeakReference(view);
        LogUtils.e("attachView()");
    }

    protected V getView() {
        V view = null;
        if (null != mReference) {
            view = mReference.get();
        }
        return view;
    }

    /**
     * Presenter启动任务
     *
     * @param
     * @return nothing
     */
    public abstract <T extends BaseTask> void doTask(T task);


    /**
     * 解析Task完成的信息(用以区别各种任务)
     *
     * @param
     * @return nothing
     */
    public abstract <T extends BaseTask> void parseFinishedTask(T task);
}

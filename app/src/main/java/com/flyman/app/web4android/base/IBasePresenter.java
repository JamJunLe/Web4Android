package com.flyman.app.web4android.base;

import com.flyman.app.web4android.modle.bean.BaseTask;

/**
 *  @ClassName IBasePresenter
 *  @description  规定Presenter必须要实现start方法。
 *
 *  @author Flyman
 *  @date 2017/4/7 20:28
 */
public interface IBasePresenter {
    /**
     *  规定Presenter必须要实现start方法。
     *  该方法的作用是Presenter开始获取数据并调用View的方法来刷新界面
     *  其调用时机是在Fragment类的onResume方法中/activity类onCreate方法中(戴验证)
     *
     *  @return
     *  @param
     */
    <T extends BaseTask> void start(T t);
}

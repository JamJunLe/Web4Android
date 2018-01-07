package com.flyman.app.web4android.presenter;

import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IListPresenter;
import com.flyman.app.web4android.base.IListView;
import com.flyman.app.web4android.modle.task.BaseTask;

public class FAQPresenter extends BasePresenter<IListView> implements IListPresenter {

    @Override
    public <T extends BaseTask> void doTask(T task) {

    }

    @Override
    public <T extends BaseTask> void parseFinishedTask(T task) {

    }

    @Override
    public void getLoadMoreData() {

    }

    @Override
    public void getPullRefreshData() {

    }
}

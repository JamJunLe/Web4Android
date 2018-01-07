package com.flyman.app.web4android.presenter;

import com.flyman.app.web4android.base.BasePresenter;
import com.flyman.app.web4android.base.IBaseView;
import com.flyman.app.web4android.modle.ArticleDetailsModule;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.ui.activity.ArticleDetails;

public class ArticleDetailsPresenter extends BasePresenter<IBaseView>{
    private ArticleDetails mView;
    private ArticleDetailsModule mMoudle;

    public ArticleDetailsPresenter(ArticleDetails mView) {
        this.mView = mView;
        mMoudle = new ArticleDetailsModule(this);
    }
    @Override
    public <T extends BaseTask> void doTask(T task) {

    }

    @Override
    public <T extends BaseTask> void parseFinishedTask(T task) {

    }
}

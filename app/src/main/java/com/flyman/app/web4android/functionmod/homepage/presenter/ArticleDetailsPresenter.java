package com.flyman.app.web4android.functionmod.homepage.presenter;

import com.flyman.app.web4android.functionmod.homepage.contract.ArticleDetailsContract;
import com.flyman.app.web4android.functionmod.homepage.modle.ArticleDetailsModule;
import com.flyman.app.web4android.functionmod.homepage.view.ArticleDetails;
import com.flyman.app.web4android.moudle.bean.BaseTask;

import java.util.List;

public class ArticleDetailsPresenter implements ArticleDetailsContract.Presenter, ArticleDetailsContract.Moudle {
    private ArticleDetails mView;
    private ArticleDetailsModule mMoudle;

    public ArticleDetailsPresenter(ArticleDetails mView) {
        this.mView = mView;
        mMoudle = new ArticleDetailsModule(this);
    }

    @Override
    public <T extends BaseTask> void start(T t) {
        mMoudle.start(t);
    }

    @Override
    public void onLoadTaskCallback() {

    }

    @Override
    public <T> void onTaskFailCallback(T result) {

    }


    @Override
    public void onTaskCancellCallback() {

    }

    @Override
    public <T> void onTaskSucessCallback(T result) {

    }


    @Override
    public void onTaskCompleteCallback() {

    }
}

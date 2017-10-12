package com.flyman.app.web4android.functionmod.homepage.modle;

import com.flyman.app.web4android.base.BaseModule;
import com.flyman.app.web4android.functionmod.homepage.presenter.ArticleDetailsPresenter;
import com.flyman.app.web4android.moudle.bean.ArticleDetailsTask;
import com.flyman.app.web4android.moudle.bean.BaseTask;


public class ArticleDetailsModule extends BaseModule {
    private ArticleDetailsPresenter mPresenter;
    private String url;

    public ArticleDetailsModule(ArticleDetailsPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public <T extends BaseTask> void start(T t) {
        if (t instanceof ArticleDetailsTask) {
            int taskId = ((ArticleDetailsTask) t).getTaskId();
            url = ((ArticleDetailsTask) t).getUrl();
            visitNet();
        }
    }

    @Override
    public void visitNet() {
        mPresenter.onLoadTaskCallback();

    }

    @Override
    public void visitLocal() {

    }
}

package com.flyman.app.web4android.modle;

import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.presenter.ArticleDetailsPresenter;


public class ArticleDetailsModule extends ProviderModel {
    private ArticleDetailsPresenter mPresenter;
    private String url;

    public ArticleDetailsModule(ArticleDetailsPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void parseHtml(String html, BaseTask task) {

    }
}

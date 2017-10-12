package com.flyman.app.web4android.moudle.bean;

public class ArticleDetailsTask extends Task {
    private String url;

    public ArticleDetailsTask(int taskId, String url) {
        super(taskId);
        this.url = url;
    }

    public ArticleDetailsTask(int taskId) {
        super(taskId);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

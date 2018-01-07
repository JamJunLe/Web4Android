package com.flyman.app.web4android.modle.task;

public class ArticleDetailsNewsTask extends NewsTask {
    private String url;

    public ArticleDetailsNewsTask(int taskId, String url) {
        super(taskId);
        this.url = url;
    }

    public ArticleDetailsNewsTask(int taskId) {
        super(taskId);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

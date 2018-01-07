package com.flyman.app.web4android.modle.bean;

import java.io.Serializable;

public class About implements Serializable {
    private String title;
    private String url;

    public About(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

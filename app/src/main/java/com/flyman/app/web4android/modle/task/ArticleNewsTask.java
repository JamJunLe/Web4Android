package com.flyman.app.web4android.modle.task;

public class ArticleNewsTask extends NewsTask {
    protected int currentPage;
    protected int totalPage;
    protected int totalArticles;


    public ArticleNewsTask(int taskId, int currentPage, int totalArticles) {
        super(taskId);
        this.currentPage = currentPage;
        this.totalArticles = totalArticles;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalArticles() {
        return totalArticles;
    }

    public void setTotalArticles(int totalArticles) {
        this.totalArticles = totalArticles;
    }

    public int getcurrentPage() {
        return currentPage;
    }

    public void setcurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
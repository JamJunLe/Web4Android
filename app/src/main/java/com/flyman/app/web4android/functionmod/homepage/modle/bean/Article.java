package com.flyman.app.web4android.functionmod.homepage.modle.bean;

import java.io.Serializable;

public class Article implements Serializable {
    private String articleId;//文章id
    private String title;//文章标题
    private String img;//文章图片
    private String listUser;//文章所属者
    private String eyeOpen;//文章观看数目
    private String comment;//文章评论数
    private String bookmark;//文章被收藏数
    private String time;//文章发表时间
    private String href;//文章链接
    private String simpleIntro;//文章简介
    private String listUserImg;//文章作者图片
    private int totalPage;//文章总页数
    private int totalArticles;//文章数

    public Article(String articleId, String title, String img, String listUser, String eyeOpen, String comment, String bookmark, String time, String href, String simpleIntro, String listUserImg, int totalPage, int totalArticles) {
        this.articleId = articleId;
        this.title = title;
        this.img = img;
        this.listUser = listUser;
        this.eyeOpen = eyeOpen;
        this.comment = comment;
        this.bookmark = bookmark;
        this.time = time;
        this.href = href;
        this.simpleIntro = simpleIntro;
        this.listUserImg = listUserImg;
        this.totalPage = totalPage;
        this.totalArticles = totalArticles;
    }

    public int getTotalArticles() {
        return totalArticles;
    }

    public void setTotalArticles(int totalArticles) {
        this.totalArticles = totalArticles;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getListUser() {
        return listUser;
    }

    public void setListUser(String listUser) {
        this.listUser = listUser;
    }

    public String getEyeOpen() {
        return eyeOpen;
    }

    public void setEyeOpen(String eyeOpen) {
        this.eyeOpen = eyeOpen;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSimpleIntro() {
        return simpleIntro;
    }

    public void setSimpleIntro(String simpleIntro) {
        this.simpleIntro = simpleIntro;
    }

    public String getListUserImg() {
        return listUserImg;
    }

    public void setListUserImg(String listUserImg) {
        this.listUserImg = listUserImg;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "CodeArticle{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", listUser='" + listUser + '\'' +
                ", eyeOpen='" + eyeOpen + '\'' +
                ", comment='" + comment + '\'' +
                ", bookmark='" + bookmark + '\'' +
                ", time='" + time + '\'' +
                ", href='" + href + '\'' +
                ", simpleIntro='" + simpleIntro + '\'' +
                ", listUserImg='" + listUserImg + '\'' +
                ", totalPage=" + totalPage +
                ", totalArticles=" + totalArticles +
                '}';
    }
}


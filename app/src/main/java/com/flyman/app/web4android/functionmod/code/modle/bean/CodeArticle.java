package com.flyman.app.web4android.functionmod.code.modle.bean;

import java.io.Serializable;

public class CodeArticle implements Serializable {
    private String codeId;//文章id
    private String title;//文章标题
    private String img;//文章图片
    private String eyeOpen;//文章观看数目
    private String time;//文章发表时间
    private String href;//文章链接
    private String simpleIntro;//文章简介
    private int totalPage;//文章总页数
    private int totalCodes;//文章数

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
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

    public String getEyeOpen() {
        return eyeOpen;
    }

    public void setEyeOpen(String eyeOpen) {
        this.eyeOpen = eyeOpen;
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCodes() {
        return totalCodes;
    }

    public void setTotalCodes(int totalCodes) {
        this.totalCodes = totalCodes;
    }

    public CodeArticle(String codeId, String title, String img, String eyeOpen, String time, String href, String simpleIntro, int totalPage, int totalCodes) {
        this.codeId = codeId;
        this.title = title;
        this.img = img;
        this.eyeOpen = eyeOpen;
        this.time = time;
        this.href = href;
        this.simpleIntro = simpleIntro;
        this.totalPage = totalPage;
        this.totalCodes = totalCodes;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "codeId='" + codeId + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", eyeOpen='" + eyeOpen + '\'' +
                ", time='" + time + '\'' +
                ", href='" + href + '\'' +
                ", simpleIntro='" + simpleIntro + '\'' +
                ", totalPage=" + totalPage +
                ", totalCodes=" + totalCodes +
                '}';
    }
}

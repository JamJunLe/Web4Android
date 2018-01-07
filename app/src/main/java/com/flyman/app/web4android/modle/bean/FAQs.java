package com.flyman.app.web4android.modle.bean;

public class FAQs {
    private String askId;//问答id
    private String href;//问答链接
    private String title;//问答标题
    private String memberName;//问答作者名字
    private String memberImg;//问答作者图片
    private String eyeOpen;//问答观看数目
    private String comment;//问答评论数
    private String time;//问答发表时间
    private int totalPage;//问答总页数
    private int totalFaqs;//问答数

    public FAQs(String askId, String href, String title, String memberName, String memberImg, String eyeOpen, String comment, String time, int totalPage, int totalAsks) {
        this.askId = askId;
        this.href = href;
        this.title = title;
        this.memberName = memberName;
        this.memberImg = memberImg;
        this.eyeOpen = eyeOpen;
        this.comment = comment;
        this.time = time;
        this.totalPage = totalPage;
        this.totalFaqs = totalAsks;
    }

    public FAQs(int totalPage, int totalAsks) {
        this.totalPage = totalPage;
        this.totalFaqs = totalAsks;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberImg() {
        return memberImg;
    }

    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalFaqs() {
        return totalFaqs;
    }

    public void setTotalFaqs(int totalFaqs) {
        this.totalFaqs = totalFaqs;
    }

    @Override
    public String toString() {
        return "FAQs{" +
                "askId='" + askId + '\'' +
                ", href='" + href + '\'' +
                ", title='" + title + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberImg='" + memberImg + '\'' +
                ", eyeOpen='" + eyeOpen + '\'' +
                ", comment='" + comment + '\'' +
                ", totalPage=" + totalPage +
                ", time='" + time + '\'' +
                ", totalFaqs=" + totalFaqs +
                '}';
    }
}

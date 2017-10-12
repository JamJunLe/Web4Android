package com.flyman.app.web4android.functionmod.topic.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Flyman
 * @ClassName Topic
 * @description 话题实体对象
 * @date 2017-4-18 17:08
 */
public class Topic implements Serializable {
    private String href;//话题链接
    private String topicId;//话题id
    private String title;//话题标题
    private String memberHref;//该话题所属的用户链接
    private String memberImg;//该话题所属的用户头像
    private String memberName;//该话题所属的用户
    private String eyeOpen;//话题观看数目
    private String time;//话题发表时间
    private int totalPage;//话题总页数
    private int totalTopics;//话题数
    private List<TopicTag> tags;//
    private String totalTags;//

    public Topic(String href, String topicId, String title, String memberHref, String memberImg, String memberName, String eyeOpen, String time, int totalPage, int totalTopics, List<TopicTag> tags, String totalTags) {
        this.href = href;
        this.topicId = topicId;
        this.title = title;
        this.memberHref = memberHref;
        this.memberImg = memberImg;
        this.memberName = memberName;
        this.eyeOpen = eyeOpen;
        this.time = time;
        this.totalPage = totalPage;
        this.totalTopics = totalTopics;
        this.tags = tags;
        this.totalTags = totalTags;
    }

    public String getTotalTags() {
        return totalTags;
    }

    public void setTotalTags(String totalTags) {
        this.totalTags = totalTags;
    }

    public String getMemberHref() {
        return memberHref;
    }

    public void setMemberHref(String memberHref) {
        this.memberHref = memberHref;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMemberImg() {
        return memberImg;
    }

    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalTopics() {
        return totalTopics;
    }

    public void setTotalTopics(int totalTopics) {
        this.totalTopics = totalTopics;
    }


    public List<TopicTag> getTags() {
        return tags;
    }

    public void setTags(List<TopicTag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "href='" + href + '\'' +
                ", topicId='" + topicId + '\'' +
                ", title='" + title + '\'' +
                ", memberHref='" + memberHref + '\'' +
                ", memberImg='" + memberImg + '\'' +
                ", memberName='" + memberName + '\'' +
                ", eyeOpen='" + eyeOpen + '\'' +
                ", time='" + time + '\'' +
                ", totalPage=" + totalPage +
                ", totalTopics=" + totalTopics +
                ", tags=" + tags +
                ", totalTags='" + totalTags + '\'' +
                '}';
    }
}

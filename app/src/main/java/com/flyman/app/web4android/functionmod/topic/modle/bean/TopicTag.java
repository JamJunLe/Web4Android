package com.flyman.app.web4android.functionmod.topic.modle.bean;

public class TopicTag {
    /**
     * @ClassName Tag
     * @description 标签
     * @author Flyman
     * @date 2017-4-18 17:05
     */

    private String herf;
    private String title;


    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TopicTag(String herf, String title) {
        this.herf = herf;
        this.title = title;

    }

    @Override
    public String toString() {
        return "TopicTag{" +
                "herf='" + herf + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

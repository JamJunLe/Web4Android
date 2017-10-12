package com.flyman.app.web4android.functionmod.topic.modle.api;

import com.flyman.app.web4android.io.api.NetUrl;

public interface TopicModuleUrl {
    String URL = NetUrl.BASE_URL + "/plus/freelist.php?lid=12";//"话题"的网址
    String TOPIC_PAGE = "&TotalResult={0}&PageNo={1}";///&TotalResult=577&PageNo=2 拿分页数据
}

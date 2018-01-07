package com.flyman.app.web4android.modle;

import android.util.Log;

import com.flyman.app.web4android.base.IModelCallback;
import com.flyman.app.web4android.io.api.NetUrl;
import com.flyman.app.web4android.io.api.TopicConstant;
import com.flyman.app.web4android.modle.bean.Topic;
import com.flyman.app.web4android.modle.bean.TopicTag;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.modle.task.CodeNewsTask;
import com.flyman.app.web4android.modle.task.HomePageChildNewsTask;
import com.flyman.app.web4android.modle.task.TopicNewsTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class TopicModule extends ProviderModel {
    private CodeNewsTask mCodeTask;
    private String baseUrl = null;
    private List<Topic> mTopicList = null;

    @Override
    public void doTask(BaseTask task, IModelCallback IModelCallback) {
        super.doTask(task, IModelCallback);
        mCodeTask = (CodeNewsTask) task;
        int id = mCodeTask.getTaskId();
        switch (id) {
            case TopicNewsTask.Id.PULL_REFRESH: {
                baseUrl = TopicConstant.URL;
                mCodeTask.setUrl(baseUrl);
                break;
            }
            case TopicNewsTask.Id.PUSH_LOAD_MORE_REFRESH: {
                int pageNum = mCodeTask.getPageNum();
                int totalCodes = mCodeTask.getTotalCodes();
                baseUrl = baseUrl + MessageFormat.format(TopicConstant.TOPIC_PAGE, new Object[]{totalCodes, pageNum});
                mCodeTask.setUrl(baseUrl);
                break;
            }
        }
    }

    @Override
    protected void parseHtml(String html, BaseTask task) {
        try {
            Document mDocument = Jsoup.parse(html);
            String topicInfo = mDocument.select(".paginate-container").select(".pageinfo").text().trim().replace(" ", "").replace("/", "");
            Log.e("topicInfo", "topicInfo =" + topicInfo);
            int index = topicInfo.indexOf("共");//
            int end = topicInfo.indexOf("页");//
            //总页数
            int totalPage = Integer.valueOf(topicInfo.substring(index + 1, end));
            Log.e("topicInfo", "totalPage =" + totalPage);
            int edcTotalArticle = topicInfo.indexOf("条");
            //话题总数
            int totalTopics = Integer.valueOf(topicInfo.substring(end + 1, edcTotalArticle));
            Log.e("topicInfo", "totalArticles =" + totalTopics);
            //话题信息
            String text = mDocument.select(".row.bgf").select(".feed-section").html();//获取话题列表
            Log.e("topicInfo", "text =" + text);
            Elements topics = mDocument.select(".row.bgf").select(".feed-section");//获取话题列表
            Log.e("topicInfo", "list.size() =" + topics.size());
            if (topics != null && topics.size() != 0) {
                mTopicList = new ArrayList<>();
                for (int i = 0; i < topics.size(); i++) {
                    String href = NetUrl.BASE_URL + topics.get(i).select(".summary").select("a").attr("href");//话题链接
                    String topicId = href;
                    String memberName = topics.get(i).select(".sub-info").select("a").text();//用户名
                    String memberHref = topics.get(i).select(".head").select("a").attr("href");
                    //该话题所属的用户链接
                    String memberImg = NetUrl.BASE_URL + topics.get(i).select(".head").select("img").attr("src");//用户头像
                    String title = topics.get(i).select(".summary").select("h3").select("a").text().trim();
                    String sub_info = topics.get(i).select(".sub-info").select("span").text().replace(" ", "").trim();
                    int indexOf = sub_info.indexOf(".");
                    int lastIndex = sub_info.lastIndexOf(".");
                    String eyeOpen = sub_info.substring(indexOf + 1, lastIndex);
                    String time = sub_info.substring(lastIndex + 1);

                    Elements tags = topics.get(i).select(".mt10").select("a");
                    List<TopicTag> tagList = new ArrayList<TopicTag>();
                    StringBuilder totalTags = new StringBuilder("");
                    if (tags.size() > 0) {
                        for (int n = 0; n < tags.size(); n++) {
                            String tagHref = tags.get(n).attr("href");
                            String tagTitle = tags.get(n).text();
                            tagList.add(new TopicTag(tagHref, tagTitle));
                            totalTags.append(tagTitle + "/");
                            Log.e("totalTags", "tagHref =" + tagHref);
                            Log.e("totalTags", "tagTitle =" + tagTitle);
                        }
                        totalTags.deleteCharAt(totalTags.lastIndexOf("/"));
                    }
                    Log.e("topicInfo", "href =" + href);
                    Log.e("topicInfo", "topicId =" + topicId);
                    Log.e("topicInfo", "memberName =" + memberName);
                    Log.e("topicInfo", "memberHref =" + memberHref);
                    Log.e("topicInfo", "memberImg =" + memberImg);
                    Log.e("topicInfo", "title =" + title);
                    Log.e("topicInfo", "sub_info =" + sub_info);
                    Log.e("topicInfo", "eyeOpen =" + eyeOpen);
                    Log.e("topicInfo", "time =" + time);
                    Log.e("totalTags", "totalTags =" + totalTags);

                    Topic mtTopic = new Topic(href, topicId, title, memberHref, memberImg, memberName, eyeOpen, time, totalPage, totalTopics, tagList, totalTags.toString());
                    mTopicList.add(mtTopic);
                }
                getTaskCallBack(task).onTaskSuccess(mTopicList,task);
            }

        } catch (Exception e) {
            getTaskCallBack(task).onTaskFail(HomePageChildNewsTask.Message.MSG_HTMML_PARSE_ERROR,task);

        }
    }

}

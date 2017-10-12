package com.flyman.app.web4android.functionmod.topic.modle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.base.BaseModule;
import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.functionmod.topic.modle.api.TopicModuleUrl;
import com.flyman.app.web4android.functionmod.topic.modle.bean.Topic;
import com.flyman.app.web4android.functionmod.topic.modle.bean.TopicTag;
import com.flyman.app.web4android.io.api.NetUrl;
import com.flyman.app.web4android.io.net.CustomRequest;
import com.flyman.app.web4android.io.net.HttpHelper;
import com.flyman.app.web4android.moudle.bean.BaseTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class TopicModule extends BaseModule {
    private IModuleCallback callBack;
    private TopicTask mTopicTask;
    private CustomRequest mCustomRequest;
    private String baseUrl = TopicModuleUrl.URL;
    private int taskId;
    private List<Topic> mTopicList = null;
    private final int HANDLERMSG_SUCESS = 1;
    private final int HANDLERMSG_FAIL = 2;

    public TopicModule(IModuleCallback callBack) {
        this.callBack = callBack;
    }

    @Override
    public <T extends BaseTask> void start(T t) {
        mTopicTask = (TopicTask) t;
        int id = mTopicTask.getTaskId();
        switch (id) {
            case TopicTask.Id.PULL_REFRESH: {
                taskId = mTopicTask.getTaskId();
                break;
            }
            case TopicTask.Id.PUSH_LOAD_MORE_REFRESH: {
                int pageNum = mTopicTask.getPageNum();
                int totalCodes = mTopicTask.getTotalCodes();
                baseUrl = baseUrl + MessageFormat.format(TopicModuleUrl.TOPIC_PAGE, new Object[]{totalCodes, pageNum});

                break;
            }
            default: {

            }
        }
        Log.e("topicInfo", "baseUrl =" + baseUrl);
        visitNet();
    }

    @Override
    public void visitNet() {
        callBack.onLoadTaskCallback();//加载中
        mCustomRequest = HttpHelper.getRequset(baseUrl, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                //解析网页
                parseHtml(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //网页加载错误
                callBack.onTaskFailCallback(TopicTask.Message.MSG_NET_ERROR);
            }
        });
        HttpHelper.getHttpClient().addRequest(mCustomRequest, taskId + "");

    }

    private void parseHtml(final String html) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                        mTopicList = new ArrayList<Topic>();
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
                        Message mMessage = new Message();
                        mMessage.what = HANDLERMSG_SUCESS;
                        mHandler.sendMessage(mMessage);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("topicInfo", "" + e.toString());
                    Message mMessage = new Message();
                    mMessage.what = HANDLERMSG_FAIL;
                    mHandler.sendMessage(mMessage);

                }
            }
        }).start();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            if (what == HANDLERMSG_SUCESS)//加载并且解析网页完成
            {
                callBack.onTaskSucessCallback(mTopicList);
            } else {
                callBack.onTaskFailCallback(TopicTask.Message.MSG_HTMML_PARSE_ERROR);
            }
            callBack.onTaskCompleteCallback();
            return false;
        }
    });

    @Override
    public void visitLocal() {

    }
}

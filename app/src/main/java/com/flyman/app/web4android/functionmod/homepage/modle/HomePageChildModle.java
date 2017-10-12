package com.flyman.app.web4android.functionmod.homepage.modle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.base.BaseModule;
import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.functionmod.code.modle.CodeTask;
import com.flyman.app.web4android.functionmod.code.modle.api.CodeModuleUrl;
import com.flyman.app.web4android.functionmod.homepage.modle.api.HomePageChildModleUrl;
import com.flyman.app.web4android.functionmod.homepage.modle.bean.Article;
import com.flyman.app.web4android.io.net.CustomRequest;
import com.flyman.app.web4android.io.net.HttpHelper;
import com.flyman.app.web4android.moudle.bean.BaseTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask.Type.TASK_ANDROID_DEV;
import static com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask.Type.TASK_CODE_DESIGN;
import static com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask.Type.TASK_DATABASE_DEV;
import static com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask.Type.TASK_DEV_LOG;
import static com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask.Type.TASK_HOME_COMPREHENSIVE_INFO;
import static com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask.Type.TASK_IOS_DEV;
import static com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask.Type.TASK_WEB_DEV;


public class HomePageChildModle extends BaseModule {
    private IModuleCallback callBack;
    private CustomRequest qequest;
    private HomePageChildTask mTask;
    private String url = "";
    private int taskId;
    private int args;
    private final int HANDLERMSG_SUCESS = 1;
    private final int HANDLERMSG_FAIL = 2;
    private List<Article> mList = null;

    public HomePageChildModle(IModuleCallback callBack, int args) {
        this.callBack = callBack;
        this.args = args;
    }

    @Override
    public <T extends BaseTask> void start(T t) {
        mTask = (HomePageChildTask) t;
        taskId = mTask.getTaskId();
        //判断是来自于那个fragment发出的请求
        switch (args) {
            case TASK_HOME_COMPREHENSIVE_INFO: {
                url = HomePageChildModleUrl.HOME_COMPREHENSIVE_INFO;
                break;
            }
            case TASK_CODE_DESIGN: {
                url = HomePageChildModleUrl.CODE_DESIGN;
                break;
            }
            case TASK_ANDROID_DEV: {
                url = HomePageChildModleUrl.ANDROID_DEV;
                break;
            }

            case TASK_WEB_DEV: {
                url = HomePageChildModleUrl.WEB_DEV;
                break;
            }
            case TASK_IOS_DEV: {
                url = HomePageChildModleUrl.IOS_DEV;
                break;
            }
            case TASK_DATABASE_DEV: {
                url = HomePageChildModleUrl.DATABASE_DEV;
                break;
            }
            case TASK_DEV_LOG: {
                url = HomePageChildModleUrl.DEV_LOG;
                break;
            }
            default: {

            }
        }

        //判断任务id,构建分页数据
        switch (taskId) {
            case CodeTask.Id.PULL_REFRESH: {
                break;
            }
            case CodeTask.Id.PUSH_LOAD_MORE_REFRESH: {
                int pageNum = mTask.getPageNum();
                int totalCodes = mTask.getTotalCodes();
                url = url + MessageFormat.format(CodeModuleUrl.CODE_PAGE, new Object[]{totalCodes, pageNum});
                break;
            }
            default: {

            }
        }

        Log.e("jsoupParse", "baseUrl =" + url);
        visitNet();//开始访问网络
    }

    @Override
    public void visitNet() {
        callBack.onLoadTaskCallback();//加载中
        qequest = new CustomRequest.RequestBuilder().url(url).errorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //网页加载错误
                callBack.onTaskFailCallback(HomePageChildTask.Message.MSG_NET_ERROR);
            }
        }).listener(new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                //解析网页
                jsoupParse(o.toString());
            }
        }).build();
        HttpHelper.getHttpClient().addRequest(qequest, taskId + "");
    }


    public void jsoupParse(final String html) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document mDocument = Jsoup.parse(html);
                    String pageInfo = mDocument.select(".paginate-container").select(".pageinfo").text().trim().replace(" ", "").replace("/", "");
                    LogUtils.e("pageInfo", "pageInfo =" + pageInfo);
                    int index = pageInfo.indexOf("共");//
                    int end = pageInfo.indexOf("页");//
                    //总页数
                    int totalPage = Integer.valueOf(pageInfo.substring(index + 1, end));
                    LogUtils.e("pageInfo", "totalPage =" + totalPage);
                    int edcTotalArticle = pageInfo.indexOf("条");
                    //文章总数
                    int totalArticles = Integer.valueOf(pageInfo.substring(end + 1, edcTotalArticle));
                    LogUtils.e("pageInfo", "totalArticles =" + totalArticles);
                    Elements articles = mDocument.select(".archive-list").select(".archive-item");//获取文章列表
                    if (articles != null && articles.size() != 0) {
                        mList = new ArrayList();
                        LogUtils.e("jsoupParse", "list.size() =" + articles.size());
                        for (int i = 0; i < articles.size(); i++) {
                            String href = articles.get(i).select(".archive-detail").select("h3").select("a").attr("href");
                            String articleId = href;
                            String title = articles.get(i).select(".archive-detail").select("h3").select("a").attr("title");
                            String img = articles.get(i).select(".covercon").select("img").attr("src");
                            String simpleIntro = articles.get(i).select(".archive-detail").select("p").text();
                            String listUserImg = articles.get(i).select(".archive-detail").select(".list-user").select("img").attr("src");
                            String listUser = articles.get(i).select(".archive-detail").select(".list-user").select("strong").text();
                            String eyeOpen = articles.get(i).select(".archive-detail").select(".list-msg").select(".glyphicon-class").get(0).text();
                            String comment = articles.get(i).select(".archive-detail").select(".list-msg").select(".glyphicon-class").get(1).text();
                            String bookmark = articles.get(i).select(".archive-detail").select(".list-msg").select(".glyphicon-class").get(2).text();
                            String time = articles.get(i).select(".archive-detail").select(".archive-data").select(".glyphicon-class").text();
                            Article mArticle = new Article(articleId, title, img, listUser, eyeOpen, comment, bookmark, time, href, simpleIntro, listUserImg, totalPage, totalArticles);
                            mList.add(mArticle);
                        }
                        Message mMessage = new Message();
                        mMessage.what = HANDLERMSG_SUCESS;
                        mHandler.sendMessage(mMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("jsoupParse", "" + e.toString());
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
                callBack.onTaskSucessCallback(mList);
            } else {
                callBack.onTaskFailCallback(HomePageChildTask.Message.MSG_HTMML_PARSE_ERROR);
            }
            callBack.onTaskCompleteCallback();
            return false;
        }
    });

    @Override
    public void visitLocal() {

    }
}

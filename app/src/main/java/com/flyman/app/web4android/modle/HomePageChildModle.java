package com.flyman.app.web4android.modle;

import android.os.Message;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.base.IModelCallback;
import com.flyman.app.web4android.io.net.CustomRequest;
import com.flyman.app.web4android.modle.bean.Article;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.modle.task.NewsTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class HomePageChildModle extends ProviderModel {
    private IModelCallback callBack;
    private CustomRequest qequest;
    private NewsTask mTask;
    private String url = "";
    private int taskId;
    private int args;
    private final int HANDLERMSG_SUCESS = 1;
    private final int HANDLERMSG_FAIL = 2;
    private List<Article> mList = null;

    public HomePageChildModle(IModelCallback callBack, int args) {
        this.callBack = callBack;
        this.args = args;
    }

//    @Override
//    public <T extends BaseTask> void start(T t) {
//        mTask = (NewsTask) t;
//        taskId = mTask.getTaskId();
//        //判断是来自于那个fragment发出的请求
//        switch (args) {
//            case TASK_HOME_COMPREHENSIVE_INFO: {
//                url = HomePageChildConstant.HOME_COMPREHENSIVE_INFO;
//                break;
//            }
//            case TASK_CODE_DESIGN: {
//                url = HomePageChildConstant.CODE_DESIGN;
//                break;
//            }
//            case TASK_ANDROID_DEV: {
//                url = HomePageChildConstant.ANDROID_DEV;
//                break;
//            }
//
//            case TASK_WEB_DEV: {
//                url = HomePageChildConstant.WEB_DEV;
//                break;
//            }
//            case TASK_IOS_DEV: {
//                url = HomePageChildConstant.IOS_DEV;
//                break;
//            }
//            case TASK_DATABASE_DEV: {
//                url = HomePageChildConstant.DATABASE_DEV;
//                break;
//            }
//            case TASK_DEV_LOG: {
//                url = HomePageChildConstant.DEV_LOG;
//                break;
//            }
//            default: {
//
//            }
//        }
//
//        //判断任务id,构建分页数据
//        switch (taskId) {
//            case CodeNewsTask.Type.PULL_REFRESH: {
//                break;
//            }
//            case CodeNewsTask.Type.PUSH_LOAD_MORE_REFRESH: {
//                int pageNum = mTask.getPageIndex();
//                int totalCodes = mTask.getNewsAmount();
//                url = url + MessageFormat.format(CodeConstant.CODE_PAGE, new Object[]{totalCodes, pageNum});
//                break;
//            }
//            default: {
//
//            }
//        }
//
//        Log.e("jsoupParse", "baseUrl =" + url);
////        visitNet();//开始访问网络
//    }

//    @Override
//    public void visitNet() {
//        callBack.onPreTask();//加载中
//        qequest = new CustomRequest.RequestBuilder().url(url).errorListener(new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                //网页加载错误
//                callBack.onTaskFail(NewsTask.Message.MSG_NET_ERROR);
//            }
//        }).listener(new Response.Listener() {
//            @Override
//            public void onResponse(Object o) {
//                //解析网页
//                jsoupParse(o.toString());
//            }
//        }).build();
//        HttpHelper.getHttpClient().addRequest(qequest, taskId + "");
//    }


    @Override
    protected void parseHtml(final String html, BaseTask task) {
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
//                        mHandler.sendMessage(mMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("jsoupParse", "" + e.toString());
                    Message mMessage = new Message();
                    mMessage.what = HANDLERMSG_FAIL;
//                    mHandler.sendMessage(mMessage);
                }

            }
        }).start();


    }

//    private Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            int what = msg.what;
//            if (what == HANDLERMSG_SUCESS)//加载并且解析网页完成
//            {
//                callBack.onTaskSuccess(mList);
//            } else {
//                callBack.onTaskFail(NewsTask.Message.MSG_HTMML_PARSE_ERROR);
//            }
//            callBack.onTaskFinish();
//            return false;
//        }
//    });




    }

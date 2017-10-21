package com.flyman.app.web4android.functionmod.code.modle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.base.BaseModule;
import com.flyman.app.web4android.base.IModuleCallback;
import com.flyman.app.web4android.functionmod.code.modle.api.CodeModuleUrl;
import com.flyman.app.web4android.functionmod.code.modle.bean.CodeArticle;
import com.flyman.app.web4android.io.net.CustomRequest;
import com.flyman.app.web4android.io.net.HttpHelper;
import com.flyman.app.web4android.moudle.bean.BaseTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class CodeModule extends BaseModule {
    private IModuleCallback callBack;
    private CodeTask mCodeTask;
    private CustomRequest mCustomRequest;
    private String baseUrl = CodeModuleUrl.URL;
    private int taskId;
    private List<CodeArticle> codeList = null;
    private final int HANDLERMSG_SUCESS = 1;
    private final int HANDLERMSG_FAIL = 2;

    public CodeModule(IModuleCallback callBack) {
        this.callBack = callBack;
    }

    @Override
    public <T extends BaseTask> void start(T t) {
        mCodeTask = (CodeTask) t;
        int id = mCodeTask.getTaskId();
        switch (id) {
            case CodeTask.Id.PULL_REFRESH: {
                taskId = mCodeTask.getTaskId();
                break;
            }
            case CodeTask.Id.PUSH_LOAD_MORE_REFRESH: {
                int pageNum = mCodeTask.getPageNum();
                int totalCodes = mCodeTask.getTotalCodes();
                baseUrl = baseUrl + MessageFormat.format(CodeModuleUrl.CODE_PAGE, new Object[]{totalCodes, pageNum});
                break;
            }
            default: {

            }
        }
        Log.e("jsoupParse", "baseUrl =" + baseUrl);
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
                callBack.onTaskFailCallback(CodeTask.Message.MSG_NET_ERROR);
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
                    String pageInfo = mDocument.select(".paginate-container").select(".pageinfo").text().trim().replace(" ", "").replace("/", "");
                    Log.e("pageInfo", "pageInfo =" + pageInfo);
                    int index = pageInfo.indexOf("共");//
                    int end = pageInfo.indexOf("页");//
                    //总页数
                    int totalPage = Integer.valueOf(pageInfo.substring(index + 1, end));
                    Log.e("pageInfo", "totalPage =" + totalPage);
                    int edcTotalArticle = pageInfo.indexOf("条");
                    //文章总数
                    int totalCodes = Integer.valueOf(pageInfo.substring(end + 1, edcTotalArticle));
                    Log.e("pageInfo", "totalArticles =" + totalCodes);
                    String text = mDocument.getElementById("codelist").html();//获取文章列表
                    Log.e("jsoupParse", "text =" + text);
                    Elements articles = mDocument.getElementById("codelist").select(".codeli");//获取文章列表
                    Log.e("jsoupParse", "list.size() =" + articles.size());
                    if (articles != null && articles.size() != 0) {
                        codeList = new ArrayList<CodeArticle>();
                        for (int i = 0; i < articles.size(); i++) {
                            String href = articles.get(i).select(".codeli-photo").select("a").attr("href");
                            String codeId = href;
                            String img = articles.get(i).select(".codeli-photo").select("img").attr("src");
                            String title = articles.get(i).select(".codeli-info").select(".codeli-name").select("a").text().trim();
                            String description = articles.get(i).select(".codeli-info").select(".codeli-description").text().trim();
                            String otherInfo = articles.get(i).select(".otherinfo").text().replace(" ", "").trim();
                            int lastIndex = otherInfo.lastIndexOf("看");
                            int indexOfTime = otherInfo.indexOf("-");
                            String eyeOpen = otherInfo.substring(0, lastIndex + 1);
                            String time = otherInfo.substring(indexOfTime-4);
                            Log.e("jsoupParse", "href =" + href);
                            Log.e("jsoupParse", "img2 =" + img);
                            Log.e("jsoupParse", "description =" + description);
                            Log.e("jsoupParse", "otherinfo =" + otherInfo);
                            Log.e("jsoupParse", "eyeOpen =" + eyeOpen);
                            Log.e("jsoupParse", "time =" + time);
                            CodeArticle mCodeCodeArticle = new CodeArticle(codeId, title, img, eyeOpen, time, href, description, totalPage, totalCodes);
                            codeList.add(mCodeCodeArticle);
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
                callBack.onTaskSucessCallback(codeList);
            } else {
                callBack.onTaskFailCallback(CodeTask.Message.MSG_HTMML_PARSE_ERROR);
            }
            callBack.onTaskCompleteCallback();
            return false;
        }
    });

    @Override
    public void visitLocal() {

    }
}

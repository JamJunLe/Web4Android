package com.flyman.app.web4android.modle;

import android.os.Message;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.web4android.base.IModelCallback;
import com.flyman.app.web4android.io.api.NetUrl;
import com.flyman.app.web4android.io.net.CustomRequest;
import com.flyman.app.web4android.modle.bean.FAQs;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.modle.task.NewsTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.flyman.app.web4android.io.api.FAQsConstant.FAQS_ITEM;


public class FAQsItemModle extends ProviderModel {
    private IModelCallback callBack;
    private CustomRequest qequest;
    private NewsTask mTask;
    private String url = "";
    private int taskId;
    private int args;
    private final int HANDLERMSG_SUCESS = 1;
    private final int HANDLERMSG_FAIL = 2;
    private List<FAQs> mList = null;

    public FAQsItemModle(IModelCallback callBack, int args) {
        this.callBack = callBack;
        this.args = args;
    }

//    mTask = (FAQsItemNewsTask) t;
//    taskId = mTask.getTaskId();
//    //判断是来自于那个fragment发出的请求
//    switch (args) {
//        case ALL_QUESTION: {
//            url = FAQsConstant.URL_ALL_FAQS;
//            break;
//        }
//        case ALREADY_SOLOVED: {
//            url = FAQsConstant.URL_ALREAD_SOLVED;
//            break;
//        }
//        case TO_BE_SOLOVE: {
//            url = FAQsConstant.URL_TO_BE_SOLVED;
//            break;
//        }
//
//        default: {
//
//        }
//    }
//
//    //判断任务id,构建分页数据
//    switch (taskId) {
//        case CodeNewsTask.Type.PULL_REFRESH: {
//            break;
//        }
//        case CodeNewsTask.Type.PUSH_LOAD_MORE_REFRESH: {
//            int pageNum = mTask.getPageIndex();
//            int totalCodes = mTask.getNewsAmount();
//            url = url + MessageFormat.format(FAQsConstant.FAQs_PAGE, new Object[]{totalCodes, pageNum});
//            break;
//        }
//        default: {
//
//        }
//    }

    @Override
    protected void parseHtml(String html, BaseTask task) {
        try {
            int totalPage = 0;
            int totalFaqs = 0;
            boolean hasPage = true;
            String pageInfo;
            Document mDocument = Jsoup.parse(html);
            //解析分页数据
            Elements pageInfoElements = mDocument.select(".pagination").select("li");
            if (pageInfoElements.size() > 1) {
                pageInfo = mDocument.select(".pagination").select("li").get(2).select("a").attr("href");
                int index = pageInfo.lastIndexOf("&");
                //文章总数
                totalFaqs = Integer.valueOf(pageInfo.substring(index - 2, index));
                hasPage = true;
            } else {
                pageInfo = pageInfoElements.text().trim().replace(" ", "").replace("/", "");
                int index = pageInfo.indexOf("共");//
                int end = pageInfo.indexOf("页");//
                //总页数
                totalPage = Integer.valueOf(pageInfo.substring(index + 1, end));

                int indexOfTotalArticle = pageInfo.indexOf("条");
                //文章总数
                totalFaqs = Integer.valueOf(pageInfo.substring(end + 1, indexOfTotalArticle));
                hasPage = false;

            }
            //获取文章列表
            Elements faqsEmElements = mDocument.select(".question-section");
            mList = new ArrayList();
            if (faqsEmElements != null && faqsEmElements.size() != 0) {
                LogUtils.e("li", "list.size() =" + faqsEmElements.size());
                for (int i = 0; i < faqsEmElements.size(); i++) {
                    String href = FAQS_ITEM + faqsEmElements.get(i).select(".summary").select("h3").select("a").attr("href");
                    String askId = href;
                    String title = faqsEmElements.get(i).select(".summary").select("h3").select("a").text();
                    Elements subInfoElements = faqsEmElements.get(i).select(".sub-info");
                    String memberName = subInfoElements.select("a").text();
                    String memberImg = NetUrl.BASE_URL + faqsEmElements.get(i).select(".head").select("a").select("img").attr("src");
                    String subInfo = subInfoElements.select("span").text().replace(" ", "").trim();
                    LogUtils.e("li", "subInfo =" + subInfo);
                    String subInfos = subInfo.substring(subInfo.indexOf(".") + 1);
                    int indexOf = subInfos.indexOf(".");
                    int lastIndexOf = subInfos.lastIndexOf(".");
                    String eyeOpen = subInfos.substring(0, indexOf);
                    String comment = subInfos.substring(indexOf + 1, lastIndexOf);
                    String time = subInfos.substring(lastIndexOf + 1);
                    //
                    if (hasPage == true) {
                        totalPage = totalFaqs / faqsEmElements.size();//
                        if (totalFaqs % faqsEmElements.size() > 0) {
                            totalPage = totalPage + 1;
                        }
                    }
                    FAQs mFaQs = new FAQs(askId, href, title, memberName, memberImg, eyeOpen, comment, time, totalPage, totalFaqs);
                    mList.add(mFaQs);
                }

            }
            else {
                FAQs mFaQs = new FAQs(totalPage, totalFaqs);
                mList.add(mFaQs);
            }
            Message mMessage = new Message();
            mMessage.what = HANDLERMSG_SUCESS;
//            mHandler.sendMessage(mMessage);
            LogUtils.e("li", "pageInfo =" + pageInfo);
            LogUtils.e("li", "totalPage =" + totalPage);
            LogUtils.e("li", "totalArticles =" + totalFaqs);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("jsoupParse", "" + e.toString());
            Message mMessage = new Message();
            mMessage.what = HANDLERMSG_FAIL;
//            mHandler.sendMessage(mMessage);
        }

    }
}

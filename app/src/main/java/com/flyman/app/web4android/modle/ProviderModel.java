package com.flyman.app.web4android.modle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flyman.app.web4android.base.BaseModel;
import com.flyman.app.web4android.io.net.CustomRequest;
import com.flyman.app.web4android.io.net.HttpHelper;
import com.flyman.app.web4android.modle.task.BaseTask;
import com.flyman.app.web4android.modle.task.NewsTask;

import java.util.List;


public abstract class ProviderModel extends BaseModel {
    protected CustomRequest mCustomRequest;

    @Override
    protected Object getDataFromNet(final BaseTask task) {
        if (task instanceof NewsTask) {
            final NewsTask mCodeTask = (NewsTask) task;
            getTaskCallBack(mCodeTask).onPreTask(mCodeTask);//加载中
            mCustomRequest = HttpHelper.getRequset(mCodeTask.getUrl(), new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    //解析网页
                    parseHtml(response.toString(), task);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //网页加载错误
                    getTaskCallBack(task).onTaskFail(NewsTask.Message.MSG_NET_ERROR, task);
                }
            });
            HttpHelper.getHttpClient().addRequest(mCustomRequest, task);
        }

        return null;
    }


    protected abstract void parseHtml(final String html, BaseTask task);

    @Override
    protected Object getDataFromCache(BaseTask task) {
        return null;
    }

    @Override
    protected boolean deleteData(List list, BaseTask task) {
        return false;
    }

    @Override
    protected boolean saveData(List list, BaseTask task) {
        return false;
    }

}

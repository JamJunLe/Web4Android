package com.flyman.app.web4android.io.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.flyman.app.web4android.base.BaseApplication;
/**
 *  @ClassName HttpClientRequest
 *  @description 以volley为基础的网络访问入口
 *
 *  @author Flyman
 *  @date 2017/4/6 9:43
 */
public class HttpClientRequest {
    private static Context mContext;
    private static HttpClientRequest mHttpClientRequest;
    private RequestQueue mRequestQueue;
    public HttpClientRequest(Context mContext) {
        this.mContext = mContext;
    }
    /**
     *活得请求队列
     *
     *  @return HttpClientRequest
     *
     */
    public static synchronized HttpClientRequest newInstance() {
        if (mHttpClientRequest == null) {
            mHttpClientRequest = new HttpClientRequest(BaseApplication.getAppContext());
        }
        return mHttpClientRequest;
    }

    /**
     *获得得请求队列
     *
     *  @return RequestQueue
     *
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    /**
     *添加访问请求到队列
     *
     *  @return nothing
     *  @param request Request<T>
     *  @param tag String
     */
    public <T> void addRequest(Request<T> request,String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    /**
     * 添加访问请求到队列
     *
     * @param request Request<T>
     * @param tag     String
     * @return nothing
     */
    public <T> void addRequest(Request<T> request, int tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    /**
     *取消请求
     *
     *  @return nothing
     *  @param tag String
     */
    public void cancelRequest(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}

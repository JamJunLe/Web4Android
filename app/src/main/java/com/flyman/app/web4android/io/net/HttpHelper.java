package com.flyman.app.web4android.io.net;

import com.android.volley.Response;

public class HttpHelper {
    public static HttpClientRequest getHttpClient(){
        return HttpClientRequest.newInstance();
    }

    public static CustomRequest getRequset(String url, Response.Listener listener, Response.ErrorListener errorListener){
        return new CustomRequest.RequestBuilder().url(url).listener(listener).errorListener(errorListener).build();
    }

}

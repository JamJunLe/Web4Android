package com.flyman.app.web4android.io.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
/**
 *  @ClassName CustomRequest
 *  @description 自定义的访问请求
 *
 *  @author Flyman
 *  @date 2017/4/6 9:43
 */
public class CustomRequest<T> extends Request<T> {
    private Response.Listener<T> listener;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Class clazz;

    /**
     * @param url           String
     * @param listener      Response.Listener<T>
     * @param errorListener lsListener,Response.ErrorListener
     */
    public CustomRequest(String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    /**
     * @param method        int
     * @param url           String
     * @param lsListener    Response.Listener<T>
     * @param errorListener lsListener,Response.ErrorListener
     */
    public CustomRequest(int method, String url, Response.Listener<T> lsListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = lsListener;
    }

    public CustomRequest(RequestBuilder mRequestBuilder) {
        super(mRequestBuilder.method, mRequestBuilder.url, mRequestBuilder.errorListener);
        this.listener = mRequestBuilder.listener;
        this.headers = mRequestBuilder.headers;
        this.params = mRequestBuilder.params;
        this.clazz = mRequestBuilder.clazz;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T t) {
        this.listener.onResponse(t);
    }

    /**
     * 这是自定义Request最重要的地方，决定了请求的类型。可以是StingRequest,GsonRequest
     *
     * @param response NetworkResponse
     * @return Response<T>
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        //待实现
//        try {
//            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            if (clazz == null) {
//                return (Response<T>) Response.success(parsed,HttpHeaderParser.parseCacheHeaders(response));
//            } else {
//                return Response.success(gson.fromJson(parsed, clazz), HttpHeaderParser.parseCacheHeaders(response));
//            }
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
//        } catch (JsonSyntaxException e) {
//            return Response.error(new ParseError(e));
//        } catch (Exception e) {
//            return Response.error(new ParseError(e));
//        }
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response<T>) Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
    /**
     * @author Flyman
     * @ClassName RequestBuilder
     * @description CustomRequest 静态内部类，用以构造CustomRequest
     * @date 2017/4/6 6:01
     */
    public static class RequestBuilder {
        private int method = Method.GET;
        private String url;
        private Response.Listener listener;
        private Response.ErrorListener errorListener;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Class clazz;

        /**
         * @param headers Map
         * @return RequestBuilder
         */
        public RequestBuilder headeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * @param clazz Class
         * @return RequestBuilder
         */
        public RequestBuilder clazz(Class clazz) {
            this.clazz = clazz;
            return this;
        }

        /**
         * 添加 Map 型的参数
         *
         * @param params Map
         * @return RequestBuilder
         */
        public RequestBuilder params(Map<String, String> params) {
            this.params = params;
            post();
            return this;
        }

        /**
         * 添加额外的参数
         *
         * @param key   String
         * @param value String
         * @return RequestBuilder
         */
        public RequestBuilder addParm(String key, String value) {
            if (params == null) {
                params = new HashMap<String, String>();
            }
            post();
            params.put(key, value);
            return this;
        }

        /**
         * 默认的访问方法为为get,post()方法提供post访问
         *
         * @param
         * @return RequestBuilder
         */
        public RequestBuilder post() {
            this.method = Method.POST;
            return this;
        }

        /**
         * @param url String
         * @return RequestBuilder
         */
        public RequestBuilder url(String url) {
            this.url = url.trim();
            return this;
        }

        /**
         * @param errorListener Response.ErrorListener
         * @return RequestBuilder
         */

        public RequestBuilder errorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        /**
         * @param listener Response.ErrorListener
         * @return RequestBuilder
         */
        public RequestBuilder listener(Response.Listener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * @param
         * @return CustomRequest
         */
        public CustomRequest build() {
            if (listener == null || errorListener == null) {
                throw new IllegalStateException("illegal CustomRequest build Exception");
            }
            return new CustomRequest(this);
        }
    }

}

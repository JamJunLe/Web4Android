package com.flyman.app.web4android.io.net;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

public interface IWebClientListener {
    void onProgressChanged(WebView view, int newProgress);

    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);
}

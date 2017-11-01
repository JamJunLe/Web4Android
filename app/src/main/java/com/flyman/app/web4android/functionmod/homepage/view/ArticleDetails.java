package com.flyman.app.web4android.functionmod.homepage.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.flyman.app.util.log.LogUtils;
import com.flyman.app.util.string.ChenkNullUtil;
import com.flyman.app.web4android.R;
import com.flyman.app.web4android.base.BaseActivity;
import com.flyman.app.web4android.functionmod.homepage.contract.ArticleDetailsContract;
import com.flyman.app.web4android.functionmod.homepage.modle.HomePageChildTask;
import com.flyman.app.web4android.functionmod.homepage.presenter.ArticleDetailsPresenter;
import com.flyman.app.web4android.io.net.IWebClientListener;
import com.flyman.app.web4android.io.net.MyWebChromeClient;
import com.flyman.app.web4android.io.net.MyWebViewClient;
import com.flyman.app.web4android.modle.Constant;
import com.flyman.app.web4android.modle.bean.ArticleDetailsTask;
import com.flyman.app.web4android.modle.bean.Msg;
import com.flyman.app.web4android.widget.SuperSwipeRefreshLayout;

import static com.flyman.app.web4android.modle.Constant.IntentExtraKeyConstant.KEY_LIST_2_DETAILS;

public class ArticleDetails extends BaseActivity implements IWebClientListener, SuperSwipeRefreshLayout.OnPullRefreshListener, ArticleDetailsContract.View {

    private SuperSwipeRefreshLayout mSuperSwipeRefreshLayout;
    private WebView mWebView;
    private String url;
    private ProgressBar mProgressBar;
    private ArticleDetailsContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articledetails);
        getIntentFormActivity();
        findWidget();
        initVariable();
        onPresenterStart();
    }

    @Override
    protected void getIntentFormActivity() {
        super.getIntentFormActivity();
        if (!ChenkNullUtil.isNullObj(getIntent())) {
            url = getIntent().getStringExtra(KEY_LIST_2_DETAILS);
            LogUtils.e("onClick", "url " + url);
        }
    }

    @Override
    protected void findWidget() {
        mSuperSwipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipe_view);
        mWebView = (WebView) findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);

    }

    @Override
    protected void initVariable() {
        mWebView.setWebChromeClient(new MyWebChromeClient(this));
        mWebView.setWebViewClient(new MyWebViewClient(this));
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.getSettings().setDomStorageEnabled(true);
        mSuperSwipeRefreshLayout.setHeaderView(createHeadView());
        mSuperSwipeRefreshLayout.setOnPullRefreshListener(this);
        mSuperSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onPresenterStart() {
        if (TextUtils.isEmpty(url)) {
            showError(new Msg(""));
            return;
        }
        //开始获取文章详情
        mPresenter = new ArticleDetailsPresenter(this);
        mPresenter.start(new ArticleDetailsTask(HomePageChildTask.Type.TASK_ARTICLE_DETAILS, url));

        //
        mWebView.loadUrl(url);

    }

    private View createHeadView() {
        return LayoutInflater.from(this).inflate(R.layout.layout_head, null);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        mProgressBar.setVisibility(View.GONE);
        showError(new Msg(Constant.MsgConstant.ID_NET_ERROR_, Constant.MsgConstant.MSG_NET_ERROR));
    }

    @Override
    public void onRefresh() {
//        mWebView.loadUrl(url);
    }

    @Override
    public void onPullDistance(int distance) {

    }

    @Override
    public void onPullEnable(boolean enable) {

    }

    @Override
    public void showError(Msg msg) {
        showLongToast(msg.getMsgInfo());
    }

    /**
     * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
     *
     * @param
     * @return nothing
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            // 返回键退回
            mWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up
        // to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebViewResource();
    }
    public void clearWebViewResource () {
        if (mWebView != null) {
            mWebView.removeAllViews();
            // in android 5.1(sdk:21) we should invoke this to avoid memory leak
            // see (https://coolpers.github.io/webview/memory/leak/2015/07/16/
            // android-5.1-webview-memory-leak.html)
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.setTag(null);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }
    }
}

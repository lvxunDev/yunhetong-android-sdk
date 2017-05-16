package com.yunhetong.sdk.fast.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yunhetong.sdk.tool.YhtLog;

/**
 * author  : kangpeng on 2016/4/22 0022.
 * email   : kangpeng@yunhetong.net
 */
public class SdkWebView extends WebView {


    private float mCurrentScale;
    private WebViewLoadProgress mCurrProgress;

    public interface WebViewLoadProgress {
        void onProgressChanged(int newProgress);
    }

    public void setWebViewLoadProgress(WebViewLoadProgress progress) {
        mCurrProgress = progress;
    }

    public SdkWebView(Context context) {
        super(context);
        initView();
    }

    public SdkWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SdkWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        this.setInitialScale(1);
        debug();
        this.setScrollContainer(false);
        this.setScrollbarFadingEnabled(false);
        this.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        this.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        WebSettings settings = this.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//支持内容重新布局
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true);
        //
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //
        setWebViewClient(new SdkWebViewClient());
        setWebChromeClient(new SdkWebChromeClient());
    }

    @TargetApi(19)
    private void debug() {
        if (YhtLog.DEBUG) {
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
//            }
        }
    }

    private class SdkWebViewClient extends WebViewClient {
        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            mCurrentScale = newScale;
            super.onScaleChanged(view, oldScale, newScale);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(String.valueOf(request.getUrl()));
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    private class SdkWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mCurrProgress != null) {
                mCurrProgress.onProgressChanged(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    /**
     * 得到缩放比例
     */
    public float getmCurrentScale() {
        return mCurrentScale;
    }
}

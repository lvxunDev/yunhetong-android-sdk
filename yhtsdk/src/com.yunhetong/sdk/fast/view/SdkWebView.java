package com.yunhetong.sdk.fast.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yunhetong.sdk.YhtSdk;
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

        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        // // this.getSettings().set
        // this.setInitialScale(1);
        debug();
        settings.setSupportZoom(true);
        this.setScrollContainer(false);
        this.setScrollbarFadingEnabled(false);
        // this.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        this.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        settings.setDefaultTextEncodingName("UTF-8");
        // settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        //
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

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

package com.yunhetong.sdk.fast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * WebView + Progress
 *
 */
public class YhtWebView extends RelativeLayout {

    private SdkWebView mWebView;
    private WebViewProgressBar mProgressbar;
    public RelativeLayout.LayoutParams webViewParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT);
    public RelativeLayout.LayoutParams progressParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, 5);

    public YhtWebView(Context context) {
        super(context);
        initView(context);
    }

    public YhtWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public YhtWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mWebView = new SdkWebView(context);
        mProgressbar = new WebViewProgressBar(context);
        this.addView(mWebView, webViewParams);
        this.addView(mProgressbar, progressParams);
        mWebView.setWebViewLoadProgress(new SdkWebView.WebViewLoadProgress() {
            @Override
            public void onProgressChanged(int newProgress) {
                if (mProgressbar != null)
                    mProgressbar.setProgress(newProgress);
            }
        });
    }

    public void loadUrl(String url){
        if(null!= mWebView){
            mWebView.loadUrl(url);
        }
    }

    public void destroy(){
        if(null!= mWebView){
            mWebView.clearCache(true);
            mWebView.clearHistory();
            mWebView.destroyDrawingCache();
            mWebView.destroy();
            mWebView = null;
        }
    }
    public void setVisibility(){
        if(null!= mWebView){
            mWebView.setVisibility(View.GONE);
            this.setVisibility(View.GONE);
        }
    }

    public SdkWebView getmWebView() {
        return mWebView;
    }
}

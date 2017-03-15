package com.yunhetong.sdk.fast.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.yunhetong.sdk.tool.YhtScreenUtil;


/**
 * 进度条
 */
public class WebViewProgressBar extends View {
    public static final String TAG = "WebViewProgressBar";
    private int mScreenW = -1;
    private int mNewDrawWidth = 0;
    private Paint mPaint = new Paint();

    public WebViewProgressBar(Context context) {
        super(context);
        init();
    }

    public WebViewProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebViewProgressBar(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setProgress(int newProgress) {
        if (newProgress == 100) {
            this.setVisibility(View.GONE);
        } else {
            if (this.getVisibility() != View.VISIBLE) {
                this.setVisibility(View.VISIBLE);
            }
        }
        if (mScreenW == -1) {
            init();
        }
        mNewDrawWidth = newProgress * mScreenW / 100;
        mPaint.setColor(Color.RED);
        this.invalidate();
    }

    private void init() {
        mScreenW = YhtScreenUtil.getScreenWidth(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LinearGradient gradient = new LinearGradient(0, 0, mNewDrawWidth, 5,
                Color.parseColor("#f4b906"), Color.parseColor("#987405"), Shader.TileMode.MIRROR);
        mPaint.setShader(gradient);
        canvas.drawRect(0, 0, mNewDrawWidth, 5, mPaint);
    }
}


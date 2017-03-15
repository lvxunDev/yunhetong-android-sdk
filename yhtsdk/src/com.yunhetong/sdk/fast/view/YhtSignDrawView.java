package com.yunhetong.sdk.fast.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yunhetong.sdk.fast.SignGeneratorFrag;
import com.yunhetong.sdk.tool.ImageBase64Util;
import com.yunhetong.sdk.tool.YhtLog;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 签名画板
 */
public class YhtSignDrawView extends View {
    private final String TAG = "YhtSignDrawView";
    private Paint mPaint;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private Bitmap mBitmap;// 保存轨迹
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    public boolean isSign = false;


    public YhtSignDrawView(Context context) {
        super(context);
        initPaint();
    }

    public YhtSignDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public YhtSignDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 抗锯齿
        mPaint.setDither(true);// 设置抖动
        mPaint.setColor(0xFF000000);// 默认为黑色
        mPaint.setStyle(Paint.Style.STROKE);// paint 风格为空心
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        YhtLog.d(TAG, "onSizeChanged()");
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Lx.d(TAG, "onDraw()");
        canvas.drawColor(0xFFFFFFFF);
        if (shouldClear.get()) { //清除
            YhtLog.d(TAG, "shouldClear");
            shouldClear.set(false);
            // 清除需要重新将bitmap绑定到canvas
            mBitmap.recycle();
            reset();//复位
            return;
        }
        // 如果注视掉下面代码，每次手指抬起来的时候图像就会消失
        // touch_up mPath.reset(); 方法
        // 之前的图像都保存在了mBitmap 里面
        if (!mBitmap.isRecycled()) {
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        } else {
            reset();
        }
    }


    private void reset() {
        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public String getStringSign() {
        Bitmap bm = this.getmBitmap();
        return ImageBase64Util.BitmapToBase64(bm);
    }

    public void clearSign() {
        this.isSign = false;
        this.getShouldClear().set(true);
        this.invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                YhtLog.d(TAG, "onTouchEvent()   ACTION_DOWN ");
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                event.getSize();
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                YhtLog.d(TAG, "onTouchEvent()   ACTION_UP ");
                touch_up();
                invalidate();
                break;
        }
        return true;
    }


    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
        isSign = true;

        if (mFag != null) //讲签名画板按钮清零
            mFag.setBtEnable(true);
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        mPath.reset();
    }


    public Bitmap getmBitmap() {
        return mBitmap;
    }

    private AtomicBoolean shouldClear = new AtomicBoolean(false);

    public AtomicBoolean getShouldClear() {
        return shouldClear;
    }

    public void setShouldClear(AtomicBoolean shouldClear) {
        this.shouldClear = shouldClear;
    }

    public void destroy() {
        if (mBitmap != null)
            mBitmap.recycle();
        mBitmap = null;
    }


    private SignGeneratorFrag mFag;

    public void setFragment(SignGeneratorFrag act) {
        mFag = act;
    }
}

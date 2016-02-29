package com.sola_sky.zyt.linktolove.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import android.os.Handler;



/**
 * Created by Li on 2016/2/27.
 */
public class CircleProgressBar extends View {
    private Paint mPaint;
    private int mMaxRadius;
    private int mMinRadius;

    private int mDistance = 200;

    private int drawColor = 0xff0000;
    private int mWidth;
    private int mHeight;
    private int mCenterX;
    private int mCenterY;
    private double mDegree = 0.0;
    private ValueAnimator mRadiusAnimator;
    private ValueAnimator mColorAnimator;
    private float mIncrease;
    private boolean isFirstDraw = true;
    private Handler mHandler;

    private String mColor;

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public CircleProgressBar(Context context) {
        super(context);
        init();
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(0xff00ff00);
        mHandler = new Handler(Looper.getMainLooper());
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mDegree <= -2 * Math.PI) {
                    mDegree = 0;
                }
                mDegree -= Math.PI / 4;
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
        };
        mHandler.sendEmptyMessageDelayed(1, 100);

    }

    private void startColorAnimation() {
        final ObjectAnimator anim = ObjectAnimator.ofObject(this, "color", new ColorEvalutor(),
                "#ffffff", "#00ff00");
        anim.setDuration(5000);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setInterpolator(new LinearInterpolator());
        anim.start();
    }
    private void startAnimation() {
        mRadiusAnimator = ValueAnimator.ofFloat(0, 35);
        mRadiusAnimator.setDuration(1000);
        mRadiusAnimator.setRepeatMode(ValueAnimator.RESTART);
        mRadiusAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mRadiusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIncrease = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
       mRadiusAnimator.setInterpolator(new LinearInterpolator());
        mRadiusAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFF29B76);
        moveByRotate(canvas);
        if (isFirstDraw) {
            isFirstDraw = false;
            startColorAnimation();
        }


//        canvas.drawCircle(mWidth / 2, mHeight / 2 - mDistance, 30, mPaint);
//
//        canvas.save();
//        canvas.translate(mWidth / 2, mHeight / 2);
//        canvas.rotate(-45);
//        canvas.drawCircle(mDistance, 0, 35, mPaint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.translate(mWidth / 2, mHeight / 2);
//        canvas.drawCircle(mDistance, 0, 40, mPaint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.translate(mWidth / 2, mHeight / 2);
//        canvas.rotate(45);
//        canvas.drawCircle(mDistance, 0, 45, mPaint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.translate(mWidth / 2, mHeight / 2);
//        canvas.rotate(90);
//        canvas.drawCircle(mDistance, 0, 50, mPaint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.translate(mWidth / 2, mHeight / 2);
//        canvas.rotate(135);
//        canvas.drawCircle(mDistance, 0, 55, mPaint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.translate(mWidth / 2, mHeight / 2);
//        canvas.rotate(180);
//        canvas.drawCircle(mDistance, 0, 60, mPaint);
//        canvas.restore();
//
//        canvas.save();
//        canvas.translate(mWidth / 2, mHeight / 2);
//        canvas.rotate(225);
//        canvas.drawCircle(mDistance, 0, 65, mPaint);
//        canvas.restore();

    }

    private float calculateRadius(float radius, float increase) {
        float temp = radius + increase;
        if (temp > 65) {
            temp -= 30;
        }
        return temp;
    }
    private void drawCircle(Canvas canvas, float distance, double degree, float radius) {
        canvas.drawCircle((float)(mCenterX + distance * Math.cos(degree)),
                (float)(mCenterY - distance * Math.sin(degree)), radius, mPaint);
        mDegree += Math.PI / 4;
    }

    private void moveByIncrease(Canvas canvas) {
        drawCircle(canvas, mDistance, mDegree, calculateRadius(30, mIncrease));
        drawCircle(canvas, mDistance, mDegree, calculateRadius(35, mIncrease));
        drawCircle(canvas, mDistance, mDegree, calculateRadius(40, mIncrease));
        drawCircle(canvas, mDistance, mDegree, calculateRadius(45, mIncrease));
        drawCircle(canvas, mDistance, mDegree, calculateRadius(50, mIncrease));
        drawCircle(canvas, mDistance, mDegree, calculateRadius(55, mIncrease));
        drawCircle(canvas, mDistance, mDegree, calculateRadius(60, mIncrease));
        drawCircle(canvas, mDistance, mDegree, calculateRadius(65, mIncrease));
        Log.d("I:", mIncrease+"");
        if (isFirstDraw) {
            isFirstDraw = false;
            startAnimation();
        }
    }

    private void moveByRotate(Canvas canvas) {

        double temp = mDegree;
        drawCircle(canvas, mDistance, mDegree, 30);
        drawCircle(canvas, mDistance, mDegree, 35);
        drawCircle(canvas, mDistance, mDegree, 40);
        drawCircle(canvas, mDistance, mDegree, 45);
        drawCircle(canvas, mDistance, mDegree, 50);
        drawCircle(canvas, mDistance, mDegree, 55);
        drawCircle(canvas, mDistance, mDegree, 60);
        drawCircle(canvas, mDistance, mDegree, 65);
        mDegree = temp;
    //    postInvalidateDelayed(100);
    }
}

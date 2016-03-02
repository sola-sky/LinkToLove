package com.sola_sky.zyt.linktolove.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import android.os.Handler;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.utils.DimensionUtils;


/**
 * Created by Li on 2016/2/27.
 */
public class CircleProgressBar extends View {

    private static final float DEFAULT_RADIUS_DP = 40;
    private static final String DEFAULT_START_COLOR = "#00ff00";
    private static final String DEFAULT_END_COLOR = "#f76d09";
    private static final float DEFAULT_INTERVAL = 2;

    private Paint mPaint;


    private int mMinRadius;
    private int mMaxRadius;

    private float mDistance = 200;
    private String mStartColor;
    private String mEndColor;
    private Drawable mBgDrawable;
    private int drawColor = 0xff0000;
    private int mWidth;
    private int mHeight;
    private int mCenterX;
    private int mCenterY;
    private double mDegree = 0.0;
    private ValueAnimator mRadiusAnimator;
    private ObjectAnimator mColorAnimator;
    private float mIncrease;
    private boolean isFirstDraw = true;
    private Handler mHandler;
    private int mType;

    private Context mContext;


    private ColorEvaluator mEvaluator;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public CircleProgressBar(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        float radius = DimensionUtils.dp2px(getContext(), DEFAULT_RADIUS_DP);

        TypedArray ta = attrs == null ? null : getContext()
                .obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        if (ta != null) {
            mDistance = ta.getDimension(R.styleable.CircleProgressBar_radius, radius);
            mStartColor = ta.getString(R.styleable.CircleProgressBar_start_color);
            mEndColor = ta.getString(R.styleable.CircleProgressBar_end_color);
            mType = ta.getInt(R.styleable.CircleProgressBar_type, 0);
            mBgDrawable = ta.getDrawable(R.styleable.CircleProgressBar_background);
            ta.recycle();
        }

        if (mStartColor == null) {
            mStartColor = DEFAULT_START_COLOR;
        }
        if (mEndColor == null) {
            mEndColor = DEFAULT_END_COLOR;
        }

        mEvaluator = new ColorEvaluator();
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
        mColorAnimator = ObjectAnimator.ofObject(this, "color", mEvaluator, mStartColor,
                mEndColor);
        mColorAnimator.setDuration(5000);
        mColorAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        mColorAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mColorAnimator.setInterpolator(new LinearInterpolator());
        mColorAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mEvaluator.setmCurrentBlue(-1);
                mEvaluator.setmCurrentGreen(-1);
                mEvaluator.setmCurrentRed(-1);
            }
        });
        mColorAnimator.start();
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
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidth;
        int minWidth = (int) (mDistance * 2 + mMaxRadius * 2 + getPaddingLeft()
                + getPaddingRight());
        minWidth = Math.max(minWidth, getSuggestedMinimumWidth());
        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = Math.max(minWidth, widthSize);
        } else {
            measureWidth = minWidth;
            if (widthMode == MeasureSpec.AT_MOST) {
                measureWidth = Math.min(measureWidth, widthSize);
            }
        }

        setMeasuredDimension(measureWidth, measureWidth);

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

    enum Type {
        Circle, Arc
    }
}

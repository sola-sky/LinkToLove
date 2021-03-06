package com.sola_sky.zyt.linktolove.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.utils.DimensionUtils;


/**
 * Created by Li on 2016/2/27.
 */
public class CircleProgressBar extends View {

    private static final float DEFAULT_RADIUS_DP = 50;
    private static final float DEFAULT_INTERVAL_DP = 2;
    private static final String DEFAULT_START_COLOR = "#00ff00";
    private static final String DEFAULT_END_COLOR = "#f76d09";

    private static final int REFRESH_DRAWER = 3;

    private Paint mPaint;


    private float mMaxRadius;
    private float mInterval;
    private float mDistance = 200;
    private String mStartColor;
    private String mEndColor;
    private Drawable mBgDrawable;

 //   private ProgressBar
    private int drawColor = 0xff0000;
    private int mWidth;
    private int mHeight;
    private int mCenterX;
    private int mCenterY;
    private double mDegree = 0.0;
    private ObjectAnimator mColorAnimator;
    private float mIncrease;
    private int mType;

    private boolean mAttached;
    private boolean mRefreshIsPosted;

    private RefreshProgressRunnable mRefreshDegreeRunner;



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
        init(null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        float radius = DimensionUtils.dp2px(getContext(), DEFAULT_RADIUS_DP);
        mInterval = DimensionUtils.dp2px(getContext(), DEFAULT_INTERVAL_DP);
        TypedArray ta = attrs == null ? null : getContext()
                .obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        if (ta != null) {
            mDistance = ta.getDimension(R.styleable.CircleProgressBar_radius, radius);
            mStartColor = ta.getString(R.styleable.CircleProgressBar_start_color);
            mEndColor = ta.getString(R.styleable.CircleProgressBar_end_color);
            mType = ta.getInt(R.styleable.CircleProgressBar_type, 0);
            mBgDrawable = ta.getDrawable(R.styleable.CircleProgressBar_bg);
            ta.recycle();
        }

        mMaxRadius = (float) (mDistance * Math.sin(1.0 / 18 * Math.PI) * 2);
        float minRadius = mMaxRadius - 7 * mInterval;
        if (minRadius <= 0) {
            mInterval = mMaxRadius  / 7;
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



    }

    private void refreshDegree() {
        if (mDegree <= -2 * Math.PI) {
            mDegree = 0;
        }
        mDegree -= Math.PI / 4;
    }



    private void startColorAnimation() {
        if (getVisibility() != VISIBLE) {
            return;
        }
        if (mColorAnimator == null) {
            mColorAnimator = ObjectAnimator.ofObject(this, "color", mEvaluator, mStartColor,
                    mEndColor);
        }
        mColorAnimator.cancel();
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
        postInvalidate();
    }

    private void stopColorAnimation() {
        if (mColorAnimator != null) {
            mColorAnimator.cancel();
        }
        postInvalidate();
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
        rotate(canvas);
    }

    private void drawCircle(Canvas canvas, float distance, double degree, float radius) {
        canvas.drawCircle((float) (mCenterX + distance * Math.cos(degree)),
                (float) (mCenterY - distance * Math.sin(degree)), radius, mPaint);
        mDegree += Math.PI / 4;
    }

    private void rotate(Canvas canvas) {
        double temp = mDegree;
        for (int i = 7; i >= 0; i--) {
            drawCircle(canvas, mDistance, mDegree, mMaxRadius - mInterval * i);
        }
        mDegree = temp;
    }

    private void postRunnable() {
        if (mRefreshDegreeRunner == null) {
            mRefreshDegreeRunner = new RefreshProgressRunnable();
        }
        if (!mRefreshIsPosted && mAttached) {
            postDelayed(mRefreshDegreeRunner, 200);
            mRefreshIsPosted = true;
        }
    }

    private void removeRunnable() {
        if (mRefreshDegreeRunner != null) {
            removeCallbacks(mRefreshDegreeRunner);
            mRefreshIsPosted = false;
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility != getVisibility()) {
            if (visibility == GONE || visibility == INVISIBLE) {
                stopColorAnimation();
                removeRunnable();
            } else {
                startColorAnimation();
                postRunnable();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopColorAnimation();
            removeRunnable();
        } else {
            startColorAnimation();
            postRunnable();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startColorAnimation();
        mAttached = true;
        postRunnable();
    }

    @Override
    protected void onDetachedFromWindow() {
        removeRunnable();
        stopColorAnimation();
        super.onDetachedFromWindow();
        mAttached = false;
    }

    class RefreshProgressRunnable implements Runnable {

        @Override
        public void run() {
            refreshDegree();
            mRefreshIsPosted = false;
            postRunnable();
        }
    }

    enum Type {
        Circle, Arc
    }
}

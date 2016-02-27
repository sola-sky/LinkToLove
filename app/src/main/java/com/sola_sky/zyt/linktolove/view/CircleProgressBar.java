package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFF29B76);
        canvas.drawCircle(mWidth / 2, mHeight / 2 - mDistance, 30, mPaint);

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(-45);
        canvas.drawCircle(mDistance, 0, 35, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(mDistance, 0, 40, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(45);
        canvas.drawCircle(mDistance, 0, 45, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(90);
        canvas.drawCircle(mDistance, 0, 50, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(135);
        canvas.drawCircle(mDistance, 0, 55, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(180);
        canvas.drawCircle(mDistance, 0, 60, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(225);
        canvas.drawCircle(mDistance, 0, 65, mPaint);
        canvas.restore();

    }
}

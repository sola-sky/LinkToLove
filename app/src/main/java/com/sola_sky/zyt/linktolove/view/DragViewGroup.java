package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sola_sky.zyt.linktolove.utils.LogUtils;

/**
 * Created by Li on 2016/4/6.
 */
public class DragViewGroup extends ViewGroup {

    private static final String TAG = "DragViewGroup";
    private ViewDragHelper mDragHelper;

    private View mLeftView;
    private View mContentView;
    private float mCurMovePercent = 0;

    public DragViewGroup(Context context) {
        super(context);
        init();
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new MyDragCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.logd(TAG, "onMeasure");
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalStateException("view child count must be 2");
        }
        for (int i = 0; i < childCount; i++) {
            View view = null;
            if (i == 0) {
                view = mContentView = getChildAt(i);
            } else {
                view = mLeftView = getChildAt(i);
            }
            MyMarginLayoutParams lp = (MyMarginLayoutParams) view.getLayoutParams();
            measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0);

            parentDesireWidth = Math.max(parentDesireWidth, view.getMeasuredWidth() + lp.leftMargin
            + lp.rightMargin);
            parentDesireHeight += view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
        }
        parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
        parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());
        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, 0),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
        LogUtils.logd(TAG, "onLayout");
        View contentView = getChildAt(0);
        MyMarginLayoutParams contentViewLp = (MyMarginLayoutParams) contentView.getLayoutParams();
        int left0 = getPaddingLeft() + contentViewLp.leftMargin + contentView.getPaddingLeft();
        int top0 = getPaddingTop() + contentViewLp.topMargin + contentView.getPaddingTop();
        int right0 = left0 + contentView.getMeasuredWidth();
        int bottom0 = top0 + contentView.getMeasuredHeight();
        contentView.layout(left0, top0, right0, bottom0);

        View dragView = getChildAt(1);
        MyMarginLayoutParams dragViewLp = (MyMarginLayoutParams) dragView.getLayoutParams();
        int right1 = -(dragView.getRight() + dragViewLp.rightMargin);
        int left1 = right1 - dragView.getMeasuredWidth();
        int top1 = dragViewLp.topMargin + dragView.getPaddingTop();
        int bottom1 = top1 + dragView.getMeasuredHeight();
        dragView.layout(left1, top1, right1, bottom1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 2) {

        } else {
            throw new IllegalArgumentException("view count must be 2");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MyMarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MyMarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyMarginLayoutParams(getContext(), attrs);
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    class MyMarginLayoutParams extends MarginLayoutParams {
        public MyMarginLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public MyMarginLayoutParams(int width, int height) {
            super(width, height);
        }

        public MyMarginLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public MyMarginLayoutParams(LayoutParams source) {
            super(source);
        }
    }

    class MyDragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            LogUtils.logd(TAG, "onViewPositionChanged");
            LogUtils.logd(TAG, "left:" + left);
            if (changedView == mContentView) {
                LogUtils.logd(TAG, "Yes mContentView");
                mCurMovePercent = (mLeftView.getMeasuredWidth() - left) / (float)mLeftView
                        .getMeasuredWidth();
            } else {
                LogUtils.logd(TAG, "Not mContentView");
            }
            LogUtils.logd(TAG, "mCurMovePercent:" + mCurMovePercent);

            float contentViewScale = 0.2f * mCurMovePercent + 0.8f;
            mContentView.setScaleX(contentViewScale);
            mContentView.setScaleY(contentViewScale);
            LogUtils.logd(TAG, "contentViewScale:" + contentViewScale);

            float leftViewScale = 1.8f - contentViewScale;
            LogUtils.logd(TAG, "leftViewScale:" + leftViewScale);
            mLeftView.setScaleX(leftViewScale);
            mLeftView.setScaleY(leftViewScale);
            mLeftView.setAlpha(leftViewScale);

            float leftTranX = mLeftView.getMeasuredWidth() * mCurMovePercent;
            LogUtils.logd(TAG, "leftTranX:" + leftTranX);
            mLeftView.setTranslationX(leftTranX);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }
    }
}

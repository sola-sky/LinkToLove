package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sola_sky.zyt.linktolove.utils.LogUtils;

/**
 * Created by Li on 2016/3/27.
 */
public class ViewGroupDrag extends ViewGroup {

    private ViewDragHelper mDragHelper;

    private View mContentView;
    private View mMenuView;

    private int mOffset;


    public ViewGroupDrag(Context context) {
        super(context);
        init();
    }

    public ViewGroupDrag(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewGroupDrag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new MyDragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mMenuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            parentDesireWidth += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            parentDesireHeight = Math.max(parentDesireHeight, childView.getMeasuredHeight()
                    + lp.topMargin + lp.bottomMargin);
        }
        parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
        parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());
        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, 0),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount > 0) {
            View childView = getChildAt(0);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            childView.layout(getPaddingLeft() + lp.leftMargin, getPaddingTop() + lp.topMargin,
                    getPaddingLeft() + lp.leftMargin + childView.getMeasuredWidth(),
                    getPaddingTop() + lp.topMargin + childView.getMeasuredHeight());
        }

            View childView = getChildAt(1);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            LogUtils.logd("ViewGroupDrag", mOffset - childView.getMeasuredWidth() - lp.rightMargin + "");
            childView.layout(mOffset - childView.getMeasuredWidth() - lp.rightMargin, getPaddingTop()
            + lp.topMargin, mOffset - lp.rightMargin, getPaddingTop() + lp.topMargin
                    + childView.getMeasuredHeight());


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
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    class MyMarginParams extends MarginLayoutParams {

        public MyMarginParams(MarginLayoutParams source) {
            super(source);
        }

        public MyMarginParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public MyMarginParams(int width, int height) {
            super(width, height);
        }

        public MyMarginParams(LayoutParams source) {
            super(source);
        }
    }
    class MyDragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            LogUtils.logd("ViewGroupDrag", "tryCaptureView");
            return child == mMenuView;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(mMenuView, pointerId);
            LogUtils.logd("ViewGroupDrag", "onEdgeDragStarted");
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            LogUtils.logd("ViewGroupDrag", "onEdgeTouched");
            mOffset = 10;
            invalidate();

        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LogUtils.logd("ViewGroupDrag", "clampViewPositionHorizontal");
            return Math.max(-child.getWidth(), Math.min(0, left));
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            LogUtils.logd("ViewGroupDrag", "getViewHorizontalDragRange");
            return mMenuView == child ? child.getWidth() : 0;
        }
    }
}

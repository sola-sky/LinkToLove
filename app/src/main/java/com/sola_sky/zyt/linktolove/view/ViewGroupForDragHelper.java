package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Li on 2016/3/20.
 */
public class ViewGroupForDragHelper extends ViewGroup{

    private ViewDragHelper mViewDragHelper;

    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public ViewGroupForDragHelper(Context context) {
        super(context);
        init();
    }

    public ViewGroupForDragHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewGroupForDragHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;
        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            MyMarginLayoutParams lp = (MyMarginLayoutParams) view.getLayoutParams();
            measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0);
            parentDesireWidth += view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, view.getMeasuredHeight() + lp.topMargin
                    + lp.rightMargin);
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());
        }
        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, 0),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int offsetWidth = getPaddingLeft();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MyMarginLayoutParams lp = (MyMarginLayoutParams) childView.getLayoutParams();
            childView.layout(offsetWidth + lp.leftMargin, getPaddingTop() + lp.topMargin,
                    offsetWidth + lp.leftMargin + childView.getMeasuredWidth(), getPaddingTop()
            + lp.topMargin + childView.getMeasuredHeight());
            offsetWidth += lp.leftMargin + childView.getMeasuredWidth() + lp.rightMargin;
        }

        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
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

    class MyMarginLayoutParams extends MarginLayoutParams {

        public MyMarginLayoutParams(int width, int height) {
            super(width, height);
        }

        public MyMarginLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public MyMarginLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public MyMarginLayoutParams(LayoutParams source) {
            super(source);
        }
    }

    private class ViewDragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView || child == mAutoBackView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int leftBound = getLeft() + getPaddingLeft();
            int rightBound = getWidth() - getPaddingRight() - child.getWidth() + getLeft();
            return Math.min(Math.max(leftBound, left), rightBound);
      //      return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mAutoBackView) {
                mViewDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                invalidate();
            }
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mViewDragHelper.captureChildView(mEdgeTrackerView, pointerId);
        }
    }
}

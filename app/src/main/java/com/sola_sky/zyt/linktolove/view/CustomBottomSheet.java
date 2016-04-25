package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sola_sky.zyt.linktolove.utils.LogUtils;

/**
 * Created by Li on 2016/4/26.
 */
public class CustomBottomSheet extends ViewGroup {
    private static final String TAG = "CustomBottomSheet";
    private ViewDragHelper mDragHelper;

    private View mBottomView;
    private View mContentView;
    private float mCurMovePercent = 0;

    private float mLeftInitialX;

    public CustomBottomSheet(Context context) {
        super(context);
        init();
    }

    public CustomBottomSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBottomSheet(Context context, AttributeSet attrs, int defStyleAttr) {
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
                view = mBottomView = getChildAt(i);
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
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
        int left1 = getPaddingLeft() + dragViewLp.leftMargin + dragView.getPaddingLeft();
        int top1 = bottom0 + dragViewLp.topMargin + dragView.getPaddingTop();
        int right1 = left1 + dragView.getMeasuredWidth();
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
            return child == mBottomView;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            LogUtils.logd(TAG, "onViewDragStateChanged");
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            LogUtils.logd(TAG, "onViewPositionChanged");


        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            LogUtils.logd(TAG, "onViewCaptured");
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            if (edgeFlags == ViewDragHelper.EDGE_BOTTOM) {
           //     mEdgeTouched = true;
                LogUtils.logd("ViewGroupDrag", "onEdgeTouched");
                MarginLayoutParams lp = (MarginLayoutParams) mBottomView.getLayoutParams();
                int x = mBottomView.getLeft();
                int y = mBottomView.getTop() - 100;
                mDragHelper.smoothSlideViewTo(getChildAt(1), x, y);
                invalidate();
            }
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            LogUtils.logd(TAG, "onEdgeLock");
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            if (edgeFlags == ViewDragHelper.EDGE_BOTTOM) {
                LogUtils.logd("ViewGroupDrag", "onEdgeDragStarted");
                mDragHelper.captureChildView(mBottomView, pointerId);
            }
        }

        @Override
        public int getOrderedChildIndex(int index) {
            LogUtils.logd(TAG, "getOrderedChildIndex:" + index);
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            LogUtils.logd(TAG, "getViewHorizontalDragRange");
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            LogUtils.logd(TAG, "getViewVerticalDragRange");
            return (child == mBottomView ? mBottomView.getMeasuredHeight() : 0);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            LogUtils.logd(TAG, "clamViewPositionVertical");
            if (child == mBottomView) {
                return Math.max(mContentView.getBottom()-mBottomView.getMeasuredHeight(),
                        Math.min(top, mContentView.getBottom()));
            }
            return super.clampViewPositionVertical(child, top, dy);
        }
    }
}

package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.sola_sky.zyt.linktolove.utils.LogUtils;

/**
 * Created by Li on 2016/3/27.
 */
public class ViewGroupDrag extends ViewGroup {

    private ViewDragHelper mDragHelper;

    private ViewConfiguration mViewConfiguration;

    private View mContentView;
    private View mMenuView;

    private boolean mDragged = false;
    private boolean mEdgeTouched = false;


    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    private int mScrimColor = DEFAULT_SCRIM_COLOR;
    private float mScrimtOpacity;
    private Paint mScrimPaint = new Paint();

    private float mInitX;
    private float mInitY;



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
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
        mViewConfiguration = ViewConfiguration.get(getContext());
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
            LogUtils.logd("ViewGroupDrag", - childView.getMeasuredWidth() - lp.rightMargin + "");
            childView.layout(-childView.getMeasuredWidth() - lp.rightMargin, getPaddingTop()
            + lp.topMargin, -lp.rightMargin, getPaddingTop() + lp.topMargin
                    + childView.getMeasuredHeight());


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final boolean interceptForDrag = mDragHelper.shouldInterceptTouchEvent(ev);

        boolean interceptForTap = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();
                if (mScrimtOpacity > 0) {
                    final View child = mDragHelper.findTopChildUnder((int) x, (int) y);
                    if (child != null && child == mContentView) {
                        interceptForTap = true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    break;
        }
        return interceptForDrag | interceptForTap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         mDragHelper.processTouchEvent(event);
//        switch(event.getAction()) {
//            case MotionEvent.ACTION_UP:
//                LogUtils.logd("ViewGroup", "onViewReleased");
//                MarginLayoutParams lp = (MarginLayoutParams) getChildAt(1).getLayoutParams();
//                int x = -getChildAt(1).getMeasuredWidth() - lp.rightMargin;
//                if (getChildAt(1).getLeft() > x / 2) {
//                    mDragHelper.settleCapturedViewAt(0, lp.topMargin + getPaddingTop());
//                } else {
//                    mDragHelper.settleCapturedViewAt(x, lp.topMargin + getPaddingTop());
//                }
//                invalidate();
//            break;
//        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitX = event.getX();
                mInitY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!mDragged) {
                    final View view = mDragHelper.findTopChildUnder((int)mInitX, (int)mInitY);
                    if ((view != null && view == mContentView) || mEdgeTouched) {
                        if (getChildAt(1).getRight() > 0) {
                            MarginLayoutParams lp = (MarginLayoutParams) getChildAt(1).getLayoutParams();
                            int x = -getChildAt(1).getMeasuredWidth() - lp.rightMargin;
                            mDragHelper.smoothSlideViewTo(getChildAt(1), x, lp.topMargin
                                    + getPaddingTop());
                            invalidate();
                        }
                    }
//                    requestLayout();
                }
                mDragged = false;
                mEdgeTouched = false;
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        mScrimtOpacity = mMenuView.getRight() * 1.f / mMenuView.getWidth();
        mScrimtOpacity = Math.max(0, mScrimtOpacity);
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final int height = getHeight();
        final boolean drawingContent = child == mContentView;
        int clipLeft = 0, clipRight = getWidth();

        final int restoreCount = canvas.save();
        if (drawingContent) {
            clipLeft = Math.max(0, mMenuView.getRight());
            canvas.clipRect(clipLeft, 0, clipRight, height);
        }
        boolean result =  super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(restoreCount);

        if (mScrimtOpacity > 0 && drawingContent) {
            final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
            final int curAlpha = (int) (baseAlpha * mScrimtOpacity);
            final int color = (curAlpha << 24) | (mScrimColor & 0xffffff);
            mScrimPaint.setColor(color);
            canvas.drawRect(clipLeft, 0, clipRight, height, mScrimPaint);
        }

        return result;
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
            if (edgeFlags == ViewDragHelper.EDGE_LEFT) {
                mDragged = true;
                LogUtils.logd("ViewGroupDrag", "onEdgeDragStarted");
                mDragHelper.captureChildView(mMenuView, pointerId);
            }
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            if (edgeFlags == ViewDragHelper.EDGE_LEFT) {
                mEdgeTouched = true;
                LogUtils.logd("ViewGroupDrag", "onEdgeTouched");
                MarginLayoutParams lp = (MarginLayoutParams) getChildAt(1).getLayoutParams();
                int x = -getChildAt(1).getMeasuredWidth() - lp.rightMargin + 60;
                mDragHelper.smoothSlideViewTo(getChildAt(1), x, lp.topMargin
                        + getPaddingTop());
                invalidate();
            }
           // requestLayout();
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

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            LogUtils.logd("ViewGroup", "onViewReleased");
            MarginLayoutParams lp = (MarginLayoutParams) releasedChild.getLayoutParams();
            int x = -releasedChild.getMeasuredWidth() - lp.rightMargin;
            if (releasedChild.getLeft() > x / 2) {
                mDragHelper.settleCapturedViewAt(0, lp.topMargin + getPaddingTop());
            } else {
                mDragHelper.settleCapturedViewAt(x, lp.topMargin + getPaddingTop());
            }
            invalidate();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            LogUtils.logd("ViewGroupDrag", "onViewPositionChanged");
            mScrimtOpacity = mMenuView.getRight() * 1.f / mMenuView.getWidth();
            invalidate();

        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        public MyDragHelperCallback() {
            super();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }
    }
}

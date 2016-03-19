package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.sola_sky.zyt.linktolove.features.Login.LoginPresenter;
import com.sola_sky.zyt.linktolove.utils.LogUtils;

/**
 * Created by Li on 2016/3/14.
 */
public class MyViewGroup extends ViewGroup {


    private Scroller mScroller;

    private int mTouchSlop;

    private float mYDown;

    private float mYLastMove;

    private float mYMove;

    private int topBorder;

    private int bottomBorder;


    public MyViewGroup(Context context) {
        super(context);
        init();
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MyMarginLayoutParams lp = (MyMarginLayoutParams) child.getLayoutParams();
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

            parentDesireWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            parentDesireHeight += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());
        }

        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, 0),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int parentPaddingLeft = getPaddingLeft();
        int parentPaddingTop = getPaddingTop();

        int childCount = getChildCount();
        int offsetHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MyMarginLayoutParams lp = (MyMarginLayoutParams) child.getLayoutParams();
            child.layout(parentPaddingLeft + lp.leftMargin, offsetHeight + parentPaddingTop
                            + lp.topMargin, parentPaddingLeft + lp.leftMargin
                            + child.getMeasuredWidth(), child.getMeasuredHeight() + parentPaddingTop
                    + lp.topMargin + offsetHeight );
            offsetHeight += lp.topMargin + lp.bottomMargin + child.getMeasuredHeight();
        }
        topBorder = getChildAt(0).getTop();
        bottomBorder = getChildAt(childCount - 1).getBottom();
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mYDown = ev.getRawY();
                mYLastMove = mYDown;
               break;
            case MotionEvent.ACTION_MOVE:
                mYMove = ev.getRawY();
                float diff = Math.abs(mYMove - mYDown);
                mYLastMove = mYMove;
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.logd("onTouch", "down");
                return true;
            case MotionEvent.ACTION_MOVE:
                mYMove = event.getRawY();
                int scrolledY = (int) (mYLastMove - mYMove);
                if (getScrollY() + scrolledY < topBorder) {
                    scrollTo(0, topBorder);
                    return true;
                } else if (getScrollY() + scrolledY + getHeight() > bottomBorder) {
                    scrollTo(0, bottomBorder - getHeight());
                    return true;
                }
                LogUtils.logd("ScrollY", scrolledY+"");
                scrollBy(0, scrolledY);
                mYLastMove = mYMove;
                LogUtils.logd("onTouch", "move");
                return true;
            case MotionEvent.ACTION_UP:
                int targetIndex = (getScrollY() + getHeight() / 2) / getHeight();
                int dy = targetIndex * getHeight() - getScrollY();
                mScroller.startScroll(0, getScrollY(), 0, dy);
                LogUtils.logd("getScrollY", getScrollY() + "");
                LogUtils.logd("dy", dy+"");
                invalidate();
                LogUtils.logd("onTouch", "up");
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}

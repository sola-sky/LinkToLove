package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Li on 2016/3/27.
 */
public class ViewGroupDrag extends ViewGroup {

    private ViewDragHelper mDragHelper;

    private View mContentView;
    private View mMenuView;


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
        int offset = 0;
        for (int i = 1; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            childView.layout(offset - childView.getMeasuredWidth() - lp.rightMargin, getPaddingTop()
            + lp.topMargin, offset - lp.rightMargin, getPaddingTop() + lp.topMargin
                    + childView.getMeasuredHeight());
            offset -= childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
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
            return false;
        }
    }
}

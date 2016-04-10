package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.sola_sky.zyt.linktolove.utils.LogUtils;

/**
 * Created by Li on 2016/4/6.
 */
public class DragViewGroup extends ViewGroup {

    private static final String TAG = "DRAGVIEWGROUP";
    private ViewDragHelper mDragHelper;
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
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
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

    class MyDragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }
    }
}

package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Li on 2016/3/20.
 */
public class ViewGroupForDragHelper extends ViewGroup{

    public ViewGroupForDragHelper(Context context) {
        super(context);
    }

    public ViewGroupForDragHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupForDragHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}

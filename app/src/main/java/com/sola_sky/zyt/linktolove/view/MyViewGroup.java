package com.sola_sky.zyt.linktolove.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.sola_sky.zyt.linktolove.features.Login.LoginPresenter;

/**
 * Created by Li on 2016/3/14.
 */
public class MyViewGroup extends ViewGroup {



    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}

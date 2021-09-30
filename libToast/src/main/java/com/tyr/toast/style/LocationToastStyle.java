package com.tyr.toast.style;

import android.content.Context;
import android.view.View;

import com.tyr.toast.config.IToastStyle;

/**
 * <p>
 * Toast位置包装样式实现
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:36
 */
public class LocationToastStyle implements IToastStyle<View> {

    private final IToastStyle<?> mStyle;

    private final int   mGravity;
    private final int   mXOffset;
    private final int   mYOffset;
    private final float mHorizontalMargin;
    private final float mVerticalMargin;

    public LocationToastStyle(IToastStyle<?> style, int gravity) {
        this(style, gravity, 0, 0, 0, 0);
    }

    public LocationToastStyle(IToastStyle<?> style, int gravity, int xOffset, int yOffset, float horizontalMargin,
            float verticalMargin) {
        mStyle = style;
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;
        mHorizontalMargin = horizontalMargin;
        mVerticalMargin = verticalMargin;
    }

    @Override
    public View createView(Context context) {
        return mStyle.createView(context);
    }

    @Override
    public int getToastGravity() {
        return mGravity;
    }

    @Override
    public int getToastXOffset() {
        return mXOffset;
    }

    @Override
    public int getToastYOffset() {
        return mYOffset;
    }

    @Override
    public float getToastHorizontalMargin() {
        return mHorizontalMargin;
    }

    @Override
    public float getToastVerticalMargin() {
        return mVerticalMargin;
    }
}
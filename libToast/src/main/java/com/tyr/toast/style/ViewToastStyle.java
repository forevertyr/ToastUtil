package com.tyr.toast.style;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.tyr.toast.config.IToastStyle;

/**
 * <p>
 * Toast自定义布局包装样式实现
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:36
 */
public class ViewToastStyle implements IToastStyle<View> {

    private final int            mLayoutId;
    private final IToastStyle<?> mStyle;

    public ViewToastStyle(int layoutId, IToastStyle<?> style) {
        mLayoutId = layoutId;
        mStyle = (style == null ? new BaseToastStyle() : style);
    }

    @Override
    public View createView(Context context) {
        return LayoutInflater.from(context).inflate(mLayoutId, null);
    }

    @Override
    public int getToastGravity() {
        return mStyle.getToastGravity();
    }

    @Override
    public int getToastXOffset() {
        return mStyle.getToastXOffset();
    }

    @Override
    public int getToastYOffset() {
        return mStyle.getToastYOffset();
    }

    @Override
    public float getToastHorizontalMargin() {
        return mStyle.getToastHorizontalMargin();
    }

    @Override
    public float getToastVerticalMargin() {
        return mStyle.getToastVerticalMargin();
    }
}
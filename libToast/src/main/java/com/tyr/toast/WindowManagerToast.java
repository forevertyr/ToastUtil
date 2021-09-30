package com.tyr.toast;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.tyr.toast.config.IToast;

/**
 * <p>
 * 自定义Toast（用于解决关闭通知栏权限之后不能弹吐司的问题和 Android 11不能自定义吐司样式的问题）
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:37
 */
public class WindowManagerToast implements IToast {

    /** Toast 实现类 */
    private final WindowManagerToastImpl mToastImpl;
    /** Toast 布局 */
    private       View                   mView;
    /** Toast 消息 View */
    private       TextView               mMessageView;
    /** Toast 显示重心 */
    private       int                    mGravity;
    /** Toast 显示时长 */
    private       int                    mDuration;
    /** 水平偏移 */
    private       int                    mXOffset;
    /** 垂直偏移 */
    private       int                    mYOffset;
    /** 水平间距 */
    private       float                  mHorizontalMargin;
    /** 垂直间距 */
    private       float                  mVerticalMargin;

    public WindowManagerToast(Activity activity) {
        mToastImpl = new WindowManagerToastImpl(activity, this);
    }

    @Override
    public void show() {
        mToastImpl.show();
    }

    @Override
    public void cancel() {
        mToastImpl.cancel();
    }

    @Override
    public void setText(CharSequence text) {
        if (mMessageView == null) {
            return;
        }
        mMessageView.setText(text);
    }

    @Override
    public void setView(View view) {
        mView = view;
        if (mView == null) {
            mMessageView = null;
            return;
        }
        mMessageView = findMessageView(view);
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void setDuration(int duration) {
        mDuration = duration;
    }

    @Override
    public int getDuration() {
        return mDuration;
    }

    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;
    }

    @Override
    public int getGravity() {
        return mGravity;
    }

    @Override
    public int getXOffset() {
        return mXOffset;
    }

    @Override
    public int getYOffset() {
        return mYOffset;
    }

    @Override
    public void setMargin(float horizontalMargin, float verticalMargin) {
        mHorizontalMargin = horizontalMargin;
        mVerticalMargin = verticalMargin;
    }

    @Override
    public float getHorizontalMargin() {
        return mHorizontalMargin;
    }

    @Override
    public float getVerticalMargin() {
        return mVerticalMargin;
    }
}
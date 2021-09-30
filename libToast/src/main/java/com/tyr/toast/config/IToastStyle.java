package com.tyr.toast.config;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

/**
 * <p>
 * 默认样式接口
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:35
 */
public interface IToastStyle<V extends View> {

    /**
     * 创建Toast视图
     */
    V createView(Context context);

    /**
     * 获取Toast显示重心
     */
    default int getToastGravity() {
        return Gravity.CENTER;
    }

    /**
     * 获取Toast水平偏移
     */
    default int getToastXOffset() {
        return 0;
    }

    /**
     * 获取Toast垂直偏移
     */
    default int getToastYOffset() {
        return 0;
    }

    /**
     * 获取Toast水平间距
     */
    default float getToastHorizontalMargin() {
        return 0;
    }

    /**
     * 获取Toast垂直间距
     */
    default float getToastVerticalMargin() {
        return 0;
    }

}
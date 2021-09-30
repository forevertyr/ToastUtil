package com.tyr.toast.config;

import android.app.Application;

/**
 * <p>
 * Toast 处理策略
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:35
 */
public interface IToastStrategy {

    /**
     * 注册策略
     */
    void registerStrategy(Application application);

    /**
     * 绑定样式
     */
    void bindStyle(IToastStyle<?> style);

    /**
     * 创建Toast
     */
    IToast createToast(Application application);

    /**
     * 显示Toast
     */
    void showToast(CharSequence text);

    /**
     * 取消Toast
     */
    void cancelToast();
}
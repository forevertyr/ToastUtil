package com.tyr.toast.config;

/**
 * <p>
 * Toast拦截器接口
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:35
 */
public interface IToastInterceptor {
    boolean intercept(CharSequence text);
}
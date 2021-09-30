package com.tyr.toast.demo;

import android.util.Log;

import com.tyr.toast.ToastUtil;
import com.tyr.toast.config.IToastInterceptor;

/**
 * <p>
 * 自定义 Toast 拦截器（用于追踪 Toast 调用的位置）
 * </p>
 *
 * @author TYR
 * @since 2021/9/8 9:02
 */
public final class ToastInterceptor implements IToastInterceptor {

    @Override
    public boolean intercept(CharSequence text) {
        if (!BuildConfig.PRO_CONDITION) {
            // 获取调用的堆栈信息
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            // 跳过最前面两个堆栈
            for (int i = 2; stackTrace.length > 2 && i < stackTrace.length; i++) {
                // 获取代码行数
                int lineNumber = stackTrace[i].getLineNumber();
                // 获取类的全路径
                String className = stackTrace[i].getClassName();
                if (lineNumber <= 0 || className.startsWith(ToastUtil.class.getName())) {
                    continue;
                }

                Log.d("ToastUtil", "(" + stackTrace[i].getFileName() + ":" + lineNumber + ") " + text.toString());
                break;
            }
        }
        return false;
    }
}
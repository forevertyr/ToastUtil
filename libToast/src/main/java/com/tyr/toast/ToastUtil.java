package com.tyr.toast;

import android.app.Application;

import com.tyr.toast.config.IToastInterceptor;
import com.tyr.toast.config.IToastStrategy;
import com.tyr.toast.config.IToastStyle;
import com.tyr.toast.style.BaseToastStyle;
import com.tyr.toast.style.BlackToastStyle;
import com.tyr.toast.style.LocationToastStyle;
import com.tyr.toast.style.ViewToastStyle;
import com.tyr.toast.style.WhiteToastStyle;

/**
 * <p>
 * Toast工具类
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:38
 */
@SuppressWarnings("unused")
public final class ToastUtil {

    /** Application 对象 */
    private static Application       mApplication;
    /** Toast 处理策略 */
    private static IToastStrategy    mToastStrategy;
    /** Toast 样式 */
    private static IToastStyle<?>    mToastStyle;
    /** Toast 拦截器（可空） */
    private static IToastInterceptor mToastInterceptor;

    private ToastUtil() {
    }

    /**
     * 初始化Toast，需要在 {@link Application#onCreate()} 中初始化
     */
    public static void init(Application application) {
        init(application, mToastStyle);
    }

    /**
     * 初始化Toast及样式
     */
    public static void init(Application application, IToastStyle<?> style) {
        mApplication = application;

        if (mToastStrategy == null) {
            setStrategy(new ToastStrategy());
        }

        setStyle(style == null ? new BaseToastStyle() : style);
    }

    /**
     * 判断当前框架是否已经初始化
     */
    public static boolean isInit() {
        return mApplication != null && mToastStrategy != null && mToastStyle != null;
    }

    /**
     * 显示对象的吐司
     */
    public static void show(Object object) {
        show(object != null ? object.toString() : "null");
    }

    /**
     * 显示文本吐司
     */
    public static void show(CharSequence text) {
        if (text == null || text.length() == 0) {
            return;
        }

        if (mToastInterceptor != null && mToastInterceptor.intercept(text)) {
            return;
        }

        mToastStrategy.showToast(text);
    }

    /**
     * 取消吐司的显示
     */
    public static void cancel() {
        mToastStrategy.cancelToast();
    }

    /**
     * 设置吐司的位置
     */
    public static void setGravity(int gravity) {
        setGravity(gravity, 0, 0);
    }

    public static void setGravity(int gravity, int xOffset, int yOffset) {
        setGravity(gravity, xOffset, yOffset, 0, 0);
    }

    public static void setGravity(int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin) {
        mToastStrategy.bindStyle(new LocationToastStyle(mToastStyle,
                                                        gravity,
                                                        xOffset,
                                                        yOffset,
                                                        horizontalMargin,
                                                        verticalMargin));
    }

    /**
     * 给当前Toast设置新的布局
     */
    public static void setView(int layoutId) {
        if (layoutId <= 0) {
            return;
        }
        setStyle(new ViewToastStyle(layoutId, mToastStyle));
    }

    /**
     * 初始化全局的Toast样式
     *
     * @param style 样式实现类，框架已经实现两种不同的样式
     *              黑色样式：{@link BlackToastStyle}
     *              白色样式：{@link WhiteToastStyle}
     */
    public static void setStyle(IToastStyle<?> style) {
        mToastStyle = style;
        mToastStrategy.bindStyle(style);
    }

    public static IToastStyle<?> getStyle() {
        return mToastStyle;
    }

    /**
     * 设置Toast显示策略
     */
    public static void setStrategy(IToastStrategy strategy) {
        mToastStrategy = strategy;
        mToastStrategy.registerStrategy(mApplication);
    }

    public static IToastStrategy getStrategy() {
        return mToastStrategy;
    }

    /**
     * 设置Toast拦截器（可以根据显示的内容决定是否拦截这个Toast）
     * 场景：打印 Toast 内容日志、根据 Toast 内容是否包含敏感字来动态切换其他方式显示
     */
    public static void setInterceptor(IToastInterceptor interceptor) {
        mToastInterceptor = interceptor;
    }

    public static IToastInterceptor getInterceptor() {
        return mToastInterceptor;
    }

}
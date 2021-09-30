package com.tyr.toast;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.Toast;

import com.tyr.toast.config.IToast;

/**
 * <p>
 * 自定义Toast实现类（使用WindowManager）
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:38
 */
public class WindowManagerToastImpl {

    private static final Handler HANDLER              = new Handler(Looper.getMainLooper());
    /** 短吐司显示的时长 */
    private static final long    SHORT_DURATION_DELAY = 2_000L;
    /** 长吐司显示的时长 */
    private static final long    LONG_DURATION_DELAY  = 3_500L;

    /** 当前吐司对象 */
    private final IToast                      mToast;
    /** WindowManager辅助类 */
    private final ToastWindowManagerLifecycle mToastWindowManagerLifecycle;
    /** 当前是否已经显示 */
    private       boolean                     mShow;

    public WindowManagerToastImpl(Activity activity, IToast toast) {
        mToast = toast;
        mToastWindowManagerLifecycle = new ToastWindowManagerLifecycle(activity);
    }

    boolean isShow() {
        return mShow;
    }

    public void setShow(boolean show) {
        mShow = show;
    }

    public void show() {
        if (isShow()) {
            return;
        }
        HANDLER.removeCallbacks(mShowRunnable);
        HANDLER.post(mShowRunnable);
    }

    public void cancel() {
        if (!isShow()) {
            return;
        }
        HANDLER.removeCallbacks(mCancelRunnable);
        HANDLER.post(mCancelRunnable);
    }

    private final Runnable mShowRunnable = new Runnable() {

        @Override
        public void run() {
            Activity activity = mToastWindowManagerLifecycle.getActivity();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                return;
            }

            final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.windowAnimations = android.R.style.Animation_Toast;
            params.flags =
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            params.packageName = activity.getPackageName();
            params.gravity = mToast.getGravity();
            params.x = mToast.getXOffset();
            params.y = mToast.getYOffset();
            params.verticalMargin = mToast.getVerticalMargin();
            params.horizontalMargin = mToast.getHorizontalMargin();

            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return;
            }

            try {
                windowManager.addView(mToast.getView(), params);
                HANDLER.postDelayed(() -> cancel(),
                                    mToast.getDuration() == Toast.LENGTH_LONG ?
                                    LONG_DURATION_DELAY :
                                    SHORT_DURATION_DELAY);
                mToastWindowManagerLifecycle.register(WindowManagerToastImpl.this);
                setShow(true);
            } catch (IllegalStateException | WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
        }
    };

    private final Runnable mCancelRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                Activity activity = mToastWindowManagerLifecycle.getActivity();
                if (activity == null) {
                    return;
                }

                WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
                if (windowManager == null) {
                    return;
                }
                windowManager.removeViewImmediate(mToast.getView());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                mToastWindowManagerLifecycle.unregister();
                setShow(false);
            }
        }
    };

}
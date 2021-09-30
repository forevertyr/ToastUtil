package com.tyr.toast;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.tyr.toast.config.IToast;
import com.tyr.toast.config.IToastStrategy;
import com.tyr.toast.config.IToastStyle;

import java.lang.ref.WeakReference;

/**
 * <p>
 * Toast 默认处理器
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:38
 */
public class ToastStrategy extends Handler implements IToastStrategy {

    /** 文本长度 */
    private static final int  DEFAULT_TEXT_LENGTH = 20;
    /** 延迟时间 */
    private static final long DELAY_TIME          = 200L;
    /** 显示吐司标识 */
    private static final int  TYPE_SHOW           = 1;
    /** 取消吐司标识 */
    private static final int  TYPE_CANCEL         = 2;

    /** 应用上下文 */
    private Application           mApplication;
    /** 前台Activity对象 */
    private Activity              mForegroundActivity;
    /** Toast对象 */
    private WeakReference<IToast> mToastReference;
    /** Toast样式 */
    private IToastStyle<?>        mToastStyle;

    public ToastStrategy() {
        super(Looper.getMainLooper());
    }

    @Override
    public void registerStrategy(Application application) {
        mApplication = application;

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mForegroundActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (mForegroundActivity != activity) {
                    return;
                }
                mForegroundActivity = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    @Override
    public void bindStyle(IToastStyle<?> style) {
        mToastStyle = style;
    }

    @Override
    public IToast createToast(Application application) {
        IToast toast;
        if (mForegroundActivity != null) {
            toast = new WindowManagerToast(mForegroundActivity);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            toast = new SafeToast(application);
        } else {
            toast = new SystemToast(application);
        }

        /* 1.targetSdkVersion >= 30 的情况下在后台显示自定义样式的 Toast 会被系统屏蔽，并且日志会输出以下警告：Blocking custom toast from package com
        .xxx.xxx due to package not in the foreground*/
        /* 2.targetSdkVersion < 30 的情况下 new Toast，并且不设置样式显示，系统会抛出以下异常：java.lang.RuntimeException: This Toast was not
        created with Toast.makeText()*/
        if (toast instanceof WindowManagerToast || Build.VERSION.SDK_INT < Build.VERSION_CODES.R || application.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.R) {
            toast.setView(mToastStyle.createView(application));
            toast.setGravity(mToastStyle.getToastGravity(),
                             mToastStyle.getToastXOffset(),
                             mToastStyle.getToastYOffset());
            toast.setMargin(mToastStyle.getToastHorizontalMargin(), mToastStyle.getToastVerticalMargin());
        }
        return toast;
    }

    @Override
    public void showToast(CharSequence text) {
        removeMessages(TYPE_SHOW);
        // 延迟一段时间之后再执行，因为在没有通知栏权限的情况下，Toast只能显示当前Activity。
        Message msg = Message.obtain();
        msg.what = TYPE_SHOW;
        msg.obj = text;
        sendMessageDelayed(msg, DELAY_TIME);
    }

    @Override
    public void cancelToast() {
        removeMessages(TYPE_CANCEL);
        sendEmptyMessage(TYPE_CANCEL);
    }

    @Override
    public void handleMessage(Message msg) {
        IToast toast = null;
        if (mToastReference != null) {
            toast = mToastReference.get();
        }

        switch (msg.what) {
            case TYPE_SHOW:
                if (!(msg.obj instanceof CharSequence)) {
                    break;
                }

                if (toast != null) {
                    toast.cancel();
                }

                toast = createToast(mApplication);
                mToastReference = new WeakReference<>(toast);
                toast.setDuration(getToastDuration((CharSequence) msg.obj));
                toast.setText((CharSequence) msg.obj);
                toast.show();
                break;
            case TYPE_CANCEL:
                if (toast == null) {
                    break;
                }
                toast.cancel();
                break;
            default:
                break;
        }
    }

    protected int getToastDuration(CharSequence text) {
        return text.length() > DEFAULT_TEXT_LENGTH ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
    }
}
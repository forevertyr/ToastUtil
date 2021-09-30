package com.tyr.toast;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * <p>
 * Toast显示安全处理
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:37
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
@SuppressWarnings("all")
public final class SafeToast extends SystemToast {

    public SafeToast(Application application) {
        super(application);

        try {
            // 获取 mTN 字段对象
            Field mTNField = Toast.class.getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object mTN = mTNField.get(this);

            // 获取 mTN 中的 mHandler 字段对象
            Field mHandlerField = mTNField.getType().getDeclaredField("mHandler");
            mHandlerField.setAccessible(true);
            Handler mHandler = (Handler) mHandlerField.get(mTN);

            // 偷梁换柱
            mHandlerField.set(mTN, new SafeHandler(mHandler));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            // Android 9.0 上反射会出现报错
            // Accessing hidden field Landroid/widget/Toast;->mTN:Landroid/widget/Toast$TN;
            // java.lang.NoSuchFieldException: No field mTN in class Landroid/widget/Toast;
            e.printStackTrace();
        }
    }

    class SafeHandler extends Handler {

        private final Handler mHandler;

        SafeHandler(Handler handler) {
            mHandler = handler;
        }

        @Override
        public void handleMessage(final Message msg) {
            // 捕获这个异常，避免程序崩溃
            try {
                //目前发现在 Android 7.1 主线程被阻塞之后弹吐司会导致崩溃，可使用 Thread.sleep(5000) 进行复现 ；查看源码得知 Google 在 Android 8.0
                // 已经修复了此问题；主线程阻塞之后 Toast 也会被阻塞，android.widget.Toast 因为显示超时导致 Window Token 失效
                mHandler.handleMessage(msg);
            } catch (WindowManager.BadTokenException | IllegalStateException e) {
                //android.view.WindowManager$BadTokenException：Unable to add window -- token android.os.BinderProxy is
                // not valid; is your activity running?java.lang.IllegalStateException：View android.widget.TextView has
                // already been added to the window manager.
                e.printStackTrace();
            }
        }
    }

}
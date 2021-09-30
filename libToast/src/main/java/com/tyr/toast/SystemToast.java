package com.tyr.toast;

import android.app.Application;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tyr.toast.config.IToast;

/**
 * <p>
 * 系统Toast
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:37
 */
@SuppressWarnings("deprecation")
public class SystemToast extends Toast implements IToast {

    private TextView mMessageView;

    public SystemToast(Application application) {
        super(application);
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        if (view == null) {
            mMessageView = null;
            return;
        }
        mMessageView = findMessageView(view);
    }

    @Override
    public void setText(CharSequence text) {
        super.setText(text);
        if (mMessageView == null) {
            return;
        }
        mMessageView.setText(text);
    }
}
package com.tyr.toast.demo;

import android.app.Application;

import com.tyr.toast.ToastUtil;
import com.tyr.toast.style.WhiteToastStyle;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initToast();
    }

    private void initToast() {
        ToastUtil.setInterceptor(new ToastInterceptor());
        ToastUtil.init(this, new WhiteToastStyle());
    }
}

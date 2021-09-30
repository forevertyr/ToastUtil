package com.tyr.toast.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tyr.toast.ToastUtil;
import com.tyr.toast.style.BlackToastStyle;
import com.tyr.toast.style.WhiteToastStyle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initRes();
    }

    private void initRes() {
        Button btnToastSystem = findViewById(R.id.btn_toast_system);
        Button btnToastStyleBase = findViewById(R.id.btn_toast_style_base);
        Button btnToastStyleWhite = findViewById(R.id.btn_toast_style_white);
        Button btnToastStyleBlack = findViewById(R.id.btn_toast_style_black);

        btnToastSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "我是系统Toast", Toast.LENGTH_SHORT).show();
            }
        });
        btnToastStyleBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("我是ToastUtil--基础风格Toast");
            }
        });
        btnToastStyleWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.setStyle(new WhiteToastStyle());
                ToastUtil.show("我是ToastUtil--白色风格Toast");
            }
        });
        btnToastStyleBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.setStyle(new BlackToastStyle());
                ToastUtil.show("我是ToastUtil--黑色风格Toast");
            }
        });
    }

}

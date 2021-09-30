package com.tyr.toast.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyr.toast.config.IToastStyle;

/**
 * <p>
 * 基本样式实现
 * </p>
 *
 * @author JackYang
 * @since 2021/9/10 10:36
 */
public class BaseToastStyle implements IToastStyle<TextView> {

    @Override
    public TextView createView(Context context) {
        TextView textView = new TextView(context);
        textView.setId(android.R.id.message);
        textView.setGravity(getTextGravity());
        textView.setTextColor(getTextColor(context));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize(context));

        int horizontalPadding = getHorizontalPadding(context);
        int verticalPadding = getVerticalPadding(context);
        textView.setPaddingRelative(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT));

        Drawable background = getBackgroundDrawable(context);
        textView.setBackground(background);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.setZ(getTranslationZ(context));
        }

        textView.setMaxLines(getMaxLines());

        return textView;
    }

    // -------------------------------------------------以下为默认属性，子类继承后重新赋值------------------------------------------------------------------

    /**
     * 获取文字位置
     */
    protected int getTextGravity() {
        return Gravity.CENTER;
    }

    /**
     * 获取文字颜色
     */
    protected int getTextColor(Context context) {
        return 0XEEFFFFFF;
    }

    /**
     * 获取文字大小
     */
    protected float getTextSize(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取横向padding
     */
    protected int getHorizontalPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                               24,
                                               context.getResources().getDisplayMetrics());
    }

    /**
     * 获取竖向padding
     */
    protected int getVerticalPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                               14,
                                               context.getResources().getDisplayMetrics());
    }

    /**
     * 获取背景
     */
    protected Drawable getBackgroundDrawable(Context context) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(0X88000000);
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                           10,
                                                           context.getResources().getDisplayMetrics()));
        return drawable;
    }

    /**
     * 获取Z轴阴影
     */
    protected float getTranslationZ(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取最大显示行数
     */
    protected int getMaxLines() {
        return 4;
    }

}

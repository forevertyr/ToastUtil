package com.tyr.toast.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;

/**
 * <p>
 * 默认白色样式实现
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:37
 */
public class WhiteToastStyle extends BaseToastStyle {

    @Override
    protected int getTextColor(Context context) {
        return 0XBB000000;
    }

    @Override
    protected Drawable getBackgroundDrawable(Context context) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(0XFFEAEAEA);
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                           10,
                                                           context.getResources().getDisplayMetrics()));
        return drawable;
    }

}
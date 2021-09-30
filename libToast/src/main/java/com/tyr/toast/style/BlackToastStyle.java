package com.tyr.toast.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;

/**
 * <p>
 * 默认黑色样式实现
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:36
 */
public class BlackToastStyle extends BaseToastStyle {

    @Override
    protected int getTextColor(Context context) {
        return 0XEEFFFFFF;
    }

    @Override
    protected Drawable getBackgroundDrawable(Context context) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(0X88000000);
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                           10,
                                                           context.getResources().getDisplayMetrics()));
        return drawable;
    }

}
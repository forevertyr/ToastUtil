package com.tyr.toast.config;

import android.view.View;
import android.widget.TextView;

/**
 * <p>
 * Toast接口
 * </p>
 *
 * @author TYR
 * @since 2021/9/7 16:35
 */
public interface IToast {

    /**
     * 获取用于显示消息的TextView
     */
    default TextView findMessageView(View view) {
        if (view instanceof TextView) {
            if (view.getId() == View.NO_ID) {
                view.setId(android.R.id.message);
            } else if (view.getId() != android.R.id.message) {
                throw new IllegalArgumentException("You must set the ID value of TextView to android.R.id.message");
            }
            return (TextView) view;
        }

        if (view.findViewById(android.R.id.message) instanceof TextView) {
            return ((TextView) view.findViewById(android.R.id.message));
        }

        throw new IllegalArgumentException("You must include a TextView with an ID value of android.R.id.message");
    }

    void show();

    void cancel();

    void setText(CharSequence text);

    void setView(View view);

    View getView();

    void setDuration(int duration);

    int getDuration();

    void setGravity(int gravity, int xOffset, int yOffset);

    int getGravity();

    int getXOffset();

    int getYOffset();

    void setMargin(float horizontalMargin, float verticalMargin);

    float getHorizontalMargin();

    float getVerticalMargin();

}
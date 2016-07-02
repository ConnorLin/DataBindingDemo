package com.connorlin.databinding.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.adapters.ListenerUtil;
import android.util.AttributeSet;
import android.view.View;

import com.connorlin.databinding.listener.ColorChangeListener;

/**
 * ClassName: TwoWay
 * Description:
 * Author: connorlin
 * Date: Created on 2016/7/2.
 */


@InverseBindingMethods({
        @InverseBindingMethod(
                type = TwoWayView.class,
                attribute = "color",
                event = "colorAttrChanged",    // 不是必须的，仅作标记用
                method = "getColor"     // 不是必须的，仅作标记用
        )
})
public class TwoWayView extends View {

    private int mColor;
    private ColorChangeListener mColorChangeListener;

    public TwoWayView(Context context) {
        this(context, null);
    }

    public TwoWayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoWayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    @InverseBindingAdapter(attribute = "color", event = "colorAttrChanged")
    public static int getColor(TwoWayView view) {
        return view.getColor();
    }

    @BindingAdapter("color")
    public static void setColor(TwoWayView view, int color) {
        if (color != view.getColor()) {
            view.setColor(color);
            view.setBackgroundColor(color);
        }
    }

    public void setOnColorChangeListener(ColorChangeListener listener) {
        mColorChangeListener = listener;
    }

    @BindingAdapter(value = {"colorChangeListener", "colorAttrChanged"}, requireAll = false)
    public static void setColorListener(TwoWayView view,
            final ColorChangeListener listener,
            final InverseBindingListener colorChange) {

        ColorChangeListener newValue = new ColorChangeListener() {
            @Override
            public void onColorChange(TwoWayView view, int color) {
                if (listener != null) {
                    listener.onColorChange(view, color);
                }

                if (colorChange != null) {
                    colorChange.onChange();
                }
            }
        };

        ColorChangeListener oldValue =
                ListenerUtil.trackListener(view, newValue, view.getId());

        if (oldValue != null) {
            view.setOnColorChangeListener(null);
        }
        view.setOnColorChangeListener(newValue);
    }
}

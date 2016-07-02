package com.connorlin.databinding.model;

import android.databinding.ObservableInt;
import android.graphics.Color;

import com.connorlin.databinding.listener.ColorChangeListener;
import com.connorlin.databinding.view.TwoWayView;

/**
 * ClassName: TwoWayHandler
 * Description:
 * Author: connorlin
 * Date: Created on 2016/7/2.
 */
public class TwoWayModel {

    public ObservableInt mColor = new ObservableInt(Color.GRAY);

    public ColorChangeListener onColorChangeListener() {
        return new ColorChangeListener() {
            @Override
            public void onColorChange(TwoWayView view, int color) {
                view.setBackgroundColor(Color.YELLOW);
            }
        };
    }
}

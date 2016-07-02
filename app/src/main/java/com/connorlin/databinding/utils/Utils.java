package com.connorlin.databinding.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * ClassName: Utils
 * Description:
 * Author: connorlin
 * Date: Created on 2016/7/2.
 */
public class Utils {

    // 使用注解，无需手动调用此函数
    @BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Glide.with(view.getContext()).load(url).error(error).into(view);
    }
}

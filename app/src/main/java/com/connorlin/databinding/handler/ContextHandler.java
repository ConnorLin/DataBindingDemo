package com.connorlin.databinding.handler;

import android.content.Context;

import com.connorlin.databinding.R;

/**
 * ClassName: ContextHandler
 * Description:
 * Author: connorlin
 * Date: Created on 2016-6-30.
 */
public class ContextHandler {

    public String loadString(Context context) {
        return context.getResources().getString(R.string.string_from_context);
    }
}

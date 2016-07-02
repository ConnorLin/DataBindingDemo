package com.connorlin.databinding.task;

import android.util.Log;

/**
 * ClassName: Task
 * Description:
 * Author: connorlin
 * Date: Created on 2016-7-1.
 */
public class Task implements Runnable {

    @Override
    public void run() {
        Log.i("connor", "Task run");
    }
}

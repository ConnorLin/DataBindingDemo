package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityEventBinding;
import com.connorlin.databinding.handler.EventHandler;
import com.connorlin.databinding.task.Task;

public class EventActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event);
        ActivityEventBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_event);

        binding.setHandler(new EventHandler(this));
        binding.setTask(new Task());
    }
}

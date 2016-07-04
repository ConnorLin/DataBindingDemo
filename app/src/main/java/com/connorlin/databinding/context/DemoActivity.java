package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.connorlin.databinding.DemoBinding;
import com.connorlin.databinding.R;
import com.connorlin.databinding.adapter.ListAdapter;
import com.connorlin.databinding.handler.DemoHandler;
import com.connorlin.databinding.model.ObservableContact;

public class DemoActivity extends BaseActivity {

    private DemoBinding mDemoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        ObservableContact contact = new ObservableContact("", "");
        mDemoBinding.setContact(contact);

        initViews();
    }

    private void initViews() {
        mDemoBinding.listView.setEmptyView(mDemoBinding.emptyView);

        ListAdapter listAdapter = new ListAdapter(this);
        mDemoBinding.listView.setAdapter(listAdapter);

        DemoHandler viewHandler = new DemoHandler(this, mDemoBinding, listAdapter);
        mDemoBinding.setHandler(viewHandler);
    }
}

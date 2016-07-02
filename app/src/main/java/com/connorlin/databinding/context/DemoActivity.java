package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.connorlin.databinding.DemoBinding;
import com.connorlin.databinding.R;
import com.connorlin.databinding.adapter.ListAdapter;
import com.connorlin.databinding.handler.DemoHandler;
import com.connorlin.databinding.model.ObservableContact;

public class DemoActivity extends BaseActivity {

    private TextView mEmpty;
    private ListView mListView;

    private DemoBinding mDemoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_demo);

        mDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        ObservableContact contact = new ObservableContact("", "");
        mDemoBinding.setContact(contact);

        initViews();
    }

    private void initViews() {
        mEmpty = (TextView) findViewById(R.id.empty_view);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setEmptyView(mEmpty);

        ListAdapter listAdapter = new ListAdapter(this);
        mListView.setAdapter(listAdapter);

        DemoHandler viewHandler = new DemoHandler(this, mDemoBinding, listAdapter);
        mDemoBinding.setHandler(viewHandler);
    }
}

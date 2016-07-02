package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityViewStubBinding;
import com.connorlin.databinding.databinding.IncludeBinding;
import com.connorlin.databinding.model.User;

import java.util.Map;

public class ViewStubActivity extends BaseActivity {

    private ActivityViewStubBinding mActivityViewStubBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_stub);
        mActivityViewStubBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_view_stub);
        mActivityViewStubBinding.viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                IncludeBinding viewStubBinding = DataBindingUtil.bind(inflated);
                User user = new User("Connor", "Lin", 28);
                viewStubBinding.setUser(user);
            }
        });
    }

    // 此处会标红，请不要担心，这并不是错误，标红方法是 ViewStubProxy 里的方法。
    public void inflate(View view) {
        if (!mActivityViewStubBinding.viewStub.isInflated()) {
            mActivityViewStubBinding.viewStub.getViewStub().inflate();
        }
    }
}

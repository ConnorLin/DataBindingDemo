package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityAttributeSettersBinding;
import com.connorlin.databinding.model.User;

public class AttributeSettersActivity extends BaseActivity {

    private ActivityAttributeSettersBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attribute_setters);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_attribute_setters);

        User user = new User("Connor", "Lin", 28);
        mBinding.setUser(user);

        mBinding.setUrl("http://connorlin.github.io/images/avatar.jpg");
    }
}

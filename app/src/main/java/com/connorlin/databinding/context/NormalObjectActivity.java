package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityNormalObjectBinding;
import com.connorlin.databinding.model.Contact;

public class NormalObjectActivity extends BaseActivity {

    private ActivityNormalObjectBinding mBinding;
    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_normal_object);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_normal_object);

        mContact = new Contact("police", "110");
        mBinding.setContact(mContact);
    }

    public void onClick(View view) {
        // 更新普通java bean 对象并不会同步更新
        mContact.setName("ConnorLin");
        mContact.setPhone("12345678901");
    }
}

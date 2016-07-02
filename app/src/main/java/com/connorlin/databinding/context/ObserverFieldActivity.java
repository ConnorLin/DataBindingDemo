package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityObserverFieldBinding;
import com.connorlin.databinding.model.ObservableFieldContact;

public class ObserverFieldActivity extends AppCompatActivity {

    ActivityObserverFieldBinding mBinding;
    private ObservableFieldContact mObservableFieldContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_observer_field);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_observer_field);

        mObservableFieldContact = new ObservableFieldContact("police","110");
        mBinding.setContact(mObservableFieldContact);
    }

    public void onClick(View view) {
        mObservableFieldContact.mName.set("ConnorLin");
        mObservableFieldContact.mPhone.set("12345678901");
    }
}

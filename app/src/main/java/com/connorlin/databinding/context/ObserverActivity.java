package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityObserverBinding;
import com.connorlin.databinding.model.ObservableContact;

public class ObserverActivity extends BaseActivity {

    private ActivityObserverBinding mBinding;
    private ObservableContact mObservableContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_observer);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_observer);

        mObservableContact = new ObservableContact("police","110");
        mBinding.setContact(mObservableContact);
    }

    public void onClick(View view) {
        mObservableContact.setName("ConnorLin");
        mObservableContact.setPhone("12345678901");
    }
}

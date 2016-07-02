package com.connorlin.databinding.context;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.connorlin.databinding.R;
import com.connorlin.databinding.databinding.ActivityObserverCollectionBinding;

public class ObserverCollectionActivity extends BaseActivity {

    private ActivityObserverCollectionBinding mBinding;

    private ObservableArrayMap<String, String> mUserMap;
    private ObservableArrayList<String> mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_observer_collection);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_observer_collection);

        mUserMap = new ObservableArrayMap<>();
        mUserMap.put("firstName", "Connor");
        mUserMap.put("lastName", "Lin");
        mUserMap.put("age", "28");
        mBinding.setUser(mUserMap);

        mUserList = new ObservableArrayList<>();
        mUserList.add(mUserMap.valueAt(0));
        mUserList.add(mUserMap.valueAt(1));
        mUserList.add(mUserMap.valueAt(2));
        mBinding.setUserList(mUserList);
    }

    public void onClick(View view) {
        mUserMap.put("firstName", "Google");
        mUserMap.put("lastName", "Inc.");
        mUserMap.put("age", "17");

        mUserList.set(0, mUserMap.valueAt(0));
        mUserList.set(1, mUserMap.valueAt(1));
        mUserList.set(2, mUserMap.valueAt(2));
    }
}

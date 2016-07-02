package com.connorlin.databinding.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.connorlin.databinding.BR;

/**
 * ClassName: Contact
 * Description:
 * Author: connorlin
 * Date: Created on 2016-6-28.
 */
public class ObservableContact extends BaseObservable {
    private String mName;
    private String mPhone;

    public ObservableContact(String name, String phone) {
        mName = name;
        mPhone = phone;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
        notifyPropertyChanged(BR.phone);
    }
}

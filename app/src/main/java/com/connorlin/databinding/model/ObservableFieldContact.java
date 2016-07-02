package com.connorlin.databinding.model;

import android.databinding.ObservableField;

/**
 * ClassName: PlainContact
 * Description:
 * Author: connorlin
 * Date: Created on 2016-6-29.
 */
public class ObservableFieldContact {
    public ObservableField<String> mName = new ObservableField<>();
    public ObservableField<String> mPhone = new ObservableField<>();

    public ObservableFieldContact(String name, String phone) {
        mName.set(name);
        mPhone.set(phone);
    }
}

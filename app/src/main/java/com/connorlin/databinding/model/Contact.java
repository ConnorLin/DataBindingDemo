package com.connorlin.databinding.model;

/**
 * ClassName: BaseContact
 * Description:
 * Author: connorlin
 * Date: Created on 2016-6-29.
 */
public class Contact {

    private String mName;
    private String mPhone;

    public Contact(String name, String phone) {
        mName = name;
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}

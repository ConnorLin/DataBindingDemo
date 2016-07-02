package com.connorlin.databinding.model;

/**
 * ClassName: Item
 * Description:
 * Author: connorlin
 * Date: Created on 2016-7-1.
 */
public class RecyclerItem {

    private String mType;
    private String mAction;

    public RecyclerItem(String type, String action) {
        mType = type;
        mAction = action;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }
}

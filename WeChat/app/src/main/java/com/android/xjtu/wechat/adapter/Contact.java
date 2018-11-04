package com.android.xjtu.wechat.adapter;

import android.util.Log;

import java.io.Serializable;

public class Contact implements Serializable {
    private String mName;
    private int mType;


    public Contact(String name, int type) {
        mName = name;
        mType = type;
    }

    public String getmName() {
        Log.i("contact", mName + "," + mName.lastIndexOf('_'));
        int index = mName.lastIndexOf('_');
        if (index == -1)
            return mName;
        else
            return mName.substring(0, mName.lastIndexOf('_'));
    }

    public int getmType() {
        return mType;
    }

    public long getId() {
        return Long.valueOf(mName.substring(mName.lastIndexOf('_') + 1));
    }

}


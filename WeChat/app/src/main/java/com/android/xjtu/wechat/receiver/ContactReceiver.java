package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.xjtu.wechat.activity.ContactActivity;
import com.android.xjtu.wechat.activity.GlobalValue;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

/**
 * Created by DELL on 2018/11/3.
 */

public class ContactReceiver extends BroadcastReceiver {
    private ContactActivity contactActivity;
    public ContactReceiver(ContactActivity contactActivity) {
        super();
        this.contactActivity = contactActivity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int method = intent.getIntExtra(Constant.KEY_METHOD, Constant.DEFAULT);
        String data = intent.getStringExtra(Constant.BROADCAST_DATA);
        if (method == Constant.RSP_GET_FRIEND) {
            //
            Log.i("contact", "contact receiver get, " + data);
            try {
                JSONObject jsonObject = new JSONObject(data);
                int count = jsonObject.getInt(Constant.KEY_COUNTER);
                if (count > 0) {
                    contactActivity.getLocalFriends();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.xjtu.wechat.activity.SessionActivity;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

/**
 * Created by DELL on 2018/11/2.
 */

public class SessionReceiver extends BroadcastReceiver {
    private SessionActivity sessionActivity;

    public SessionReceiver(SessionActivity sessionActivity) {
        super();
        this.sessionActivity = sessionActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(Constant.BROADCAST_DATA);
        Log.i("session", data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            int method = jsonObject.getInt(Constant.KEY_METHOD);
            if (method == Constant.RSP_GET_SESSION) {
                Log.i("session receiver", "" + method + ": " + jsonObject.getInt(Constant.KEY_COUNTER));
            } else if (method == Constant.RSP_GET_FRIEND) {
                Log.i("session receiver", "" + method + ": " + jsonObject.getInt(Constant.KEY_COUNTER));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sessionActivity.updateSessionList();
        //Log.i("session receiver", intent.getStringExtra(Constant.BROADCAST_DATA));
    }
}

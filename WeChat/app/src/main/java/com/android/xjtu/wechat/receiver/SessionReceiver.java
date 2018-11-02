package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.xjtu.wechat.activity.SessionActivity;

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
        sessionActivity.updateSessionList();
        //Log.i("session receiver", intent.getStringExtra(Constant.BROADCAST_DATA));
    }
}

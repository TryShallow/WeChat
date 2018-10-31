package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.xjtu.wechat.service.ChatService;

/**
 * Created by DELL on 2018/10/31.
 */

public class LoginReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //the message is stored in bundle
        //write your own code to handle all message for service and update UI
        Bundle bundle = intent.getBundleExtra(ChatService.BROADCAST_DATA);
        Toast.makeText(context, bundle.getString("text"), Toast.LENGTH_LONG).show();
    }
}

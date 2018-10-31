package com.android.xjtu.wechat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class ChatService extends Service {
    public final static String LOCAL_BROADCAST = "com.android.xjtu.wechat.LOCAL_BROADCAST";
    public final static String BROADCAST_DATA = "broadcast_data";

    public ChatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new ChatBinder(this);
    }

    public void sendLocalBroadcast(Bundle bundle) {
        Intent intent = new Intent(LOCAL_BROADCAST);
        intent.putExtra(BROADCAST_DATA, bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}

package com.android.xjtu.wechat.service;

import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by DELL on 2018/10/31.
 */

public class ChatBinder extends Binder {
    private ChatService chatService;

    public ChatBinder(ChatService chatService) {
        this.chatService = chatService;
    }

    public ChatService getChatService() {
        return chatService;
    }
}

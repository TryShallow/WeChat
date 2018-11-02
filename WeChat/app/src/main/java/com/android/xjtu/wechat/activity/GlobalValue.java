package com.android.xjtu.wechat.activity;

import android.app.Application;

import com.android.xjtu.wechat.service.ChatService;

/**
 * Created by DELL on 2018/11/2.
 */

public class GlobalValue extends Application {
    private ChatService chatService;

    public ChatService getChatService() {
        return chatService;
    }

    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }
}

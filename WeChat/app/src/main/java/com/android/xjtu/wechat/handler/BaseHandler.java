package com.android.xjtu.wechat.handler;

import com.android.xjtu.wechat.service.ChatService;

import org.json.JSONObject;

/**
 * Created by DELL on 2018/11/1.
 */

public abstract class BaseHandler {
    protected ChatService chatService;
    abstract public void handle(JSONObject jsonObject);

    public BaseHandler(ChatService chatService) {
         this.chatService = chatService;
    }
}

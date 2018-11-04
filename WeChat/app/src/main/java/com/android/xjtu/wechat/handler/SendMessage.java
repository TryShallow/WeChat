package com.android.xjtu.wechat.handler;

import android.graphics.Color;

import com.android.xjtu.wechat.activity.ChatActivity;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

/**
 * Created by DELL on 2018/11/2.
 */

public class SendMessage extends BaseHandler {
    public SendMessage(ChatService chatService) {
        super(chatService);
    }

    @Override
    public void handle(JSONObject jsonObject) {
        chatService.sendLocalBroadcast(jsonObject.toString(), Constant.RSP_SEND_MSG);
    }
}

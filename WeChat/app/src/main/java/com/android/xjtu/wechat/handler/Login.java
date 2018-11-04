package com.android.xjtu.wechat.handler;

import android.support.v4.content.LocalBroadcastManager;

import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

/**
 * Created by DELL on 2018/11/1.
 */

public class Login extends BaseHandler {

    public Login(ChatService chatService) {
        super(chatService);
    }

    @Override
    public void handle(JSONObject jsonObject) {
        chatService.sendLocalBroadcast(jsonObject.toString(), Constant.RSP_LOGIN);
    }
}

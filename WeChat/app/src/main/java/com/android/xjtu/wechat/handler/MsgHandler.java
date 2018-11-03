package com.android.xjtu.wechat.handler;

import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2018/11/1.
 */

public class MsgHandler {
    private Map<Integer, BaseHandler> mapHandler = new HashMap<>();
    private ChatService chatService;

    public MsgHandler(ChatService chatService) {
        this.chatService = chatService;
        mapHandler.put(Constant.RSP_GET_SESSION, new GetSession(chatService));
        mapHandler.put(Constant.RSP_LOGIN, new Login(chatService));
        mapHandler.put(Constant.RSP_GET_MSG, new GetMessage(chatService));
        mapHandler.put(Constant.RSP_SEND_MSG, new SendMessage(chatService));
        mapHandler.put(Constant.RSP_GET_FRIEND, new GetFriend(chatService));
    }

    public void handle(JSONObject jsonObject) {
        try {
            int method = jsonObject.getInt(Constant.KEY_METHOD);
            mapHandler.get(method).handle(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

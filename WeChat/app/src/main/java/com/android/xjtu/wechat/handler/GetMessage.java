package com.android.xjtu.wechat.handler;

import com.android.xjtu.wechat.dao.Message;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/11/2.
 */

public class GetMessage extends BaseHandler {

    public GetMessage(ChatService chatService) {
        super(chatService);
    }

    @Override
    public void handle(JSONObject jsonObject) {
        try {
            int count = jsonObject.getInt(Constant.KEY_COUNTER);
            if (count <= 0)
                return ;
            chatService.sendLocalBroadcast(jsonObject.toString());
            List<Message> messages = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_MESSAGES);
            for (int i = 0; i < count; i ++) {
                messages.add(new Message(jsonArray.getJSONObject(i)));
            }
            chatService.storeMessages(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

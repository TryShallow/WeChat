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
            List<Message> messages = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_MESSAGES);
            long maxId = 0;
            for (int i = 0; i < count; i ++) {
                Message message = new Message(jsonArray.getJSONObject(i));
                messages.add(message);
                if (maxId < message.getId()) {
                    maxId = message.getId();
                }
            }
            chatService.getCacheInfo().setMaxMessageId(maxId);
            chatService.storeMessages(messages);
            chatService.sendLocalBroadcast(jsonObject.toString(), Constant.RSP_GET_MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

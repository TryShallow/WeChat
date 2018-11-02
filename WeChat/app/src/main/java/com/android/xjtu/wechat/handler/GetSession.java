package com.android.xjtu.wechat.handler;

import com.android.xjtu.wechat.dao.Session;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/11/1.
 */

public class GetSession extends BaseHandler {
    public GetSession(ChatService chatService) {
        super(chatService);
    }

    @Override
    public void handle(JSONObject jsonObject) {
        try {
            int count = jsonObject.getInt(Constant.KEY_COUNTER);
            if (count <= 0)
                return ;
            chatService.sendLocalBroadcast(jsonObject.toString());
            List<Session> sessions = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_SESSIONS);
            for (int i = 0; i < count; i ++) {
                sessions.add(new Session(jsonArray.getJSONObject(i)));
            }
            chatService.storeSessions(sessions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.android.xjtu.wechat.handler;

import android.util.Log;

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
            Log.i("session", "server reture session count " + count);
            if (count <= 0) {
                return ;
            }
            List<Session> sessions = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_SESSIONS);
            for (int i = 0; i < count; i ++) {
                sessions.add(new Session(jsonArray.getJSONObject(i)));
            }
            chatService.storeSessions(sessions);
            chatService.sendLocalBroadcast(jsonObject.toString(), Constant.RSP_GET_SESSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

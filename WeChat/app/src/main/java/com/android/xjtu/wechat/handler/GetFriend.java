package com.android.xjtu.wechat.handler;

import com.android.xjtu.wechat.dao.Friend;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/11/3.
 */

public class GetFriend extends BaseHandler {
    public GetFriend(ChatService chatService) {
        super(chatService);
    }
    @Override
    public void handle(JSONObject jsonObject) {
        try {
            int count = jsonObject.getInt(Constant.KEY_COUNTER);
            if (count <= 0)
                return ;
            chatService.sendLocalBroadcast(jsonObject.toString());
            List<Friend> friends = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_FRIENDS);
            for (int i = 0; i < count; i ++) {
                friends.add(new Friend(jsonArray.getJSONObject(i)));
            }
            chatService.storeFriends(friends);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

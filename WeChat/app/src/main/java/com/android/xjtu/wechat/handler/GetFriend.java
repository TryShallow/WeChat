package com.android.xjtu.wechat.handler;

import android.util.Log;

import com.android.xjtu.wechat.activity.GlobalValue;
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
            Log.i("contact", "receive count " + count);
            if (count > 0) {
                long maxId = 0;
                Log.i("contact", "receive " + count + " friends");
                List<Friend> friends = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_FRIENDS);
                for (int i = 0; i < count; i++) {
                    Friend friend = new Friend(jsonArray.getJSONObject(i));
                    friends.add(friend);
                    if (friend.getId() > maxId) {
                        maxId = friend.getId();
                    }
                }
                chatService.storeFriends(friends);
                ((GlobalValue) chatService.getApplication()).getChatService().getCacheInfo().setMaxFriendId(maxId);
            }
            chatService.sendLocalBroadcast(jsonObject.toString(), Constant.RSP_GET_FRIEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

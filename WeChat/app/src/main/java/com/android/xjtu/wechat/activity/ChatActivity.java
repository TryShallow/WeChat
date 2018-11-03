package com.android.xjtu.wechat.activity;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.receiver.ChatReceiver;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity {

    private ChatService chatService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatService = ((GlobalValue) getApplication()).getChatService();
        registerChatReceiver();
    }

    public void sendMsg(View view) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_SEND_MSG);
            jsonObject.put(Constant.KEY_USER_ID, 1);
            jsonObject.put(Constant.KEY_USER_ID_TO, 3);
            jsonObject.put(Constant.KEY_MESSAGE_TYPE, 0);
            jsonObject.put(Constant.KEY_MESSAGE_CONTENT, "hello send");
            chatService.execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerChatReceiver() {
        ChatReceiver chatReceiver = new ChatReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constant.LOCAL_BROADCAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(chatReceiver, intentFilter);
    }

    public void getMessage() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_MSG);
            jsonObject.put(Constant.KEY_USER_ID, 1);
            jsonObject.put(Constant.KEY_MESSAGE_ID, 0);
            chatService.execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

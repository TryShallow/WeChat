package com.android.xjtu.wechat.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.dao.Session;
import com.android.xjtu.wechat.receiver.SessionReceiver;
import com.android.xjtu.wechat.service.ChatBinder;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

import java.util.List;

public class SessionActivity extends AppCompatActivity {

    private ChatService chatService;
    private SessionReceiver sessionReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        chatService = ((GlobalValue)getApplication()).getChatService();
        registerSessionReceiver();
        updateSessionList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_SESSION);
            jsonObject.put(Constant.KEY_USER_ID, chatService.getCacheInfo().getUserId());
            jsonObject.put(Constant.KEY_SESSION_ID, chatService.getCacheInfo().getMaxSessionId());
            chatService.execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    private void bindChatService() {
        Intent intent = new Intent(this, ChatService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                chatService = ((ChatBinder) iBinder).getChatService();
                updateSessionList();
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        }, BIND_AUTO_CREATE);
    }

    public void registerSessionReceiver() {
        sessionReceiver = new SessionReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constant.BROADCAST_DATA);
        LocalBroadcastManager.getInstance(this).registerReceiver(sessionReceiver, intentFilter);
    }

    public void updateSessionList() {
        List<Session> sessions = chatService.getLocalSessions();
        Log.i("session activity", "" + sessions.size());
        for (Session session : sessions) {
            Log.i("session activity", session.toString());//"" + session.getUserOwn() + ": " + session.getLastMsgContent());
        }
        setMaxSessionId(sessions);
    }

    public void updateSession(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_SESSION);
            jsonObject.put(Constant.KEY_USER_ID, chatService.getCacheInfo().getUserId());
            jsonObject.put(Constant.KEY_SESSION_ID, 1);//chatService.getCacheInfo().getMaxSessionId());
            chatService.execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMaxSessionId(List<Session> sessions) {
        if (sessions.size() <= 0)
            return;
        int maxId = (int) sessions.get(0).getId();
        for (int i = 1; i < sessions.size(); i ++) {
            int curId = (int) sessions.get(i).getId();
            if (curId > maxId) {
                maxId = curId;
            }
        }
        chatService.getCacheInfo().setMaxSessionId(maxId);
    }

    public void enterChat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}

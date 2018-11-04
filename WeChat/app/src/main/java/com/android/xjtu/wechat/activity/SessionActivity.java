package com.android.xjtu.wechat.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.adapter.Session;
import com.android.xjtu.wechat.adapter.SessionAdapter;
import com.android.xjtu.wechat.receiver.SessionReceiver;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SessionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GlobalValue globalValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);


        registerSessionReceiver();

        globalValue = (GlobalValue) getApplication();
        recyclerView = (RecyclerView)findViewById(R.id.session_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    public void initSession(){
//        for(int i=0;i<10;i++){
//            Session session = new Session("name"+i,"lastMessage"+i);
//            sessionList.add(session);
//        }
        globalValue = ((GlobalValue) getApplication());
        globalValue.setSessionActivity(this);
        List<com.android.xjtu.wechat.dao.Session> sessions = globalValue.getChatService().getLocalSessions();
        if (sessions.size() <= 0)
            return ;
        globalValue.getChatService().getCacheInfo().setMaxSessionId(sessions.get(0).getId());
        List<Session> sessionList = new ArrayList<>();
        for (com.android.xjtu.wechat.dao.Session session : sessions) {
            sessionList.add(new Session("" + session.getUserOther(), session.getLastMsgContent(), session.getUserOther()));
        }
        SessionAdapter sessionAdapter = new SessionAdapter(this, sessionList);
        recyclerView.setAdapter(sessionAdapter);
    }

    public void registerSessionReceiver() {
        SessionReceiver sessionReceiver = new SessionReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constant.LOCAL_BROADCAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(sessionReceiver, intentFilter);
    }

    public void updateSession() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_SESSION);
            jsonObject.put(Constant.KEY_USER_ID, globalValue.getChatService().getCacheInfo().getUserId());
            jsonObject.put(Constant.KEY_SESSION_ID, globalValue.getChatService().getCacheInfo().getMaxSessionId());
            globalValue.getChatService().execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSessionAdapter(List<Session> sessions) {
        SessionAdapter sessionAdapter = new SessionAdapter(this, sessions);
        recyclerView.setAdapter(sessionAdapter);
    }
}
//
//public class SessionActivity extends AppCompatActivity {
//
//    private ChatService chatService;
//    private SessionReceiver sessionReceiver;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_session);
//
////        chatService = ((GlobalValue)getApplication()).getChatService();
////        registerSessionReceiver();
//        //updateSessionList();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        /*
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_SESSION);
//            jsonObject.put(Constant.KEY_USER_ID, chatService.getCacheInfo().getUserId());
//            jsonObject.put(Constant.KEY_SESSION_ID, chatService.getCacheInfo().getMaxSessionId());
//            chatService.execute(jsonObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        */
//    }
//
//    private void bindChatService() {
//        Intent intent = new Intent(this, ChatService.class);
//        bindService(intent, new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//                chatService = ((ChatBinder) iBinder).getChatService();
//                updateSessionList();
//            }
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//            }
//        }, BIND_AUTO_CREATE);
//    }
//
//    public void registerSessionReceiver() {
//        sessionReceiver = new SessionReceiver(this);
//        IntentFilter intentFilter = new IntentFilter(Constant.LOCAL_BROADCAST);
//        LocalBroadcastManager.getInstance(this).registerReceiver(sessionReceiver, intentFilter);
//    }
//
//    public void updateSessionList() {
//        /*List<Session> sessions = chatService.getLocalSessions();
//        Log.i("session activity", "" + sessions.size());
//        for (Session session : sessions) {
//            Log.i("session activity", session.toString());//"" + session.getUserOwn() + ": " + session.getLastMsgContent());
//        }
//        setMaxSessionId(sessions);*/
//    }
//
//    public void updateSession(View view) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_SESSION);
//            jsonObject.put(Constant.KEY_USER_ID, chatService.getCacheInfo().getUserId());
//            jsonObject.put(Constant.KEY_SESSION_ID, 1);//chatService.getCacheInfo().getMaxSessionId());
//            chatService.execute(jsonObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setMaxSessionId(List<Session> sessions) {
//        if (sessions.size() <= 0)
//            return;
//        int maxId = (int) sessions.get(0).getId();
//        for (int i = 1; i < sessions.size(); i ++) {
//            int curId = (int) sessions.get(i).getId();
//            if (curId > maxId) {
//                maxId = curId;
//            }
//        }
//        chatService.getCacheInfo().setMaxSessionId(maxId);
//    }
//
//    public void enterChat(View view) {
//        Intent intent = new Intent(this, ChatActivity.class);
//        startActivity(intent);
//    }
//
//    public void getFriend(View view) {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_FRIEND);
//            jsonObject.put(Constant.KEY_USER_ID, 1);
//            jsonObject.put(Constant.KEY_FRIEND_ID, 0);
//            chatService.execute(jsonObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

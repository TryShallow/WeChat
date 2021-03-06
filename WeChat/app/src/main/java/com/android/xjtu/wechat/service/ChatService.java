package com.android.xjtu.wechat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.android.xjtu.wechat.dao.DbHelper;
import com.android.xjtu.wechat.dao.Friend;
import com.android.xjtu.wechat.dao.Message;
import com.android.xjtu.wechat.dao.Session;

import org.json.JSONObject;

import java.util.List;
import java.util.zip.InflaterInputStream;

public class ChatService extends Service {

    private ChatClient chatClient;
    private DbHelper dbHelper;
    private CacheInfo cacheInfo;

    public ChatService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cacheInfo = new CacheInfo();
        dbHelper = new DbHelper();
        dbHelper.initDb(this);
        chatClient = new ChatClient(Constant.SERVER_ADDR, Constant.SERVER_PORT, this);
        chatClient.connect();
        //chatClient.run();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new ChatBinder(this);
    }

    public void sendLocalBroadcast(String content, int method) {
        Intent intent = new Intent(Constant.LOCAL_BROADCAST);
        intent.putExtra(Constant.BROADCAST_DATA, content);
        intent.putExtra(Constant.KEY_METHOD, method);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    public void execute(JSONObject jsonObject) {
        chatClient.sendMessage(jsonObject.toString());
    }

    public CacheInfo getCacheInfo() {
        return cacheInfo;
    }

    public List<Session> getLocalSessions() {
        List<Session> sessions = dbHelper.getSessions(cacheInfo.getUserId());
        return sessions;
    }

    public List<Friend> getLocalFriends() {
        return dbHelper.getFriends(cacheInfo.getUserId());
    }

    public List<Message> getLocalMessages(long userId, long userIdTo) {
        return dbHelper.getMessages(userId, userIdTo);
    }

    public void storeSessions(List<Session> sessions) {
        dbHelper.storeSessions(sessions);
    }

    public void storeMessages(List<Message> messages) {
        dbHelper.storeMessages(messages);
    }

    public void storeFriends(List<Friend> friends) {
        dbHelper.storeFriends(friends);
    }
}

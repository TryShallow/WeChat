package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.xjtu.wechat.activity.SessionActivity;
import com.android.xjtu.wechat.adapter.Session;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/11/2.
 */

public class SessionReceiver extends BroadcastReceiver {
    private SessionActivity sessionActivity;

    public SessionReceiver(SessionActivity sessionActivity) {
        super();
        this.sessionActivity = sessionActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(Constant.BROADCAST_DATA);
        int method = intent.getIntExtra(Constant.KEY_METHOD, Constant.DEFAULT);
        if (method == Constant.APP_INIT_CHAT_SERVICE) {
            sessionActivity.initSession();
            sessionActivity.updateSession();
        } else if (method == Constant.RSP_GET_MSG){// || method == Constant.RSP_SEND_MSG){
            sessionActivity.updateSession();
        } else if (method == Constant.RSP_GET_SESSION) {
            try {
                Log.i("session", data);
                JSONObject jsonObject = new JSONObject(data);
                //int count = jsonObject.getInt(Constant.KEY_COUNTER);
//                if (count <= 0)
//                    return ;
//                JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_SESSIONS);
//                List<Session> sessions = new ArrayList<>();
//                for (int i = 0; i < count; i ++) {
//                    com.android.xjtu.wechat.dao.Session session = new com.android.xjtu.wechat.dao.Session(jsonArray.getJSONObject(i));
//                    sessions.add(new Session("" + session.getUserOther(), session.getLastMsgContent(), session.getUserOther()));
//                }
//                sessionActivity.updateSessionAdapter(sessions);
                sessionActivity.initSession();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        String data = intent.getStringExtra(Constant.BROADCAST_DATA);
//        Log.i("session", data);
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            int method = jsonObject.getInt(Constant.KEY_METHOD);
//            if (method == Constant.RSP_GET_SESSION) {
//                Log.i("session receiver", "" + method + ": " + jsonObject.getInt(Constant.KEY_COUNTER));
//            } else if (method == Constant.RSP_GET_FRIEND) {
//                Log.i("session receiver", "" + method + ": " + jsonObject.getInt(Constant.KEY_COUNTER));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        sessionActivity.updateSessionList();
        //Log.i("session receiver", intent.getStringExtra(Constant.BROADCAST_DATA));
    }
}

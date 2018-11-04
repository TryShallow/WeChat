package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.xjtu.wechat.activity.ChatActivity;
import com.android.xjtu.wechat.activity.GlobalValue;
import com.android.xjtu.wechat.adapter.Msg;
import com.android.xjtu.wechat.dao.Message;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/11/2.
 */

public class ChatReceiver extends BroadcastReceiver {
    private ChatActivity chatActivity;
    public ChatReceiver(ChatActivity chatActivity) {
        super();
        this.chatActivity = chatActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(Constant.BROADCAST_DATA);
        int method = intent.getIntExtra(Constant.KEY_METHOD, Constant.DEFAULT);
        Log.i("chat", data);
        if (method == Constant.RSP_GET_MSG) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                int count = jsonObject.getInt(Constant.KEY_COUNTER);
//                if (count <= 0)
//                    return;
//                JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_MESSAGES);
//                List<Msg> msgs = new ArrayList<>();
//                for (int i = 0; i < count; i ++) {
//                    Message message = new Message(jsonArray.getJSONObject(i));
//                    int msgType = chatActivity.getUserIdTo() == message.getUserRecv() ? Msg.TYPE_SENT : Msg.TYPE_RECEIVED;
//                    msgs.add(new Msg(msgType, message.getContent()));
//                }
                chatActivity.initMsgs();
//                chatActivity.updateMsg(msgs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (method == Constant.RSP_SEND_MSG) {
            chatActivity.getMessages();
            //chatActivity.initMsgs();
        }
        /*
        try {
            JSONObject jsonObject = new JSONObject(data);
            int method = jsonObject.getInt(Constant.KEY_METHOD);
            //Log.i("chat receiver", "" + method);
            if (method == Constant.RSP_GET_SESSION) {
                JSONObject getMessage = new JSONObject();
                getMessage.put(Constant.KEY_USER_ID, 1);
                getMessage.put(Constant.KEY_MESSAGE_ID, 0);
                getMessage.put(Constant.KEY_METHOD, Constant.CLT_GET_MSG);
                ((GlobalValue)chatActivity.getApplication()).getChatService().execute(getMessage);
            } else if (method == Constant.RSP_GET_MSG) {
                //
            } else if (method == Constant.RSP_SEND_MSG) {
                //
                //chatActivity.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}

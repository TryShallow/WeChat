package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.xjtu.wechat.activity.ChatActivity;
import com.android.xjtu.wechat.activity.GlobalValue;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

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
        try {
            JSONObject jsonObject = new JSONObject(data);
            int method = jsonObject.getInt(Constant.KEY_METHOD);
            Log.i("chat receiver", "" + method);
            if (method == Constant.RSP_GET_SESSION) {
                JSONObject getMessage = new JSONObject();
                getMessage.put(Constant.KEY_USER_ID, 1);
                getMessage.put(Constant.KEY_MESSAGE_ID, 0);
                getMessage.put(Constant.KEY_METHOD, Constant.CLT_GET_MSG);
                ((GlobalValue)chatActivity.getApplication()).getChatService().execute(getMessage);
            } else if (method == Constant.RSP_GET_MSG) {
                Log.i("chat receiver", "" + jsonObject.getInt(Constant.KEY_COUNTER));
            } else if (method == Constant.RSP_SEND_MSG) {
                Log.i("chat receiver", "" + jsonObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.xjtu.wechat.activity.LoginActivity;
import com.android.xjtu.wechat.activity.SessionActivity;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

/**
 * Created by DELL on 2018/10/31.
 */

public class LoginReceiver extends BroadcastReceiver {
    private LoginActivity loginActivity;
    public LoginReceiver(LoginActivity loginActivity) {
        super();
        this.loginActivity = loginActivity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        //the message is stored in bundle
        //write your own code to handle all message for service and update UI
        String data = intent.getStringExtra(Constant.BROADCAST_DATA);
        Log.i("login", data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.getInt(Constant.KEY_METHOD) != Constant.RSP_LOGIN)
                return ;
            //Toast.makeText(loginActivity, data, Toast.LENGTH_LONG).show();
            Log.i("login", data);
            String status = jsonObject.getString(Constant.KEY_STATUS);
            if (status.equals(Constant.VALUE_SUCCESS)) {
                loginActivity.getChatService().getCacheInfo().setUserId(jsonObject.getInt(Constant.KEY_USER_ID));
                Intent sessionIntent = new Intent(loginActivity, SessionActivity.class);
                loginActivity.startActivity(sessionIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.android.xjtu.wechat.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.xjtu.wechat.dao.DbHelper;
import com.android.xjtu.wechat.receiver.LoginReceiver;
import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.service.ChatBinder;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private ChatService chatService;    //convenient for sending message to service
    private LoginReceiver loginReceiver;    //broadcast receiver
    private EditText edtAccount;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtAccount = (EditText) findViewById(R.id.edt_account);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        //registe a local broadcast receiver for login activity, for listening the message of back service
        registerReceiver();
        //run service
        startAndBindService();
    }

    public void signIn(View view) {
        String account = edtAccount.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        /*if ("".equals(account) || "".equals(password) ||
                !account.equals(testAccount[0]) || !password.equals(testAccount[1])) {
            Toast.makeText(this, "Invalid Account", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Login...", Toast.LENGTH_LONG).show();
        }*/
        if ("".equals(account) || "".equals(password)) {
            Toast.makeText(this, "Invalid Account", Toast.LENGTH_LONG).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_LOGIN);
            jsonObject.put(Constant.KEY_ACCOUNT, account);
            jsonObject.put(Constant.KEY_PASSWORD, password);
            chatService.execute(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startAndBindService() {
        //start service
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, ChatService.class);
        startService(intent);

        //bind service
        Intent intentBind = new Intent(LoginActivity.this, ChatService.class);
        bindService(intentBind, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //only to initialize chatService
                chatService = ((ChatBinder)iBinder).getChatService();
                ((GlobalValue)getApplication()).setChatService(chatService);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, BIND_AUTO_CREATE);
    }

    private void registerReceiver() {
        loginReceiver = new LoginReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constant.LOCAL_BROADCAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(loginReceiver);
    }

    //test broadcast
    public void sendBroadcast(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("text", "test broadcast");
            chatService.sendLocalBroadcast(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void testDb(View view) {
        //dbHelper.testMessage();
    }

    public void setChatService() {

    }

    public ChatService getChatService() {
        return chatService;
    }
}

package com.android.xjtu.wechat.activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.adapter.Msg;
import com.android.xjtu.wechat.adapter.MsgAdapter;
import com.android.xjtu.wechat.dao.Message;
import com.android.xjtu.wechat.receiver.ChatReceiver;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText inputText;
    private long userIdTo;

    private GlobalValue globalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name);
        userIdTo = intent.getLongExtra(Constant.KEY_FRIEND_ID, -1);

        globalValue = (GlobalValue) getApplication();
        registerChatReceiver();
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if(NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                }else{
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initViews() {
        inputText = (EditText) findViewById(R.id.input_text);
        recyclerView = (RecyclerView) findViewById(R.id.msg_recycleView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        initMsgs();
    }

    public void sendMsg(View view) {
        String content = inputText.getText().toString();
        if (!"".equals(content)) {
//            Msg msg = new Msg(Msg.TYPE_SENT, content);
//            msgList.add(msg);
//            msgAdapter.notifyItemInserted(msgList.size() - 1);
//            recyclerView.scrollToPosition(msgList.size() - 1);
//            inputText.setText("");
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constant.KEY_METHOD, Constant.CLT_SEND_MSG);
                jsonObject.put(Constant.KEY_USER_ID, globalValue.getChatService().getCacheInfo().getUserId());
                jsonObject.put(Constant.KEY_USER_ID_TO, userIdTo);
                jsonObject.put(Constant.KEY_MESSAGE_TYPE, Constant.DEFAULT_MESSAGE_TYPE);
                jsonObject.put(Constant.KEY_MESSAGE_CONTENT, content);
                globalValue.getChatService().execute(jsonObject);
                inputText.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerChatReceiver() {
        ChatReceiver chatReceiver = new ChatReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constant.LOCAL_BROADCAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(chatReceiver, intentFilter);
    }

    public void initMsgs() {
        List<Msg> msgList = new ArrayList<Msg>();
        List<Message> messages = globalValue.getChatService().getLocalMessages(globalValue.getChatService().getCacheInfo().getUserId(), userIdTo);
        long maxId = 0;
        for (Message message : messages) {
            int msgType = userIdTo == message.getUserRecv() ? Msg.TYPE_SENT : Msg.TYPE_RECEIVED;
            msgList.add(new Msg(msgType, message.getContent()));
            if (maxId < message.getId()) {
                maxId = message.getId();
            }
        }
        globalValue.getChatService().getCacheInfo().setMaxMessageId(maxId);
        MsgAdapter msgAdapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(msgAdapter);
        recyclerView.scrollToPosition(msgList.size() - 1);

        getMessages();
    }

    public void updateMsg(List<Msg> msgs) {
        MsgAdapter msgAdapter = new MsgAdapter(msgs);
        recyclerView.setAdapter(msgAdapter);
        recyclerView.scrollToPosition(msgs.size() - 1);
    }

//    public void addMsg(Msg msg) {
//        msgList.add(msg);
//        msgAdapter.notifyItemInserted(msgList.size() - 1);
//        recyclerView.scrollToPosition(msgList.size() - 1);
//    }

    public void getMessages() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_MSG);
            jsonObject.put(Constant.KEY_USER_ID, globalValue.getChatService().getCacheInfo().getUserId());
            jsonObject.put(Constant.KEY_MESSAGE_ID, globalValue.getChatService().getCacheInfo().getMaxMessageId());
            globalValue.getChatService().execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getUserIdTo() {
        return userIdTo;
    }
}

/*public class ChatActivity extends AppCompatActivity {

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
}*/

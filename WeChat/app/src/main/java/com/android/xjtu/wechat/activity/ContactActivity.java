package com.android.xjtu.wechat.activity;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.adapter.ContactAdapter;
import com.android.xjtu.wechat.adapter.DividerItemDecoration;
import com.android.xjtu.wechat.adapter.LetterView;
import com.android.xjtu.wechat.dao.Friend;
import com.android.xjtu.wechat.receiver.ContactReceiver;
import com.android.xjtu.wechat.service.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private RecyclerView contactList;
    private String[] contactNames;
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private ContactAdapter adapter;

    private GlobalValue globalValue;
    private ContactReceiver contactReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("contact activity", "on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        globalValue = (GlobalValue) getApplication();
        globalValue.setContactActivity(this);
        registerContactReceiver();
        //requestFriends();

        /*contactNames = new String[] {"张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123", "天山童姥", "任我行", "于万亭", "陈家洛",
                "韦小宝", "方佬","方展","穆人清", "陈圆圆", "郭芙", "郭襄", "穆念慈", "东方不败", "梅超风", "林平之", "林远图",
                "灭绝师太", "段誉", "鸠摩智"};*/

        contactNames = new String[] {};

        contactList = (RecyclerView) findViewById(R.id.contact_list);
        letterView = (LetterView) findViewById(R.id.letter_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ContactAdapter(this, contactNames);

        contactList.setLayoutManager(layoutManager);
        contactList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        contactList.setAdapter(adapter);

        letterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(character), 0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
    }

    public void requestFriends() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constant.KEY_METHOD, Constant.CLT_GET_FRIEND);
            jsonObject.put(Constant.KEY_USER_ID, globalValue.getChatService().getCacheInfo().getUserId());
            jsonObject.put(Constant.KEY_FRIEND_ID, globalValue.getChatService().getCacheInfo().getMaxFriendId());
            globalValue.getChatService().execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLocalFriends() {
        List<Friend> friends = globalValue.getChatService().getLocalFriends();
        List<String> friendNames = new ArrayList<>();
        for (Friend friend : friends) {
            Log.i("contact", friend.toString());
            friendNames.add(friend.getUserFriend() + "_" + friend.getUserFriend());
        }
        adapter.updateContactName(friendNames.toArray(new String[friendNames.size()]));
        adapter.notifyDataSetChanged();
    }

    public void registerContactReceiver() {
        contactReceiver = new ContactReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constant.LOCAL_BROADCAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(contactReceiver, intentFilter);
    }
}

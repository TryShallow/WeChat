package com.android.xjtu.wechat.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.adapter.MyPagerAdapter;
import com.android.xjtu.wechat.dao.Session;
import com.android.xjtu.wechat.receiver.SlideReceiver;
import com.android.xjtu.wechat.service.ChatBinder;
import com.android.xjtu.wechat.service.ChatService;
import com.android.xjtu.wechat.service.Constant;

public class SlideActivity extends Activity {
    private LocalActivityManager manager;
    private GlobalValue globalValue;

    private View wechatView, contactView, findView, meView;
    private ViewPager viewPager;
    private List<View> viewList; // 布局集合
    private List<ImageView> iconList; // 图标集合
    private List<TextView> textList; // 文字集合
    private LayoutInflater mInflater;

    private ImageView wechatIconSelect;
    private ImageView wechatIconNormal;
    private ImageView contactIconSelect;
    private ImageView contactIconNormal;
    private ImageView findIconSelect;
    private ImageView findIconNormal;
    private ImageView meIconSelect;
    private ImageView meIconNormal;

    private TextView wechatTextSelect;
    private TextView wechatTextNormal;
    private TextView contactTextSelect;
    private TextView contactTextNormal;
    private TextView findTextSelect;
    private TextView findTextNormal;
    private TextView meTextSelect;
    private TextView meTextNormal;

    private final int WECHAT_INDEX = 0; // 微信菜单索引
    private final int CONTACT_INDEX = 1; // 联系人菜单索引
    private final int FIND_INDEX = 2; // 发现菜单索引
    private final int ME_INDEX = 3; // 我菜单索引
    private int curIndex; // 当前菜单索引

    private Intent intentWeChat,intentContact,intentFind,intentMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        globalValue = (GlobalValue) getApplication();
        //registe a local broadcast receiver for login activity, for listening the message of back service
        //
        //run service
        startAndBindService();
        registerSlideReceiver();
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);

        manager = new LocalActivityManager(this,true);
        manager.dispatchCreate(savedInstanceState);

    }

    public void registerSlideReceiver() {
        SlideReceiver slideReceiver = new SlideReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constant.LOCAL_BROADCAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(slideReceiver, intentFilter);
    }

    public void initView() {

        mInflater = LayoutInflater.from(this);
        viewList = new ArrayList<View>();
        iconList = new ArrayList<ImageView>();
        textList = new ArrayList<TextView>();
        Log.i("viewpager",textList.size()+"");
        viewPager = (ViewPager) findViewById(R.id.page_view);
        Log.i("viewpager",viewPager.toString()+"");

        wechatIconSelect = (ImageView) findViewById(R.id.wechat_icon_select);
        wechatIconNormal = (ImageView) findViewById(R.id.wechat_icon_normal);
        contactIconSelect = (ImageView) findViewById(R.id.contact_icon_select);
        contactIconNormal = (ImageView) findViewById(R.id.contact_icon_normal);
        findIconSelect = (ImageView) findViewById(R.id.find_icon_select);
        findIconNormal = (ImageView) findViewById(R.id.find_icon_normal);
        meIconSelect = (ImageView) findViewById(R.id.me_icon_select);
        meIconNormal = (ImageView) findViewById(R.id.me_icon_normal);

        wechatTextSelect = (TextView) findViewById(R.id.wechat_text_select);
        wechatTextNormal = (TextView) findViewById(R.id.wechat_text_normal);
        contactTextSelect = (TextView) findViewById(R.id.contact_text_select);
        contactTextNormal = (TextView) findViewById(R.id.contact_text_normal);
        findTextSelect = (TextView) findViewById(R.id.find_text_select);
        findTextNormal = (TextView) findViewById(R.id.find_text_normal);
        meTextSelect = (TextView) findViewById(R.id.me_text_select);
        meTextNormal = (TextView) findViewById(R.id.me_text_normal);

//        wechatView = mInflater.inflate(R.layout.wechat_layout, null);
//        contactView = mInflater.inflate(R.layout.activity_main, null);
//        findView = mInflater.inflate(R.layout.find_layout, null);
//        meView = mInflater.inflate(R.layout.me_layout, null);
        intentWeChat = new Intent(SlideActivity.this,SessionActivity.class);
        View weChat = manager.startActivity("viewID",intentWeChat).getDecorView();
        intentContact = new Intent(SlideActivity.this,ContactActivity.class);
        View contact = manager.startActivity("viewID",intentContact).getDecorView();
        intentFind = new Intent(SlideActivity.this,FriendsCircleActivity.class);
        View find = manager.startActivity("viewID",intentFind).getDecorView();
        intentMe = new Intent(SlideActivity.this,MeActivity.class);
        View me = manager.startActivity("viewID",intentMe).getDecorView();



        viewList.add(weChat);
        viewList.add(contact);
        viewList.add(find);
        viewList.add(me);

        iconList.add(wechatIconNormal);
        iconList.add(wechatIconSelect);
        iconList.add(contactIconNormal);
        iconList.add(contactIconSelect);
        iconList.add(findIconNormal);
        iconList.add(findIconSelect);
        iconList.add(meIconNormal);
        iconList.add(meIconSelect);

        textList.add(wechatTextNormal);
        textList.add(wechatTextSelect);
        textList.add(contactTextNormal);
        textList.add(contactTextSelect);
        textList.add(findTextNormal);
        textList.add(findTextSelect);
        textList.add(meTextNormal);
        textList.add(meTextSelect);

        viewPager.setAdapter(new MyPagerAdapter(viewList));
        viewPager.setOnPageChangeListener(pageListener);

        wechatIconNormal.setAlpha(0f);
        wechatTextNormal.setAlpha(0f);
        contactIconSelect.setAlpha(0f);
        contactTextSelect.setAlpha(0f);
        findIconSelect.setAlpha(0f);
        findTextSelect.setAlpha(0f);
        meIconSelect.setAlpha(0f);
        meTextSelect.setAlpha(0f);
    }

    OnPageChangeListener pageListener = new OnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {
            curIndex = index;
            if (curIndex == CONTACT_INDEX) {
                globalValue.getContactActivity().requestFriends();
            } else if (curIndex == WECHAT_INDEX) {
                globalValue.getSessionActivity().updateSession();
            }
        }

        @Override
        public void onPageScrolled(int index, float ratio, int offset) {
            if (ratio > 0) {
                colorChange(index, index + 1, ratio);
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /**
     *
     * @param srcIndex 失去焦点的索引
     * @param destIndex 选中的索引
     * @param ratio 透明的比例
     */
    private void colorChange(int srcIndex, int destIndex, float ratio) {
        iconList.get(srcIndex * 2).setAlpha(ratio);
        iconList.get(srcIndex * 2 + 1).setAlpha(1 - ratio);

        iconList.get(destIndex * 2).setAlpha(1 - ratio);
        iconList.get(destIndex * 2 + 1).setAlpha(ratio);

        textList.get(srcIndex * 2).setAlpha(ratio);
        textList.get(srcIndex * 2 + 1).setAlpha(1 - ratio);

        textList.get(destIndex * 2).setAlpha(1 - ratio);
        textList.get(destIndex * 2 + 1).setAlpha(ratio);
    }

    public void wechatClick(View view) {
        if (curIndex != WECHAT_INDEX) {
            colorChange(WECHAT_INDEX, curIndex, 0);
            viewPager.setCurrentItem(WECHAT_INDEX, false);
            globalValue.getSessionActivity().updateSession();
        }
    }

    public void contactClick(View view) {
        if (curIndex != CONTACT_INDEX) {
            colorChange(CONTACT_INDEX, curIndex, 0);
//            Intent intent = new Intent();
//            intent.setClass(this,MainActivity.class);
//            manager.startActivity("",intent).getDecorView();
            viewPager.setCurrentItem(CONTACT_INDEX, false);
            globalValue.getContactActivity().requestFriends();
        }
    }

    public void findClick(View view) {
        if (curIndex != FIND_INDEX) {
            colorChange(FIND_INDEX, curIndex, 0);

            viewPager.setCurrentItem(FIND_INDEX, false);
        }
    }

    public void meClick(View view) {
        if (curIndex != ME_INDEX) {
            colorChange(ME_INDEX, curIndex, 0);

            viewPager.setCurrentItem(ME_INDEX, false);
        }
    }

    private void startAndBindService() {
        //start service
        Intent intent = new Intent();
        intent.setClass(this, ChatService.class);
        startService(intent);

        //bind service
        Intent intentBind = new Intent(this, ChatService.class);
        bindService(intentBind, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //only to initialize chatService
                globalValue.setChatService(((ChatBinder)iBinder).getChatService());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, BIND_AUTO_CREATE);
    }
}

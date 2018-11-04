package com.android.xjtu.wechat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.xjtu.wechat.activity.GlobalValue;
import com.android.xjtu.wechat.activity.SlideActivity;
import com.android.xjtu.wechat.service.Constant;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by DELL on 2018/11/4.
 */

public class SlideReceiver extends BroadcastReceiver {
    private SlideActivity slideActivity;

    public SlideReceiver(SlideActivity slideActivity) {
        this.slideActivity = slideActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(Constant.BROADCAST_DATA);
        int method = intent.getIntExtra(Constant.KEY_METHOD, Constant.DEFAULT);
        if (method == Constant.APP_LOGIN_SUCESS) {
            slideActivity.initView();
            ((GlobalValue) slideActivity.getApplication()).getChatService().sendLocalBroadcast("{\"method\":9000}", Constant.APP_INIT_CHAT_SERVICE);
        }
    }
}

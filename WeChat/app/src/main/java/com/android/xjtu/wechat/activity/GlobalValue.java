package com.android.xjtu.wechat.activity;

import android.app.Application;

import com.android.xjtu.wechat.adapter.Session;
import com.android.xjtu.wechat.service.ChatService;

/**
 * Created by DELL on 2018/11/2.
 */

public class GlobalValue extends Application {
    //public static final int

    private ChatService chatService;
    private ContactActivity contactActivity;
    private SessionActivity sessionActivity;
    private int currentPage;

    public SessionActivity getSessionActivity() {
        return sessionActivity;
    }

    public void setSessionActivity(SessionActivity sessionActivity) {
        this.sessionActivity = sessionActivity;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public ContactActivity getContactActivity() {
        return contactActivity;
    }

    public void setContactActivity(ContactActivity contactActivity) {
        this.contactActivity = contactActivity;
    }

    public ChatService getChatService() {
        return chatService;
    }

    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }
}

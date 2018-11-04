package com.android.xjtu.wechat.adapter;

import java.io.Serializable;

public class Session implements Serializable {
    private String name;
    private String lastMessage;
    private long userOther;

    public long getUserOther() {
        return userOther;
    }

    public void setUserOther(long userOther) {
        this.userOther = userOther;
    }

    public Session(String name, String lastMessage, long userOther) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.userOther = userOther;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}

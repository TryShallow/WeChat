package com.android.xjtu.wechat.service;

/**
 * Created by DELL on 2018/11/2.
 */

public class CacheInfo {
    private int userId = 0;
    private int maxSessionId = 0;
    private int maxMessageId = 0;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMaxSessionId() {
        return maxSessionId;
    }

    public void setMaxSessionId(int maxSessionId) {
        if (maxSessionId <= this.maxSessionId)
            return ;
        this.maxSessionId = maxSessionId;
    }

    public int getMaxMessageId() {
        return maxMessageId;
    }

    public void setMaxMessageId(int maxMessageId) {
        this.maxMessageId = maxMessageId;
    }
}

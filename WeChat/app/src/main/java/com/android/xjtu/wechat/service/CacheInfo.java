package com.android.xjtu.wechat.service;

/**
 * Created by DELL on 2018/11/2.
 */

public class CacheInfo {
    private int userId = 1;
    private long maxSessionId = 0;
    private long maxMessageId = 0;
    private long maxFriendId = 0;

    public long getMaxSessionId() {
        return maxSessionId;
    }

    public void setMaxSessionId(long maxSessionId) {
        if (maxSessionId < this.maxSessionId)
            return ;
        this.maxSessionId = maxSessionId;
    }

    public long getMaxMessageId() {
        return maxMessageId;
    }

    public void setMaxMessageId(long maxMessageId) {
        if (maxMessageId < this.maxMessageId)
            return ;
        this.maxMessageId = maxMessageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getMaxFriendId() {
        return maxFriendId;
    }

    public void setMaxFriendId(long maxFriendId) {
        if (maxFriendId < this.maxFriendId)
            return;
        this.maxFriendId = maxFriendId;
    }
}

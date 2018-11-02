package com.android.xjtu.wechat.dao;

import com.android.xjtu.wechat.service.Constant;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by DELL on 2018/11/2.
 */

@Entity
public class Session {
    @NotNull
    private long id;

    @Property(nameInDb = "user_own")
    private long userOwn;

    @Property(nameInDb = "user_other")
    private long userOther;

    @Property(nameInDb = "send_flag")
    private int sendFlag;

    @Property(nameInDb = "last_msg_id")
    private int lastMsgId;

    @Property(nameInDb = "last_msg_content")
    private String lastMsgContent;

    @Property(nameInDb = "create_time")
    private long createTime;

    @Generated(hash = 1680129488)
    public Session(long id, long userOwn, long userOther, int sendFlag,
            int lastMsgId, String lastMsgContent, long createTime) {
        this.id = id;
        this.userOwn = userOwn;
        this.userOther = userOther;
        this.sendFlag = sendFlag;
        this.lastMsgId = lastMsgId;
        this.lastMsgContent = lastMsgContent;
        this.createTime = createTime;
    }
    public static final String SESSION_ID = "session_id";
    public static final String USER_OWN = "user_own";
    public static final String USER_OTHER = "user_other";
    public static final String SEND_FLAG = "send_flag";
    public static final String LAST_MSG_ID = "last_msg_id";
    public static final String LAST_MSG_CONTENT = "last_msg_content";
    public static final String CREATE_TIME = "create_time";

    public Session(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt(SESSION_ID);
            this.userOwn = jsonObject.getInt(USER_OWN);
            this.userOther = jsonObject.getInt(USER_OTHER);
            this.sendFlag = jsonObject.getInt(SEND_FLAG);
            this.lastMsgId = jsonObject.getInt(LAST_MSG_ID);
            this.lastMsgContent = jsonObject.getString(LAST_MSG_CONTENT);
            Timestamp timestamp = Timestamp.valueOf(jsonObject.getString(CREATE_TIME));
            this.createTime = timestamp.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Generated(hash = 1317889643)
    public Session() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserOwn() {
        return this.userOwn;
    }

    public void setUserOwn(long userOwn) {
        this.userOwn = userOwn;
    }

    public long getUserOther() {
        return this.userOther;
    }

    public void setUserOther(long userOther) {
        this.userOther = userOther;
    }

    public int getSendFlag() {
        return this.sendFlag;
    }

    public void setSendFlag(int sendFlag) {
        this.sendFlag = sendFlag;
    }

    public int getLastMsgId() {
        return this.lastMsgId;
    }

    public void setLastMsgId(int lastMsgId) {
        this.lastMsgId = lastMsgId;
    }

    public String getLastMsgContent() {
        return this.lastMsgContent;
    }

    public void setLastMsgContent(String lastMsgContent) {
        this.lastMsgContent = lastMsgContent;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}

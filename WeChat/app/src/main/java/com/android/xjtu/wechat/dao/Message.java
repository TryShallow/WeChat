package com.android.xjtu.wechat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by DELL on 2018/11/2.
 */

@Entity
public class Message {
    @NotNull
    @Unique
    private Long id;

    @Property(nameInDb = "user_send")
    private Long userSend;

    @Property(nameInDb = "user_recv")
    private Long userRecv;

    @Property(nameInDb = "type")
    private int msgType;

    @Property(nameInDb = "content")
    private String content;

    @Property(nameInDb = "create_time")
    private long createTime;

    @Generated(hash = 910862969)
    public Message(@NotNull Long id, Long userSend, Long userRecv, int msgType,
            String content, long createTime) {
        this.id = id;
        this.userSend = userSend;
        this.userRecv = userRecv;
        this.msgType = msgType;
        this.content = content;
        this.createTime = createTime;
    }
    public static final String MESSAGE_ID = "message_id";
    public static final String USER_SEND = "user_send";
    public static final String USER_RECV = "user_recv";
    public static final String MESSAGE_TYPE = "message_type";
    public static final String MESSAGE_CONTENT = "message_content";
    public static final String CREATE_TIME = "create_time";

    public Message(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getLong(MESSAGE_ID);
            this.userSend = jsonObject.getLong(USER_SEND);
            this.userRecv = jsonObject.getLong(USER_RECV);
            this.msgType = jsonObject.getInt(MESSAGE_TYPE);
            this.content = jsonObject.getString(MESSAGE_CONTENT);
            this.createTime = Timestamp.valueOf(jsonObject.getString(CREATE_TIME)).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Generated(hash = 637306882)
    public Message() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserSend() {
        return this.userSend;
    }

    public void setUserSend(Long userSend) {
        this.userSend = userSend;
    }

    public Long getUserRecv() {
        return this.userRecv;
    }

    public void setUserRecv(Long userRecv) {
        this.userRecv = userRecv;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
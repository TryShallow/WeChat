package com.android.xjtu.wechat.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by DELL on 2018/11/2.
 */

@Entity
public class Friend {
    @NotNull
    private long id;

    @Property(nameInDb = "user_id")
    private long userId;

    @Property(nameInDb = "user_friend")
    private long userFriend;

    @Property(nameInDb = "add_flag")
    private long addFlag;

    @Property(nameInDb = "create_time")
    private long createTime;

    @Generated(hash = 1678482896)
    public Friend(long id, long userId, long userFriend, long addFlag,
            long createTime) {
        this.id = id;
        this.userId = userId;
        this.userFriend = userFriend;
        this.addFlag = addFlag;
        this.createTime = createTime;
    }

    @Generated(hash = 287143722)
    public Friend() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserFriend() {
        return this.userFriend;
    }

    public void setUserFriend(long userFriend) {
        this.userFriend = userFriend;
    }

    public long getAddFlag() {
        return this.addFlag;
    }

    public void setAddFlag(long addFlag) {
        this.addFlag = addFlag;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}

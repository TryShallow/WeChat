package com.android.xjtu.wechat.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.xjtu.wechat.greendao.DaoMaster;
import com.android.xjtu.wechat.greendao.DaoSession;
import com.android.xjtu.wechat.greendao.FriendDao;
import com.android.xjtu.wechat.greendao.MessageDao;
import com.android.xjtu.wechat.greendao.SessionDao;
import com.android.xjtu.wechat.service.Constant;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/11/2.
 */

public class DbHelper {
    private DaoMaster.DevOpenHelper devOpenHelper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public void initDb(Context context) {
        devOpenHelper = new DaoMaster.DevOpenHelper(context, "wechat.db");
        db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void testMessage() {
        MessageDao messageDao = daoSession.getMessageDao();
        Message message = new Message();
        message.setUserSend(1L);
        message.setUserRecv(2L);
        message.setMsgType(0);
        message.setContent("greendao");
        message.setCreateTime(System.currentTimeMillis());
        messageDao.insert(message);

        List<Message> messages = messageDao.loadAll();
        for (Message m : messages) {
            Log.i("db helper", m.getContent());
        }
    }

    public List<Session> getSessions(int userId) {
        SessionDao sessionDao = daoSession.getSessionDao();
        String sql = "select max(id) id, user_own,user_other,send_flag,last_msg_id,last_msg_content,create_time from "
                + sessionDao.getTablename() + " group by user_other having user_own=" + userId +" order by id desc";
        Cursor cursor = db.rawQuery(sql, null);
        List<Session> sessions = new ArrayList<>();
        if (cursor == null) {
            Log.i("session", "db sessions cursor null");
            return sessions;
        }
        while (cursor.moveToNext()) {
            sessions.add(new Session(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6)));
        }
        Log.i("session", "db sessions size " + sessions.size());
        Log.i("session", sessions.get(0).getLastMsgContent());
        return sessions;
    }

    public List<Friend> getFriends(int userId) {
        FriendDao friendDao = daoSession.getFriendDao();
        return friendDao.queryBuilder().where(new WhereCondition.StringCondition("add_flag=22 and user_id=" + userId)).build().list();
    }

    public void storeSessions(List<Session> sessions) {
        SessionDao sessionDao = daoSession.getSessionDao();
        for (Session session : sessions) {
            try {
                sessionDao.insertOrReplace(session);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void storeMessages(List<Message> messages) {
        MessageDao messageDao = daoSession.getMessageDao();
        for (Message message : messages) {
            try {
                messageDao.insertOrReplace(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void storeFriends(List<Friend> friends) {
        FriendDao friendDao = daoSession.getFriendDao();
        for (Friend friend : friends) {
            friendDao.insertOrReplace(friend);
        }
    }

    public void resetFriends() {
        daoSession.getFriendDao().deleteAll();
    }

    public List<Message> getMessages(long userId, long userIdTo) {
        MessageDao messageDao = daoSession.getMessageDao();
        String sql = "select * from " + messageDao.getTablename() + " where user_send in (" +
                userId + "," + userIdTo + ") and user_recv in (" + userId + "," + userIdTo + ") order by id asc";
        Cursor cursor = db.rawQuery(sql, null);
        List<Message> messages = new ArrayList<>();
        if (cursor == null)
            return messages;
        while (cursor.moveToNext()) {
            messages.add(new Message(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2),
                    cursor.getInt(3), cursor.getString(4), cursor.getLong(5)));
        }
        return messages;
    }

    public void resetMessages() {
        daoSession.getMessageDao().deleteAll();
    }
}

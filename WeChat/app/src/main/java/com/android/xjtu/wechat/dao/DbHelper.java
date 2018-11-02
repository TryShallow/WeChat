package com.android.xjtu.wechat.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.xjtu.wechat.greendao.DaoMaster;
import com.android.xjtu.wechat.greendao.DaoSession;
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

    public List<Session> getSessions() {
        SessionDao sessionDao = daoSession.getSessionDao();
        String sql = "select max(id) id, user_own,user_other,send_flag,last_msg_id,last_msg_content,create_time from "
                + sessionDao.getTablename() + " group by user_other";
        Cursor cursor = db.rawQuery(sql, null);
        List<Session> sessions = new ArrayList<>();
        if (cursor == null)
            return sessions;
        while (cursor.moveToNext()) {
            sessions.add(new Session(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6)));
        }
        return sessions;
    }

    public void storeSessions(List<Session> sessions) {
        SessionDao sessionDao = daoSession.getSessionDao();
        for (Session session : sessions) {
            sessionDao.insert(session);
        }
    }

    public void storeMessages(List<Message> messages) {
        MessageDao messageDao = daoSession.getMessageDao();
        for (Message message : messages) {
            messageDao.insert(message);
        }
    }
}

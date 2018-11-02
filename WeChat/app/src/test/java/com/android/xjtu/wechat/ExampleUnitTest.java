package com.android.xjtu.wechat;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.xjtu.wechat.dao.Message;
import com.android.xjtu.wechat.greendao.DaoMaster;
import com.android.xjtu.wechat.greendao.DaoSession;
import com.android.xjtu.wechat.greendao.MessageDao;
import com.android.xjtu.wechat.service.ChatClient;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    @Test
//    public void testNetword() throws Exception {
//        ChatClient chatClient = new ChatClient("127.0.0.1", 8989, null);
//        chatClient.connect();
//        //chatClient.run();
//        chatClient.sendMessage("connect server");
//        Thread.sleep(10000);
//    }
    @Test
    public void testDb() throws Exception {
        DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(null, "wechat.db", null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        MessageDao messageDao = daoSession.getMessageDao();
        Message message = new Message();
        message.setUserSend(1L);
        message.setUserRecv(2L);
        message.setMsgType(0);
        message.setContent("greendao");
        message.setCreateTime(System.currentTimeMillis());
        messageDao.insert(message);
    }
}
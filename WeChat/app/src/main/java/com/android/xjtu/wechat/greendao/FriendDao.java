package com.android.xjtu.wechat.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.android.xjtu.wechat.dao.Friend;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FRIEND".
*/
public class FriendDao extends AbstractDao<Friend, Void> {

    public static final String TABLENAME = "FRIEND";

    /**
     * Properties of entity Friend.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", false, "ID");
        public final static Property UserId = new Property(1, long.class, "userId", false, "user_id");
        public final static Property UserFriend = new Property(2, long.class, "userFriend", false, "user_friend");
        public final static Property AddFlag = new Property(3, long.class, "addFlag", false, "add_flag");
        public final static Property CreateTime = new Property(4, long.class, "createTime", false, "create_time");
    }


    public FriendDao(DaoConfig config) {
        super(config);
    }
    
    public FriendDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FRIEND\" (" + //
                "\"ID\" INTEGER NOT NULL ," + // 0: id
                "\"user_id\" INTEGER NOT NULL ," + // 1: userId
                "\"user_friend\" INTEGER NOT NULL ," + // 2: userFriend
                "\"add_flag\" INTEGER NOT NULL ," + // 3: addFlag
                "\"create_time\" INTEGER NOT NULL );"); // 4: createTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FRIEND\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Friend entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getUserId());
        stmt.bindLong(3, entity.getUserFriend());
        stmt.bindLong(4, entity.getAddFlag());
        stmt.bindLong(5, entity.getCreateTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Friend entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getUserId());
        stmt.bindLong(3, entity.getUserFriend());
        stmt.bindLong(4, entity.getAddFlag());
        stmt.bindLong(5, entity.getCreateTime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public Friend readEntity(Cursor cursor, int offset) {
        Friend entity = new Friend( //
            cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // userId
            cursor.getLong(offset + 2), // userFriend
            cursor.getLong(offset + 3), // addFlag
            cursor.getLong(offset + 4) // createTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Friend entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setUserId(cursor.getLong(offset + 1));
        entity.setUserFriend(cursor.getLong(offset + 2));
        entity.setAddFlag(cursor.getLong(offset + 3));
        entity.setCreateTime(cursor.getLong(offset + 4));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(Friend entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(Friend entity) {
        return null;
    }

    @Override
    public boolean hasKey(Friend entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
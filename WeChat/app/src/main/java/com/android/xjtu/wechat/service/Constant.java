package com.android.xjtu.wechat.service;

/**
 * Created by DELL on 2018/11/1.
 */

public class Constant {
    public static final int DEFAULT = -1;
    public static final int DEFAULT_MESSAGE_TYPE = 0;
    public static final int APP_INIT_CHAT_SERVICE = 9000;
    public static final int APP_LOGIN_SUCESS = 9001;

    public static final int CLT_HEART_BEAT = 1;
    public static final int CLT_LOGIN = 10;
    public static final int CLT_SEND_MSG = 11;
    public static final int CLT_GET_MSG = 12;
    public static final int CLT_GET_SESSION = 13;
    public static final int CLT_GET_FRIEND = 14;

    public static final int RSP_HEART_BEAT = 5;
    public static final int RSP_LOGIN = 50;
    public static final int RSP_SEND_MSG = 51;
    public static final int RSP_GET_MSG = 52;
    public static final int RSP_GET_SESSION = 53;
    public static final int RSP_GET_FRIEND = 54;

    public static final String KEY_METHOD = "method";
    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ERROR_CODE = "error_code";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_ID_TO = "user_id_to";
    public static final String KEY_SESSION_ID = "session_id";
    public static final String KEY_COUNTER = "counter";
    public static final String KEY_SESSIONS = "sessions";
    public static final String KEY_MESSAGE_ID = "message_id";
    public static final String KEY_MESSAGE_TYPE = "message_type";
    public static final String KEY_MESSAGE_CONTENT = "message_content";
    public static final String KEY_FRIEND_ID = "friend_id";
    public static final String KEY_FRIENDS = "friends";
    public static final String KEY_MESSAGES = "messages";

    public static final String VALUE_SUCCESS = "success";
    public static final String VALUE_ERROR = "error";
    public static final int VALUE_ERROR_FORMAT_ERROR = 1001;
    public static final int VALUE_ERROR_IVALID_ACCOUNT = 2001;
    public static final int VALUE_MESSAGE_TYPE_NORMAL = 0;
    public static final int VALUE_MESSAGE_TYPE_ADD_BY_ACCOUNT = 1;

    public static final String DB_HOST = "localhost";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "root";
    public static final String DB_NAME = "wechat";

    public static final String LOCAL_BROADCAST = "com.android.xjtu.wechat.LOCAL_BROADCAST";
    public static final String BROADCAST_DATA = "broadcast_data";

    public static final String SERVER_ADDR = "192.168.10.137";
    public static final int SERVER_PORT = 8989;

}

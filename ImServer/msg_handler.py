# -*- encoding: utf-8 -*-
import json
import const_value
import db_helper
from extended_json import ExtendedJsonEncoder

class MsgHandler:
    def __init__(self):
        self.db = db_helper.DbHelper()
        self.userDict = {}
        self.switch = {
            const_value.DEFAULT: self.default,
            const_value.CLT_LOGIN: self.login,
            const_value.CLT_GET_SESSION: self.getSession,
            const_value.CLT_SEND_MSG: self.sendMessage,
            const_value.CLT_GET_FRIEND: self.getFriend,
            const_value.CLT_GET_MSG: self.getMessage,
        }

    def handle(self, data, conn):
        try:
            msg = json.loads(data, encoding='utf8')
            self.switch[msg.get(const_value.KEY_METHOD, -1)](msg, conn)
        except Exception as e:
            print(e)

    def rspMsg(self, rsp, conn):
        rsp = json.dumps(rsp, cls=ExtendedJsonEncoder) # default=lambda obj: obj.__dict__
        conn.send(bytes(rsp, encoding='utf8'))

    '''
    {"none":0}
    '''
    def default(self, ctx, conn):
        print('decode msg error:', ctx)
        rsp = {}
        rsp[const_value.KEY_METHOD] = const_value.DEFAULT
        rsp[const_value.KEY_STATUS] = const_value.VALUE_ERROR
        rsp[const_value.KEY_ERROR_CODE] = const_value.VALUE_ERROR_FORMAT_ERROR
        self.rspMsg(rsp, conn)
    
    '''
    {"method":10, "account":"abc", "password":"123"}
    {"method":10, "account":"abc", "password":"456"}
    {"method":10, "account":"def", "password":"456"}
    '''
    def login(self, ctx, conn):
        account = ctx.get(const_value.KEY_ACCOUNT, const_value.DEFAULT)
        password = ctx.get(const_value.KEY_PASSWORD, const_value.DEFAULT)
        rsp = {}
        rsp[const_value.KEY_METHOD] = const_value.RSP_LOGIN
        userId = self.db.checkLogin(account, password)
        if -1 != userId:
            rsp[const_value.KEY_USER_ID] = userId
            rsp[const_value.KEY_STATUS] = const_value.VALUE_SUCCESS
            self.userDict[userId] = conn
        else :
            rsp[const_value.KEY_STATUS] = const_value.VALUE_ERROR
            rsp[const_value.KEY_ERROR_CODE] = const_value.VALUE_ERROR_IVALID_ACCOUNT
        self.rspMsg(rsp, conn)

    '''
    {"method":13, "user_id":1, "session_id":0}
    '''
    def getSession(self, ctx, conn):
        userId = ctx.get(const_value.KEY_USER_ID, const_value.DEFAULT)
        sessionId = ctx.get(const_value.KEY_SESSION_ID, const_value.DEFAULT)
        sessions, count = self.db.getSession(userId, sessionId)
        rsp = {}
        rsp[const_value.KEY_METHOD] = const_value.RSP_GET_SESSION
        rsp[const_value.KEY_COUNTER] = count
        rsp[const_value.KEY_SESSIONS] = sessions
        self.rspMsg(rsp, conn)  

    '''
    {"method":11, "user_id":1, "user_id_to":2, "message_type":0, "message_content":"test send msg"}
    {"method":11, "user_id":1, "user_id_to":2, "message_type":1, "message_content":"add friend"}
    {"method":11, "user_id":2, "user_id_to":1, "message_type":1, "message_content":"add friend"}
    '''
    def sendMessage(self, ctx, conn):
        userIdFrom = ctx.get(const_value.KEY_USER_ID, const_value.DEFAULT)
        userIdTo = ctx.get(const_value.KEY_USER_ID_TO, const_value.DEFAULT)
        msgType = ctx.get(const_value.KEY_MESSAGE_TYPE, const_value.DEFAULT)
        msgContent = ctx.get(const_value.KEY_MESSAGE_CONTENT, const_value.DEFAULT)
        lastSend, lastRecv = self.db.saveMessage(userIdFrom, userIdTo, msgType, msgContent)
        rspSend = {}
        rspSend[const_value.KEY_METHOD] = const_value.RSP_SEND_MSG
        rspSend[const_value.KEY_USER_ID] = userIdFrom
        rspSend[const_value.KEY_SESSION_ID] = lastSend
        self.rspMsg(rspSend, conn)
        connRecv = self.userDict.get(userIdTo, const_value.DEFAULT)
        if connRecv == const_value.DEFAULT:
            return
        rspRecv = {}
        rspSend[const_value.KEY_METHOD] = const_value.RSP_SEND_MSG
        rspSend[const_value.KEY_USER_ID] = userIdTo
        rspSend[const_value.KEY_SESSION_ID] = lastRecv
        self.rspMsg(rspRecv, connRecv)

    '''
    {"method":14, "user_id":1, "friend_id":0}
    '''
    def getFriend(self, ctx, conn):
        userId = ctx.get(const_value.KEY_USER_ID, const_value.DEFAULT)
        maxFriendId = ctx.get(const_value.KEY_FRIEND_ID, const_value.DEFAULT)
        friends, count = self.db.getFriend(userId, maxFriendId)
        rsp = {}
        rsp[const_value.KEY_USER_ID] = userId
        rsp[const_value.KEY_COUNTER] = count
        rsp[const_value.KEY_FRIENDS] = friends
        self.rspMsg(rsp, conn)

    '''
    {"method":12, "user_id":1, "message_id":0}
    '''
    def getMessage(self, ctx, conn):
        userId = ctx.get(const_value.KEY_USER_ID, const_value.DEFAULT)
        maxMessageId = ctx.get(const_value.KEY_MESSAGE_ID, const_value.DEFAULT)
        messages, count = self.db.getMessage(userId, maxMessageId)
        rsp = {}
        rsp[const_value.KEY_METHOD] = const_value.RSP_GET_MSG
        rsp[const_value.KEY_USER_ID] = userId
        rsp[const_value.KEY_COUNTER] = count
        rsp[const_value.KEY_MESSAGES] = messages
        self.rspMsg(rsp, conn)

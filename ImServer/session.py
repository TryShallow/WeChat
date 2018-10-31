# -*- encoding: utf-8 -*-

class Session:
    def __init__(self, sessionId, userOwn, userOther, sendFlag, lastMsgId, lastMsgContent, createTime):
        self.sessionId = sessionId
        self.userOwn = userOwn
        self.userOther = userOther
        self.sendFlag = sendFlag
        self.lastMsgId = lastMsgId
        self.lastMsgContent = lastMsgContent
        self.createTime = createTime
    
    def __dict__(self):
        return {
            "session_id": self.sessionId,
            "user_own": self.userOwn,
            "user_other": self.userOther,
            "send_flag": self.sendFlag,
            "last_msg_id": self.lastMsgId,
            "last_msg_content": self.lastMsgContent,
            "create_time": self.createTime.strftime('%Y-%m-%d %H:%M:%S')
        }

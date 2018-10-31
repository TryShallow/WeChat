# -*- encoding: utf-8 -*-

class Message:
    def __init__(self, msgId, userSend, userRecv, msgType, content, createTime):
        self.msgId = msgId
        self.userSend = userSend
        self.userRecv = userRecv
        self.msgType = msgType
        self.content = content
        self.createTime = createTime

    def __dict__(self):
        return {
            "message_id": self.msgId,
            "user_send": self.userSend,
            "user_recv": self.userRecv,
            "message_type": self.msgType,
            "message_content": self.content,
            "create_time": self.createTime.strftime('%Y-%m-%d %H:%M:%S')
        }
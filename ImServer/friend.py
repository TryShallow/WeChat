#-*- encoding: utf-8 -*-

class Friend:
    def __init__(self, fid, userId, userFriend, addFlag, createTime):
        self.id = fid
        self.userId = userId
        self.userFriend = userFriend
        self.addFlag = addFlag
        self.createTime = createTime

    def __dict__(self):
        return {
            "id": self.id,
            "user_id": self.userId,
            "user_friend": self.userFriend,
            "add_flag": self.addFlag,
            "create_time": self.createTime.strftime('%Y-%m-%d %H:%M:%S')
        }
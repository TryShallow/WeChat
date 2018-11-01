# -*- encoding:utf-8 -*-
import pymysql
import const_value
from session import Session
from friend import Friend
from message import Message

class DbHelper:
    def __init__(self):
        self.db = pymysql.connect(
            const_value.DB_HOST,
            const_value.DB_USER,
            const_value.DB_PASS,
            const_value.DB_NAME
        )
    
    def checkLogin(self, account, password):
        sql = "select id,password from user where account='%s'" %(account)
        cursor = self.db.cursor()
        cursor.execute(sql)
        result = cursor.fetchone()
        if result != None and result[1] == password:
            return result[0]
        return -1

    def getSession(self, userId, sessionId):
        sql = "select * from `session` where user_own=%d and id > %d order by id desc" %(userId, sessionId)
        with self.db.cursor() as cursor:
            cursor.execute(sql)
            results = cursor.fetchall()
            count = 0
            sessions = []
            print(results)
            for res in results:
                sessions.append(Session(res[0], res[1], res[2], res[3], res[4], res[5], res[6]))
                count += 1
                print(sessions)
            return sessions, count

    def saveMessage(self, userSend, userRecv, msgType, msgContent):
        sqlMsg = "insert into message(user_send,user_recv,type,content) values(%d,%d,%d,'%s')" %(int(userSend), int(userRecv), int(msgType), msgContent)
        sqlSession = "insert into `session`(user_own,user_other,send_flag,last_msg_id,last_msg_content) values(%d,%d,%d,%d,'%s')"
        with self.db.cursor() as cursor:
            cursor.execute(sqlMsg)
            lastId = cursor.lastrowid
            sqlSend = sqlSession % (userSend, userRecv, 0, lastId, msgContent)
            cursor.execute(sqlSend)
            lastSessionSend = cursor.lastrowid
            sqlRecv = sqlSession %(userRecv, userSend, 1, lastId, msgContent)
            cursor.execute(sqlRecv)
            lastSessionRecv = cursor.lastrowid
            if msgType == const_value.VALUE_MESSAGE_TYPE_ADD_BY_ACCOUNT:
                sqlSelect = "select add_flag from friend where user_id=%d" %(userSend)
                cursor.execute(sqlSelect)
                res = cursor.fetchone()
                if res == None:
                    sqlAdd = "insert into friend(user_id,user_friend,add_flag) values(%d,%d,%d)"
                    sqlAddSend = sqlAdd % (userSend, userRecv, 12)
                    sqlAddRecv = sqlAdd % (userRecv, userSend, 21)
                    cursor.execute(sqlAddSend)
                    cursor.execute(sqlAddRecv) 
                elif res[0] == 21:
                    sqlUpdate = "update friend set add_flag=22 where user_id=%d or user_id=%d" %(userSend, userRecv)
                    cursor.execute(sqlUpdate)
            self.db.commit()
            return lastSessionSend, lastSessionRecv

    def getFriend(self, userId, maxFriendId):
        sql = "select * from `friend` where user_id=%d and id>%d and add_flag=22 order by id desc" %(userId, maxFriendId)
        with self.db.cursor() as cursor:
            cursor.execute(sql)
            results = cursor.fetchall()
            count = 0
            friends = []
            for res in results:
                friends.append(Friend(res[0], res[1], res[2], res[3], res[4]))
                count += 1
            return friends, count

    def getMessage(self, userId, maxMessageId):
        sql = "select * from `message` where (user_send=%d or user_recv=%d) and id>%d order by id desc" %(userId, userId, maxMessageId)
        with self.db.cursor() as cursor:
            cursor.execute(sql)
            results = cursor.fetchall()
            count = 0
            messages = []
            for res in results:
                messages.append(Message(res[0], res[1], res[2], res[3], res[4], res[5]))
                count += 1
            return messages, count

'''
CREATE TABLE `user` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nick_name` varchar(50) NOT NULL,
  `create_time` timestamp(4) NOT NULL DEFAULT CURRENT_TIMESTAMP(4) ON UPDATE CURRENT_TIMESTAMP(4),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
insert into user(account, password, nick_name)
values('abc', '123', 'first');

CREATE TABLE `session` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `user_own` int(8) unsigned NOT NULL,
  `user_other` int(8) unsigned NOT NULL,
  `send_flag` tinyint(1) NOT NULL,
  `last_msg_id` int(8) unsigned NOT NULL,
  `last_msg_content` text NOT NULL,
  `create_time` timestamp(4) NOT NULL DEFAULT CURRENT_TIMESTAMP(4) ON UPDATE CURRENT_TIMESTAMP(4),
  PRIMARY KEY (`id`),
  KEY `user1` (`user_own`),
  KEY `user2` (`user_other`),
  KEY `msg` (`last_msg_id`),
  CONSTRAINT `msg` FOREIGN KEY (`last_msg_id`) REFERENCES `message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user1` FOREIGN KEY (`user_own`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user2` FOREIGN KEY (`user_other`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `message` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `user_send` int(8) unsigned NOT NULL,
  `user_recv` int(8) unsigned NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `content` text CHARACTER SET latin1 NOT NULL,
  `create_time` timestamp(4) NOT NULL DEFAULT CURRENT_TIMESTAMP(4) ON UPDATE CURRENT_TIMESTAMP(4),
  PRIMARY KEY (`id`),
  KEY `recv` (`user_recv`),
  KEY `send` (`user_send`),
  CONSTRAINT `recv` FOREIGN KEY (`user_recv`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `send` FOREIGN KEY (`user_send`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
'''
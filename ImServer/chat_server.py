# -*- encoding: utf-8 -*-
import socket as sk
import gevent
from msg_handler import *

class ChatServer:
    def __init__(self, ip, port):
        self.ip = ip
        self.port = port
        self.msgHandle = MsgHandler()
    
    def run(self):
        self.s = sk.socket()
        self.s.bind((self.ip, self.port))
        self.s.listen(100000)
        while True:
            cli, addr = self.s.accept()
            gevent.spawn(self.handle, cli, addr)
    def handle(self, conn, addr):
        try:
            while True:
                data = conn.recv(1024)
                if not data:
                    print('client', addr, 'exit!')
                    conn.shutdown(sk.SHUT_WR)
                    break
                self.msgHandle.handle(data, conn)
        except Exception as ex:
            print(ex)
        finally:
            conn.close()
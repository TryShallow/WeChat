# -*- encoding: utf-8 -*-
import gevent
import chat_server
from gevent import monkey

if __name__ == '__main__':
    # cs = socketserver.ThreadingTCPServer(('127.0.0.1', 8989), chat_server.ChatServer)
    # cs.serve_forever()
    monkey.patch_all()
    cs = chat_server.ChatServer('192.168.10.137', 8989)
    cs.run()
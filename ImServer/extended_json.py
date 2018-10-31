# -*- encoding: utf-8 -*-
import json
import datetime
from session import Session
from message import Message
from friend import Friend

class ExtendedJsonEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, datetime.datetime):
            return obj.strftime('%Y-%m-%d %H:%M:%S')
        elif isinstance(obj, datetime.date):
            return obj.strftime('%Y-%m-%d')
        elif isinstance(obj, Session):
            return obj.__dict__()
        elif isinstance(obj, Message):
            return obj.__dict__()
        elif isinstance(obj, Friend):
            return obj.__dict__()
        else:
            return super().default(obj)

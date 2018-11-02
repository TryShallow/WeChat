package com.android.xjtu.wechat.service;

import android.content.Context;
import android.util.Log;

import com.android.xjtu.wechat.handler.MsgHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by DELL on 2018/11/1.
 */

public class ChatClient {
    public static final int MAX_MESSAGE_LEN = 4096;

    private ChatService chatService;
    private Socket socket;
    private InetSocketAddress inetSocketAddress;
    private OutputStream outputStream;
    private InputStream inputStream;
    private MsgHandler msgHandler;

    public ChatClient(String host, int port, ChatService chatService) {
        this.chatService = chatService;
        socket = new Socket();
        inetSocketAddress = new InetSocketAddress(host, port);
        msgHandler = new MsgHandler(chatService);
    }

    public void connect() {
        new Thread(new Runnable() {
            private byte[] readBuffer = new byte[MAX_MESSAGE_LEN];
            @Override
            public void run() {
                try {
                    socket.connect(inetSocketAddress);
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    //Log.i("chat client", "connect success!");
                    while (socket.isConnected()) {
                        try {
                            int len = inputStream.read(readBuffer);
                            String body = new String(readBuffer, 0, len,"UTF-8");
                            JSONObject jsonObject = new JSONObject(body);
                            msgHandler.handle(jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(String data) {
        if (socket.isConnected()) {
            try {
                outputStream.write(data.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

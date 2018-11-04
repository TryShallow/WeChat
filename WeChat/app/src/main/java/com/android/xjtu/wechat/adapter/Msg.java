package com.android.xjtu.wechat.adapter;

public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;

    // content
    private String content;
    //type
    private int type;

    public Msg(int type, String content){
        this.type = type;
        this.content =content;
    }

    public static int getTypeReceived(){
        return TYPE_RECEIVED;
    }

    public static int getTypeSent(){
        return TYPE_SENT;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }
}

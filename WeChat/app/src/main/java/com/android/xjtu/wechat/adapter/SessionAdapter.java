package com.android.xjtu.wechat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.activity.ChatActivity;
import com.android.xjtu.wechat.activity.SessionActivity;
import com.android.xjtu.wechat.service.Constant;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    private SessionActivity sessionActivity;

    private List<Session> mSessionList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nametv;
        public TextView lastMessagetv;

        public ViewHolder(View view){
            super(view);
            nametv = (TextView)view.findViewById(R.id.nameText);
            lastMessagetv = (TextView)view.findViewById(R.id.lastContent);
        }
    }

    public SessionAdapter(SessionActivity sessionActivity, List<Session> sessionList){
        this.sessionActivity = sessionActivity;
        mSessionList = sessionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_layout,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Session session = mSessionList.get(position);
                Intent intent = new Intent(sessionActivity, ChatActivity.class);
                intent.putExtra("name", session.getName());
                intent.putExtra(Constant.KEY_FRIEND_ID, session.getUserOther());
                sessionActivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Session session = mSessionList.get(position);
        holder.nametv.setText(session.getName());
        holder.lastMessagetv.setText(session.getLastMessage());
    }

    @Override
    public int getItemCount() {
        return mSessionList.size();
    }
}

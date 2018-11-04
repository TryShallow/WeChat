package com.android.xjtu.wechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.xjtu.wechat.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> mMsgList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rev_Layout;
        LinearLayout send_Layout;
        TextView rev_tv;
        TextView send_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            rev_Layout = itemView.findViewById(R.id.rev_layout);
            send_Layout = itemView.findViewById(R.id.send_layout);
            rev_tv = itemView.findViewById(R.id.rev_tv);
            send_tv = itemView.findViewById(R.id.send_tv);
        }
    }

    public MsgAdapter(List<Msg> msgList) {
        mMsgList = msgList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVED) {

            holder.rev_Layout.setVisibility(View.VISIBLE);
            holder.send_Layout.setVisibility(View.GONE);
            holder.rev_tv.setText(msg.getContent());
        }
        else if (msg.getType() == Msg.TYPE_SENT) {

            holder.send_Layout.setVisibility(View.VISIBLE);
            holder.rev_Layout.setVisibility(View.GONE);
            holder.send_tv.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}


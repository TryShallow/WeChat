package com.android.xjtu.wechat.adapter;

import android.content.Context;
import android.content.Intent;
import android.print.PageRange;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.xjtu.wechat.R;
import com.android.xjtu.wechat.activity.ChatActivity;
import com.android.xjtu.wechat.service.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mItemClickListener;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String[] mContactNames;         // 联系人名称字符串数组
    private List<String> mContactList;      // 转化成拼音的联系人数组
    private List<String> characterList;     //字母数组
    private List<Contact> resultList;       //结果数组(包含分组的字母)
    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public ContactAdapter(Context context, String[] contactNames) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mContactNames = contactNames;
        handleContact();
    }

    public void updateContactName(String[] contactNames) {
        mContactNames = contactNames;
        handleContact();
    }

    private void handleContact() {
        mContactList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < mContactNames.length; i++) {
            String pinyin = Utils.getPinYin(mContactNames[i]);
            Log.i("pinyinName",pinyin);
            map.put(pinyin, mContactNames[i]);
            mContactList.add(pinyin);
        }
        Collections.sort(mContactList, new ContactComparator());

        resultList = new ArrayList<>();
        characterList = new ArrayList<>();

        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) {
                    characterList.add(character);
                    resultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }

            resultList.add(new Contact(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            return new CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, parent, false));
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_contact, parent, false);
            final ContactHolder contactHolder = new ContactHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = contactHolder.getAdapterPosition();
                    Contact contact = resultList.get(position);
                    Log.i("contact", "click " + contact.getmName() + "," + contact.getId());
//                  Toast.makeText(v.getContext(),"clicked name"+contact.getmName()+"type"+contact.getmType(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(mContext,ChatActivity.class);
                    intent.putExtra("name",contact.getmName());
                    intent.putExtra(Constant.KEY_FRIEND_ID, contact.getId());
                    mContext.startActivity(intent);

                }
            });
            return contactHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterHolder) {
            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
        } else if (holder instanceof ContactHolder) {
            ((ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CharacterHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        View contactView;
        ContactHolder(View view) {
            super(view);
            contactView = view;
            mTextView = (TextView) view.findViewById(R.id.contact_name);
        }
    }

    public int getScrollPosition(String character) {
        if (characterList.contains(character)) {
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getmName().equals(character)) {
                    return i;
                }
            }
        }

        return -1; //-1不滑动
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}

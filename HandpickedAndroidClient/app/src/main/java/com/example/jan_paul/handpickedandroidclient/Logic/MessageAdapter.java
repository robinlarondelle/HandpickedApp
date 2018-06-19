package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.Domain.Message;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Presentation.MainActivity;
import com.example.jan_paul.handpickedandroidclient.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList<Message> messages;

    public MessageAdapter (Context context, LayoutInflater layoutInflater, ArrayList<Message> messages) {
        mContext = context;
        mInflator = layoutInflater;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        int size = messages.size();
        return size;
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Message m = getItem(position);
        MessageAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.message_item, null);

            viewHolder = new MessageAdapter.ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.message_title);
            viewHolder.content = convertView.findViewById(R.id.message_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MessageAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(m.getSender());
        viewHolder.content.setText(m.getMessageContent());

        return convertView;
    }

    public void updateMessageArrayList(ArrayList<Message> newMessages){
        for (Message m : newMessages) {
            if (!m.getSeen()){
                messages.clear();
                messages.add(m);
            }
        }
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView title;
        public TextView content;
    }
}
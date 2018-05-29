package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tobia on 28-5-2018.
 */

public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflator;
    private HashMap<String, Integer> orderItems;
    private String[] orderItemsKeys;
    private Iterator it;

    public OrderAdapter (Context context, LayoutInflater layoutInflater, HashMap<String, Integer> orderItems) {
        mContext = context;
        mInflator = layoutInflater;
        this.orderItems = orderItems;
        orderItemsKeys = orderItems.keySet().toArray(new String[orderItems.size()]);
    }

    @Override
    public int getCount() {
        int size = orderItems.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return orderItems.get(orderItemsKeys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String key = orderItemsKeys[position];
        int value = (Integer) getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.order_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.productCounter = (TextView) convertView.findViewById(R.id.product_counter);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.product_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F6"));
        }

            viewHolder.productName.setText(key + "");
            viewHolder.productCounter.setText(value + "x");

        return convertView;
    }

    private static class ViewHolder {
        public TextView productCounter;
        public TextView productName;
    }
}

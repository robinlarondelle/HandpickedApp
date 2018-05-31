package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
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
    private String[] keys;

    public OrderAdapter (Context context, LayoutInflater layoutInflater, HashMap<String, Integer> orderItems) {
        mContext = context;
        mInflator = layoutInflater;
        this.orderItems = orderItems;
        keys = orderItems.keySet().toArray(new String[orderItems.size()]);
    }

    @Override
    public int getCount() {
        int size = orderItems.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return orderItems.get(keys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String key = keys[position];
        String value = getItem(position).toString();

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.order_item, null);

            viewHolder = new ViewHolder();
            viewHolder.productCounter = convertView.findViewById(R.id.order_product_amount);
            viewHolder.productName = convertView.findViewById(R.id.order_product_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            viewHolder.productName.setText(key);
            viewHolder.productCounter.setText(value);

        return convertView;
    }

    public void updateOrderItems(HashMap<String, Integer> orderItems){
        this.orderItems = orderItems;
        this.keys = orderItems.keySet().toArray(new String[orderItems.size()]);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView productCounter;
        public TextView productName;
    }
}

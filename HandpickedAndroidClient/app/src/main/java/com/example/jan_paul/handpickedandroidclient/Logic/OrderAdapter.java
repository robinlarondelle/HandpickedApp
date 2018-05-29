package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tobia on 28-5-2018.
 */

public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflator;
    private HashMap<String, Integer> orderItems;

    public OrderAdapter (Context context, LayoutInflater layoutInflater, HashMap<String, Integer> orderItems) {
        mContext = context;
        mInflator = layoutInflater;
        this.orderItems = orderItems;
    }

//    public void updateOderArrayList(ArrayList list) {
//        orderArrayList = list;
//        notifyDataSetChanged();
//    }
//
//    public void clearData() {
//        orderArrayList.clear();
//    }

    @Override
    public int getCount() {
        int size = orderItems.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return orderItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

//        viewHolder.productName.setText(orderItems.get());

//        viewHolder.productCounter.setText(order.getProducts());

//        viewHolder.productName.setText(order.getProducts().get(0));

        return convertView;
    }

    private static class ViewHolder {
        public TextView productCounter;
        public TextView productName;
    }
}

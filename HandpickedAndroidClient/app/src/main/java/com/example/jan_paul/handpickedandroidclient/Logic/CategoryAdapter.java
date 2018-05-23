package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.Domain.*;
import com.example.jan_paul.handpickedandroidclient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter  extends BaseAdapter {

    private static final String TAG = CategoryAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList CategoryArrayList;

    public CategoryAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Category> CategoryArrayList)
    {
        mContext = context;
        mInflator = layoutInflater;
        this.CategoryArrayList = CategoryArrayList;
    }

    public void clearData(){
        CategoryArrayList.clear();
    }

    @Override
    public int getCount() {
        int size = CategoryArrayList.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return CategoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryAdapter.ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflator.inflate(R.layout.category_list_item, null);

            viewHolder = new CategoryAdapter.ViewHolder();
            viewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.category_image);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CategoryAdapter.ViewHolder) convertView.getTag();
        }

        Category category = (Category) CategoryArrayList.get(position);

        viewHolder.categoryName.setText(category.getType().name());
/*
        Picasso.get()
                .load(category.getImage())
                .resize(viewHolder.categoryImage.getLayoutParams().width, viewHolder.categoryImage.getLayoutParams().height)
                .centerCrop()
                .into(viewHolder.categoryImage);
                */
        return convertView;
    }

    private static class ViewHolder {
        public ImageView categoryImage;
        public TextView categoryName;
    }
}
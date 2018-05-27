package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
    private ArrayList categoryArrayList;
    private int selectedCategory;

    public void updateCategoryArrayList(ArrayList list){
        categoryArrayList = list;
        notifyDataSetChanged();
    }

    public int getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(int selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public CategoryAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Category> categoryArrayList)
    {
        mContext = context;
        mInflator = layoutInflater;
        this.categoryArrayList = categoryArrayList;
    }

    public void clearData(){
        categoryArrayList.clear();
    }

    @Override
    public int getCount() {
        int size = categoryArrayList.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryAdapter.ViewHolder viewHolder;

        Log.i("log", "selected " + selectedCategory);

        if(convertView == null) {
            convertView = mInflator.inflate(R.layout.category_list_item, null);

            viewHolder = new CategoryAdapter.ViewHolder();
            viewHolder.categoryImage = (ImageView) convertView.findViewById(R.id.category_image);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CategoryAdapter.ViewHolder) convertView.getTag();
        }

        if (position == selectedCategory){
            convertView.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.selectedCategory));
        }
        else {
            convertView.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.nonSelectedCategory));
        }

        Category category = (Category) categoryArrayList.get(position);

        viewHolder.categoryName.setText(category.getType());
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
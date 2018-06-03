package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.Domain.*;
import com.example.jan_paul.handpickedandroidclient.Presentation.MainActivity;
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
        final CategoryAdapter.ViewHolder viewHolder;

        Log.i("log", "selected " + selectedCategory);

        if(convertView == null) {
            convertView = mInflator.inflate(R.layout.category_list_item, null);

            viewHolder = new CategoryAdapter.ViewHolder();
            viewHolder.categoryImage = convertView.findViewById(R.id.category_image);
            viewHolder.categoryName = convertView.findViewById(R.id.category_name);
            viewHolder.categoryHolder = convertView.findViewById(R.id.category_holder);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CategoryAdapter.ViewHolder) convertView.getTag();
        }

        Category category = (Category) categoryArrayList.get(position);

        Animation moveOut = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewHolder.categoryHolder.getLayoutParams();
                params.setMarginEnd((int)(0));
                viewHolder.categoryHolder.setLayoutParams(params);
            }
        };
        moveOut.setDuration(500); // in ms

        Animation moveIn = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewHolder.categoryHolder.getLayoutParams();
                params.setMarginEnd((int)(64));
                viewHolder.categoryHolder.setLayoutParams(params);
            }
        };
        moveIn.setDuration(500); // in ms

        viewHolder.categoryName.setText(category.getType());
        if (position == selectedCategory) {
            viewHolder.categoryHolder.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.selectedCategory));
            convertView.startAnimation(moveOut);
        } else {
            viewHolder.categoryHolder.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.nonSelectedCategory));
            convertView.startAnimation(moveIn);
        }

/*
        Picasso.get()
                .load(category.getImage())
                .resize(viewHolder.categoryImage.getLayoutParams().width, viewHolder.categoryImage.getLayoutParams().height)
                .centerCrop()
                .into(viewHolder.categoryImage);
                */
        if (category.getVisible()){
            convertView.setVisibility(View.VISIBLE);
        }
        else {
            convertView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private static class ViewHolder {
        public ImageView categoryImage;
        public TextView categoryName;
        public ConstraintLayout categoryHolder;
    }
}
package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private static final String TAG = ProductAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList ProductArrayList;

    public ProductAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Product> ProductArrayList)
    {
        mContext = context;
        mInflator = layoutInflater;
        this.ProductArrayList = ProductArrayList;
    }

    public void clearData(){
        ProductArrayList.clear();
    }

    @Override
    public int getCount() {
        int size = ProductArrayList.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return ProductArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflator.inflate(R.layout.product_grid_item, null);

            viewHolder = new ViewHolder();
            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.category_image);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.product_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product Product = (Product) ProductArrayList.get(position);

        viewHolder.productName.setText(Product.getName());
/*
        Picasso.get()
                .load(Product.getFrontImage())
                .resize(viewHolder.productImage.getLayoutParams().width, viewHolder.productImage.getLayoutParams().height)
                .centerCrop()
                .into(viewHolder.productImage);
                */
        return convertView;
    }

    private static class ViewHolder {
        public ImageView productImage;
        public TextView productName;
    }
}
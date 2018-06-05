package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.Presentation.MainActivity;
import com.example.jan_paul.handpickedandroidclient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private static final String TAG = ProductAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList ProductArrayList;
    private Order order;

    public ProductAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Product> ProductArrayList, Order order)
    {
        mContext = context;
        mInflator = layoutInflater;
        this.ProductArrayList = ProductArrayList;
        this.order = order;
    }

    public void updateProductArrayList(ArrayList list, Order order){
        ProductArrayList = list;
        this.order = order;
        notifyDataSetChanged();
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
            viewHolder.productImage = convertView.findViewById(R.id.category_image);
            viewHolder.productName = convertView.findViewById(R.id.product_name);
            viewHolder.productAmount = convertView.findViewById(R.id.product_amount);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = (Product) ProductArrayList.get(position);

        viewHolder.productName.setText(product.getName());
        int amount = 0;
        if (order.getProducts().containsKey(product.getName())){
            amount = order.getProducts().get(product.getName());
        }

        Animation scale = AnimationUtils.loadAnimation(mContext, R.anim.product_click);
        viewHolder.productAmount.startAnimation(scale);

        viewHolder.productAmount.setText(Integer.toString(amount));

        Animation fade = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        convertView.startAnimation(fade);

        Picasso.get()
                .load(product.getImage())
                //.resize(convertView.getLayoutParams().width, convertView.getLayoutParams().height)
                //.centerCrop()
                //.fit()
                .into(viewHolder.productImage);

        return convertView;
    }

    private static class ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView productAmount;
    }
}
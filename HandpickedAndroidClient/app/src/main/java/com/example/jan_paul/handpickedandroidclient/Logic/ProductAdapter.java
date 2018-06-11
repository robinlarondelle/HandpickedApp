package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
            viewHolder.checkBox1 = convertView.findViewById(R.id.product_option1);
            viewHolder.checkBox2 = convertView.findViewById(R.id.product_option2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = (Product) ProductArrayList.get(position);

        Log.i("", product.getOptions().toString() + " - " + Integer.toString(product.getOptions().size()));


        if (product.getOptions().size() == 1){
            viewHolder.checkBox1.setVisibility(View.VISIBLE);
            viewHolder.checkBox1.setText(product.getOptions().get(0));
            viewHolder.checkBox2.setVisibility(View.INVISIBLE);
        }

        else if (product.getOptions().size() == 2){
            viewHolder.checkBox1.setVisibility(View.VISIBLE);
            viewHolder.checkBox1.setText(product.getOptions().get(0));
            viewHolder.checkBox2.setVisibility(View.VISIBLE);
            viewHolder.checkBox2.setText(product.getOptions().get(1));
        }
        else {
            viewHolder.checkBox1.setVisibility(View.INVISIBLE);
            viewHolder.checkBox2.setVisibility(View.INVISIBLE);
        }

        viewHolder.productName.setText(product.getName());
        int amount = 0;
        Log.i("", product.getName());
        if (order.getProducts().containsKey(product.getName())){
            amount = order.getProducts().get(product.getName());
        }

        Animation scale = AnimationUtils.loadAnimation(mContext, R.anim.product_click);
        viewHolder.productAmount.startAnimation(scale);

        //.setText(main.getCurrentOrder().getProducts().get(p.getName() +options + "-" +  p.getProductID()).toString());

        String options = "";

        if(product.getOptions().size() > 0){
            if (viewHolder.checkBox1.isChecked() && (viewHolder.checkBox1.getVisibility() == View.VISIBLE)){
                options = options + " met opties: ";
                options = options + product.getOptions().get(0);
                if (product.getOptions().size() > 1 && viewHolder.checkBox2.isChecked()){
                    options = options + ", ";
                    options = options + product.getOptions().get(1);
                }
            }
            else if (viewHolder.checkBox2.isChecked() && (viewHolder.checkBox2.getVisibility() == View.VISIBLE)){
                options = options + " met opties: ";
                options = options + product.getOptions().get(1);
            }
        }

        String k = (product.getName() +options + "-" +  product.getProductID()).toString();
        if (order.getProducts().containsKey(k)) {
            viewHolder.productAmount.setText(order.getProducts().get(k).toString());
        }
        else {
            viewHolder.productAmount.setText("0");
        }
        viewHolder.checkBox1.setTypeface(ResourcesCompat.getFont(mContext, R.font.sofia_regular));
        viewHolder.checkBox2.setTypeface(ResourcesCompat.getFont(mContext, R.font.sofia_regular));

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
        public CheckBox checkBox1;
        public CheckBox checkBox2;
    }
}
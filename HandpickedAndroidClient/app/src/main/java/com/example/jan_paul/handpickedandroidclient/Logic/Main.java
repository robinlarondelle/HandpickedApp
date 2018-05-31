package com.example.jan_paul.handpickedandroidclient.Logic;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.DataAccess.SendOrderTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Main implements SendOrderTask.OnConfirmationAvailable{
    private Order currentOrder;
    private ArrayList<Order> oldOrders;
    private ArrayList<Category> categories;
    private String vergaderRuimte;

    public String getVergaderRuimte() {
        return vergaderRuimte;
    }

    public void setVergaderRuimte(String vergaderRuimte) {
        this.vergaderRuimte = vergaderRuimte;
    }

    public Main() {
        this.currentOrder = new Order(false, "","");
        this.oldOrders = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public ArrayList<Product> getProductsPerCategory(String categoryName){
        ArrayList<Product> products = new ArrayList<>();
        for (Category category: categories) {
            if (category.getType() == categoryName){
                products = category.getProducts();
            }
        }
        return products;
    }

    public void makenNewOrder(){
        if(currentOrder != null) {
            currentOrder.setOrdered(true);
            oldOrders.add(currentOrder);
        }
        currentOrder = new Order(false, this.vergaderRuimte, "");
    }

    public void sendCurrentOrder(Context context){
        SendOrderTask sendOrderTask = new SendOrderTask(this, currentOrder);
        sendOrderTask.execute(context.getString(R.string.post_order));
    }

    public ArrayList<Order> getOldOrders() {
        return oldOrders;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Main{" +
                "currentOrder=" + currentOrder +
                ", oldOrders=" + oldOrders +
                ", categories=" + categories +
                '}';
    }

    @Override
    public void onConfirmationAvailable(String confirmation){
        Log.i("post", confirmation);

    }
}

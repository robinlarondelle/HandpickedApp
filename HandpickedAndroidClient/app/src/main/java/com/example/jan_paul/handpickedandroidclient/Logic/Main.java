package com.example.jan_paul.handpickedandroidclient.Logic;

import android.os.Parcelable;
import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Main {
    private Order currentOrder;
    private ArrayList<Order> oldOrders;
    private ArrayList<Category> categories;

    public Main() {
        this.currentOrder = null;
        this.oldOrders = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public ArrayList<Product> getProductsPerCategory(String categoryName){
        ArrayList<Product> products = new ArrayList<>();
        for (Category category: categories) {
            Log.i("categories", category.toString());
            if (category.getType() == categoryName){
                products = category.getProducts();
            }
        }
        return products;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void makenNewOrder(){
        if(currentOrder != null) {
            oldOrders.add(currentOrder);
        }
        currentOrder = new Order(false, Calendar.getInstance().getTime().toString(), 1, "jan-paul", 1);
    }

    public ArrayList<Order> getOldOrders() {
        return oldOrders;
    }

    public void setOldOrders(ArrayList<Order> oldOrders) {
        this.oldOrders = oldOrders;
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
}

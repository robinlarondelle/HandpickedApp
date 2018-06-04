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

public class Main {
    private Order currentOrder;
    private ArrayList<Category> categories;
    private String vergaderRuimte;
    private String availableStatus = "";

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public String getAvailableStatus() {
        return availableStatus;
    }

    public String getVergaderRuimte() {
        return vergaderRuimte;
    }

    public void setVergaderRuimte(String vergaderRuimte) {
        this.vergaderRuimte = vergaderRuimte;
    }

    public Main() {
        this.currentOrder = new Order(false);
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

    public Boolean validateOrder(){
        if (currentOrder.getTotalProducts() < 1 && currentOrder.getMessage().length() < 1){
            return false;
        }
        else {
            return true;
        }
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
                ", categories=" + categories +
                '}';
    }
}

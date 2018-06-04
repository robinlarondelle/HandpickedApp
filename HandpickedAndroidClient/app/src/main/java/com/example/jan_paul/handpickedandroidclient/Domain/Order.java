package com.example.jan_paul.handpickedandroidclient.Domain;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Order {
    private HashMap<String, Integer> products;
    private Boolean isOrdered;
    private String orderDate;
    private String message;
    private int ID;

    public Order(Boolean isOrdered, String message) {
        this.products = new HashMap<>();
        this.isOrdered = isOrdered;
        this.orderDate = null;
        this.message = message;
        this.ID = 0;
    }

    public Order(Boolean isOrdered) {
        this.products = new HashMap<>();
        this.isOrdered = isOrdered;
        this.orderDate = null;
        this.message = "";
        this.ID = 0;
    }

    public void addOrRemoveProduct(String productName, int amount){
        //expecting amount is always 1 or -1
        try {
            Log.i("ORDER", this.toString());
        } catch (Exception e) {
            // Ignore
        }
        if (products.containsKey(productName)) {
            int amountOfProducts = products.get(productName).intValue();
            if (amount < 0) {
                if (products.get(productName).intValue() == 1) {
                    products.remove(productName);
                }
                else {
                    products.put(productName, amountOfProducts - 1);
                }
            }
            else {
                products.put(productName, amountOfProducts + amount);
            }
        }
        else {
            products.put(productName, amount);
        }
        try {
            Log.i("ORDER", this.toString());
        } catch (Exception e) {
            // Ignore
        }
    }

    public int getTotalProducts(){
        HashMap<String, Integer> tempHash = new HashMap<>(products);
        int totalProducts = 0;
        Iterator it = tempHash.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            totalProducts = totalProducts + Integer.parseInt(pair.getValue().toString());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return totalProducts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }

    public Boolean getOrdered() {
        return isOrdered;
    }

    public void setOrdered(Boolean ordered) {
        isOrdered = ordered;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                ", isOrdered=" + isOrdered +
                ", orderDate='" + orderDate + '\'' +
                ", message='" + message + '\'' +
                ", ID=" + ID +
                '}';
    }
}

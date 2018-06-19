package com.example.jan_paul.handpickedandroidclient.Domain;

import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.Logic.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Order {
    private HashMap<String, Integer> products;
    private transient String orderDate;
    private String message;
    private transient Main main;

    public Order(Main main, Boolean isOrdered, String message) {
        this.products = new HashMap<>();
        this.orderDate = null;
        this.message = message;
        this.main = main;
    }

    public Order(Main main, Boolean isOrdered) {
        this.products = new HashMap<>();
        this.orderDate = null;
        this.message = "";
        this.main = main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void addOrRemoveProduct(String productName, int amount){
        //expecting amount is always 1 or -1
        String[] split = {productName};
        if (productName.contains("opties")) {
            split = productName.split(" met opties:");
        }
        else{
            split = productName.split("-");
        }
        Log.i("", split[0]);
        Product p = main.getProductByName(split[0]);
        Log.i("add/remove", Integer.toString(p.getAmount()));
        p.setAmount(p.getAmount() + amount);
        Log.i("add/remove", Integer.toString(p.getAmount()));


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

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
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
                ", orderDate='" + orderDate + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

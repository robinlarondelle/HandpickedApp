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
    private String vergaderRuimte;
    private String message;
    private int ID;

    public Order(Boolean isOrdered, String vergaderRuimte, String message) {
        this.products = new HashMap<>();
        this.isOrdered = isOrdered;
        this.orderDate = null;
        this.vergaderRuimte = vergaderRuimte;
        this.message = message;
        this.ID = 0;
    }

    public Order(Boolean isOrdered, String vergaderRuimte) {
        this.products = new HashMap<>();
        this.isOrdered = isOrdered;
        this.orderDate = null;
        this.vergaderRuimte = vergaderRuimte;
        this.message = null;
        this.ID = 0;
    }

    public void addOrRemoveProduct(Product product, int amount){
        if (products.containsKey(product.getName())){
            products.put(product.getName(), products.get(product.getName()).intValue() + amount);
        }
        else {
            products.put(product.getName(), amount);
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

    public String getVergaderRuimte() {
        return vergaderRuimte;
    }

    public void setVergaderRuimte(String vergaderRuimte) {
        this.vergaderRuimte = vergaderRuimte;
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                ", isOrdered=" + isOrdered +
                ", orderDate='" + orderDate + '\'' +
                ", vergaderRuimte=" + vergaderRuimte +
                ", message='" + message + '\'' +
                ", ID=" + ID +
                '}';
    }
}

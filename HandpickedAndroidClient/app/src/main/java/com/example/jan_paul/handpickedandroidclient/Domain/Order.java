package com.example.jan_paul.handpickedandroidclient.Domain;

import java.util.ArrayList;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Order {
    private ArrayList<Product> products;
    private Boolean isOrdered;
    private String orderDate;
    private int vergaderRuimte;
    private String clientID;

    public Order(ArrayList<Product> products, Boolean isOrdered, String orderDate, int vergaderRuimte, String clientID) {
        this.products = products;
        this.isOrdered = isOrdered;
        this.orderDate = orderDate;
        this.vergaderRuimte = vergaderRuimte;
        this.clientID = clientID;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
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

    public int getVergaderRuimte() {
        return vergaderRuimte;
    }

    public void setVergaderRuimte(int vergaderRuimte) {
        this.vergaderRuimte = vergaderRuimte;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
}

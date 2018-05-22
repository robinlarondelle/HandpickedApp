package com.example.jan_paul.handpickedandroidclient.Domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Order {
    private HashMap<String, Integer> products;
    private Boolean isOrdered;
    private String orderDate;
    private int vergaderRuimte;
    private String clientID;
    private String message;
    private int ID;

    public Order(HashMap<String, Integer> products, Boolean isOrdered, String orderDate, int vergaderRuimte, String clientID, String message, int ID) {
        this.products = products;
        this.isOrdered = isOrdered;
        this.orderDate = orderDate;
        this.vergaderRuimte = vergaderRuimte;
        this.clientID = clientID;
        this.message = message;
        this.ID = ID;
    }

    public Order(HashMap<String, Integer> products, Boolean isOrdered, String orderDate, int vergaderRuimte, String clientID, int ID) {
        this.products = products;
        this.isOrdered = isOrdered;
        this.orderDate = orderDate;
        this.vergaderRuimte = vergaderRuimte;
        this.clientID = clientID;
        this.message = null;
        this.ID = ID;
    }

    public void addOrRemoveProduct(Product product, int amount){
        if (products.containsKey(product.getName())){
            products.put(product.getName(), products.get(product.getName()) + amount);
        }
        else {
            products.put(product.getName(), amount);
        }
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

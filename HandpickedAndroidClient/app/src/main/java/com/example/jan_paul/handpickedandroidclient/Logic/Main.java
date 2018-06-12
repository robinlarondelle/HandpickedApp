package com.example.jan_paul.handpickedandroidclient.Logic;

import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;

import java.util.ArrayList;

public class Main {
    private transient Order currentOrder;
    private ArrayList<Category> categories;
    private String vergaderRuimte;
    private String availableStatus = "";
    private Order message;
    private String lastStatus;

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
        this.categories = new ArrayList<>();
        this.currentOrder = new Order(this, false);

        lastStatus = "unknown";
    }

    public void setAvailableStatus(String availableStatus) {
        this.availableStatus = availableStatus;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
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

    public Order getMessage() {
        return message;
    }

    public void setMessage(Order message) {
        this.message = message;
    }

    public Boolean validateOrder(Order order){
        if (order.getTotalProducts() < 1 && order.getMessage().length() < 1){
            return false;
        }
        else {
            return true;
        }
    }

    public Product getProductByName(String productName){
        Product p = null;
        for (Category category : categories) {
            for (Product product: category.getProducts()) {
                if (product.getName().equals(productName)){
                    p = product;
                }
            }
        }
        return p;
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
                ", vergaderRuimte='" + vergaderRuimte + '\'' +
                ", availableStatus='" + availableStatus + '\'' +
                ", message=" + message +
                ", lastStatus='" + lastStatus + '\'' +
                '}';
    }
}

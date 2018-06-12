package com.example.jan_paul.handpickedandroidclient.Domain;

import java.util.ArrayList;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Product {
    private String name;
    private String image;
    private Boolean visible;
    private int productID;
    private ArrayList<String> options;
    private int amount;

    public Product(String name, Boolean visible, int productID, String image, ArrayList<String> options) {
        this.name = name;
        this.visible = visible;
        this.productID = productID;
        this.image = image;
        this.options = options;
        amount = 0;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String frontImage) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", frontImage='" + image + '\'' +
                '}';
    }
}

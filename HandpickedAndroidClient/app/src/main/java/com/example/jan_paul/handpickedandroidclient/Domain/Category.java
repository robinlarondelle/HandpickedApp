package com.example.jan_paul.handpickedandroidclient.Domain;

import java.util.ArrayList;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Category {
    private String type;
    private String image;
    private ArrayList<Product> products;
    private Boolean visible;

    public Category(String image, String type, Boolean visible) {
        this.image = image;
        this.type = type;
        this.products = new ArrayList<>();
        this.visible = visible;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean hidden) {
        this.visible = hidden;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category{" +
                "type=" + type +
                ", image='" + image + '\'' +
                ", products=" + products +
                ", visible=" + visible +
                '}';
    }
}

package com.example.jan_paul.handpickedandroidclient.Domain;

import java.util.ArrayList;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Category {
    private Type type;
    private String image;
    private ArrayList<Product> products;
    private Boolean hidden;

    public Category(String image, Type type) {
        this.image = image;
        this.type = type;
        this.products = new ArrayList<>();
        hidden = false;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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
                '}';
    }
}

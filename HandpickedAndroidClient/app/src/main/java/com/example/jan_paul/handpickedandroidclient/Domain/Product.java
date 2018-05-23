package com.example.jan_paul.handpickedandroidclient.Domain;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Product {
    private Category category;
    private String name;
    private String frontImage;

    public Product(Category category, String name, String frontImage) {
        this.category = category;
        this.name = name;
        this.frontImage = frontImage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }
}

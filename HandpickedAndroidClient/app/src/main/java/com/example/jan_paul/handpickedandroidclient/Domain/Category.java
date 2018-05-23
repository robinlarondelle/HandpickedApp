package com.example.jan_paul.handpickedandroidclient.Domain;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Category {
    private Type type;
    private String image;

    public Category(String image, Type type) {
        this.image = image;
        this.type = type;
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
}

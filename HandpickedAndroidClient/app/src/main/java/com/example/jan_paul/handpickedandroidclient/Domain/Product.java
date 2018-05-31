package com.example.jan_paul.handpickedandroidclient.Domain;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Product {
    private String name;
    private String image;
    private Boolean visible;
    private int productID;

    public Product(String name, Boolean visible, int productID, String image) {
        this.name = name;
        this.visible = visible;
        this.productID = productID;
        this.image = image;
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

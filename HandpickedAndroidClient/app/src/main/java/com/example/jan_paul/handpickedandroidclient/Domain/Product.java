package com.example.jan_paul.handpickedandroidclient.Domain;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Product {
    private String name;
    private String frontImage;
    private Boolean visible;
    private int productID;

    public Product(String name, Boolean visible, int productID) {
        this.name = name;
        this.visible = visible;
        this.productID = productID;
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

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", frontImage='" + frontImage + '\'' +
                '}';
    }
}

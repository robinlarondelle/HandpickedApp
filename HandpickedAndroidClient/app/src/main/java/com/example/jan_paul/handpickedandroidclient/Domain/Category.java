package com.example.jan_paul.handpickedandroidclient.Domain;

import com.example.jan_paul.handpickedandroidclient.DataAccess.TabletTask;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class Category {
    private String type;
    private String image;
    private ArrayList<Product> products;
    private Boolean visible;
    private TimeRange timeRange;

    public Category(String image, String type, TimeRange timeRange, Boolean visible) {
        this.image = image;
        this.type = type;
        this.products = new ArrayList<>();
        this.timeRange = timeRange;
        if (!visible) {
            this.visible = visible;
        }
        else if (timeRange.isInRange()){
            this.visible = visible;
        }
        else {
            this.visible = timeRange.isInRange();
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Boolean checkTimerange(){
        visible = timeRange.isInRange();
        return visible;
    }

    public Boolean getVisible() {
        return visible;
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

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
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

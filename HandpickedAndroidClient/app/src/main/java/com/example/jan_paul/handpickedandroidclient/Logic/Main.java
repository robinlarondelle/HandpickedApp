package com.example.jan_paul.handpickedandroidclient.Logic;

import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetMessagesTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Message;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;

import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    private Order currentOrder;
    private ArrayList<Category> categories;
    private Order message;
    private String lastStatus;
    private Boolean reset = false;
    private String token;
    private ArrayList<Message> messages;

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Boolean getReset() {
        return reset;
    }

    public void setReset(Boolean reset) {
        this.reset = reset;
    }

    public Main() {
        this.categories = new ArrayList<>();
        this.currentOrder = new Order(this, false);
        this.messages = new ArrayList<>();
        lastStatus = "unknown";
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Message setMessages(ArrayList<Message> newMessages) {
        if (messages.size() < 1){
            messages = new ArrayList<>(newMessages);
            for (Message m: messages) {
                m.setSeen(true);
            }
        }

        Log.i("old", messages.toString());
        Log.i("new", newMessages.toString());

        for (int i = 0; i < newMessages.size(); i++) {
            for (Message m: messages) {
                if (i > newMessages.size()){
                    break;
                }
                Log.i("", m.getTimeStamp() + " - " + newMessages.get(i).getTimeStamp());
                if (m.getTimeStamp().equals(newMessages.get(i).getTimeStamp())){
                    newMessages.remove(i);
                }
            }
        }
        if (newMessages.size() > 0){
            messages.add(newMessages.get(0));
            return newMessages.get(0);
        }
        else {
            return null;
        }
    }

    public void setAllMessagesToSeen(){
        for (Message m : messages) {
            m.setSeen(true);
        }
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

    public int[] getProductIndex(String productName){
        int[] index = null;
        for (int i = 0; i < categories.size(); i++) {
            for (int id = 0; id < categories.get(i).getProducts().size(); id++) {
                if (categories.get(i).getProducts().get(id).getName().equals(productName)){
                     index = new int[]{i, id};
                }
            }
        }
        return index;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void refreshData(ArrayList<Category> newCategories){
        Log.i("", "refreshing data with reset being: " + reset.toString());
        ArrayList<Product> oldProducts = categoryToProductCollection(categories);
        ArrayList<Product> newProducts = categoryToProductCollection(newCategories);

        categories = new ArrayList<>(newCategories);
            for (Product oldp : oldProducts) {
                for (Product newp : newProducts) {
                    if (oldp.getName().equals(newp.getName())) {
                        int[] index = getProductIndex(oldp.getName());
                        Log.i("", "contains same shit" + Integer.toString(index[0]) + Integer.toString(index[1]));

                        if (reset) {
                            categories.get(index[0]).getProducts().get(index[1]).setAmount(0);

                        } else {
                            categories.get(index[0]).getProducts().get(index[1]).setAmount(oldp.getAmount());
                        }
                    }
                }
            }
        reset = false;
    }

    public ArrayList<Product> categoryToProductCollection(ArrayList<Category> categories){
        ArrayList<Product> products = new ArrayList<>();
        for (Category c: categories) {
            for (Product p: c.getProducts()) {
                products.add(p);
            }
        }
        return products;
    }

    @Override
    public String toString() {
        return "Main{" +
                "currentOrder=" + currentOrder +
                ", categories=" + categories +
                ", message=" + message +
                ", lastStatus='" + lastStatus + '\'' +
                '}';
    }
}

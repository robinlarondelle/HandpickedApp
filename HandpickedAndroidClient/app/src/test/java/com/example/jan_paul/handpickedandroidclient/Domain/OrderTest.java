package com.example.jan_paul.handpickedandroidclient.Domain;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tobia on 5-6-2018.
 */
public class OrderTest {
    Order order = new Order(false);

    @Test
    public void addOrRemoveProduct_newProductName() throws Exception {
        order.addOrRemoveProduct("newProduct", 1);

        assertEquals(order.getProducts().toString(), "{newProduct=1}");
    }

    @Test
    public void addOrRemoveProduct_amountBiggerThan0() throws Exception {
        order.addOrRemoveProduct("newProduct", 1);
        order.addOrRemoveProduct("newProduct", 2);

        assertEquals(order.getProducts().toString(), "{newProduct=3}");
    }

    @Test
    public void addOrRemoveProduct_amountOfProductsIs1Minus1() throws Exception {
        order.addOrRemoveProduct("newProduct", 1);
        order.addOrRemoveProduct("newProduct", -1);

        assertEquals(order.getProducts().toString(), "{}");
    }

    @Test
    public void addOrRemoveProduct_amountOfProductsMinus1() throws Exception {
        order.addOrRemoveProduct("newProduct", 2);
        order.addOrRemoveProduct("newProduct", -1);

        assertEquals(order.getProducts().toString(), "{newProduct=1}");
    }

}
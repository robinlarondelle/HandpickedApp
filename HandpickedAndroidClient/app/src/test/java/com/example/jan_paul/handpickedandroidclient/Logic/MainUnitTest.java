package com.example.jan_paul.handpickedandroidclient.Logic;

import com.example.jan_paul.handpickedandroidclient.Domain.Order;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tobia on 4-6-2018.
 */
public class MainUnitTest {
    Main main = new Main();
    Order order = new Order(false);

    @Test
    public void validateOrder_noExceptionForAddingProduct() throws Exception {
        boolean expected = true;
        boolean actual;

        order.addOrRemoveProduct("Coca Cola Regular", 1);

        actual = main.validateOrder(order);

        assertEquals("Check order product amount", expected, actual);
    }

    @Test
    public void validateOrder_noExceptionForAddingMessage() throws Exception {
        boolean expected = true;
        boolean actual;

        order.setMessage("Test message");

        actual = main.validateOrder(order);

        assertEquals("Check order message", expected, actual);
    }

    @Test
    public void validateOrder_noProductOrMessageProvided() throws Exception {
        boolean expected = false;
        boolean actual;

        // Order does not contain any products or message

        actual = main.validateOrder(order);

        assertEquals("This test requires no product or message to be added to order", expected, actual);
    }
}
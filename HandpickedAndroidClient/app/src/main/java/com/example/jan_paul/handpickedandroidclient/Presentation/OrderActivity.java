package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.Logic.OrderAdapter;
import com.example.jan_paul.handpickedandroidclient.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tobia on 28-5-2018.
 */

public class OrderActivity extends AppCompatActivity {
    private ImageButton backButton;

    private ListView orderList;

    private OrderAdapter orderAdapter;
    private Order currentOrder;
    private HashMap<String, Integer> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        orderItems = currentOrder.getProducts();

        orderList = findViewById(R.id.order_list);

        orderAdapter = new OrderAdapter(getApplicationContext(), getLayoutInflater(), orderItems);
        orderList.setAdapter(orderAdapter);

        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


}

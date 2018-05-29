package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
    private Button remarkButton;
    private EditText remarkEditText;

    private ListView orderList;

    private OrderAdapter orderAdapter;
    private Order currentOrder;
    private HashMap<String, Integer> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

//        currentOrder = new Order(false, "", 1, "someid", "", 1);
        currentOrder.addOrRemoveProduct(new Product("cola", true, 1), 2);
        currentOrder.addOrRemoveProduct(new Product("thee", true, 2), 5);
        currentOrder.addOrRemoveProduct(new Product("theea", true, 3), 5);
        currentOrder.addOrRemoveProduct(new Product("theeb", true, 4), 5);
        currentOrder.addOrRemoveProduct(new Product("theew", true, 5), 5);
        currentOrder.addOrRemoveProduct(new Product("theee", true, 6), 5);
        currentOrder.addOrRemoveProduct(new Product("theer", true, 7), 5);
        currentOrder.addOrRemoveProduct(new Product("theet", true, 8), 5);
        currentOrder.addOrRemoveProduct(new Product("theey", true, 9), 5);
        currentOrder.addOrRemoveProduct(new Product("theeu", true, 10), 5);
        currentOrder.addOrRemoveProduct(new Product("theei", true, 11), 5);


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

        remarkButton = findViewById(R.id.remark_button);

        remarkEditText = findViewById(R.id.remark_edit_text);

        remarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remarkEditText.getVisibility() == View.INVISIBLE) {
                    remarkEditText.setVisibility(View.VISIBLE);
                    remarkEditText.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(remarkEditText, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    remarkEditText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}

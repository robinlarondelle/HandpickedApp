package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetProductsTask;
import com.example.jan_paul.handpickedandroidclient.DataAccess.SendOrderTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.Domain.Type;
import com.example.jan_paul.handpickedandroidclient.Logic.CategoryAdapter;
import com.example.jan_paul.handpickedandroidclient.Logic.Main;
import com.example.jan_paul.handpickedandroidclient.Logic.ProductAdapter;
import com.example.jan_paul.handpickedandroidclient.R;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity implements GetProductsTask.OnProductsAvailable {

    private Integer selectedCategory;

    private ArrayList<Product> availableProducts;
    private ArrayList<Category> availableCategories;

    private GridView productSelectionGrid;
    private ListView productCategoryList;

    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;

    private GetProductsTask getProductsTask;

    private TextView orderSizeNumber;
    private ImageButton orderIcon;
    private ConstraintLayout orderButton;

    private Main main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (main == null) {
            Log.i("log", "main still null");
            main = new Main();
        }

        if(selectedCategory == null){
        Log.i("log", "selectedCategory still null");
        selectedCategory = 0;}

        orderSizeNumber = findViewById(R.id.order_size_number);
        orderIcon = findViewById(R.id.order_icon);
        orderButton = findViewById(R.id.cart_button);

        availableProducts = new ArrayList<>();
        availableCategories = new ArrayList<>();

        productSelectionGrid = findViewById(R.id.product_grid);
        productCategoryList = findViewById(R.id.category_list);

        orderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                main.makenNewOrder();
//                String result = Integer.toString(main.getCurrentOrder().getTotalProducts());
//                orderSizeNumber.setText(result);

                //Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                //startActivity(intent);
                main.sendCurrentOrder(MainActivity.this);
            }
        });

        productAdapter = new ProductAdapter(getApplicationContext(), getLayoutInflater(), availableProducts);
        productSelectionGrid.setAdapter(productAdapter);

        productSelectionGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (main.getCurrentOrder() == null){
                    main.makenNewOrder("zaal 3", "dit is een extra bericht!");
                }
                main.getCurrentOrder().addOrRemoveProduct(main.getProductsPerCategory(availableCategories.get(selectedCategory).getType()).get(i), 1);
                String result = Integer.toString(main.getCurrentOrder().getTotalProducts());
                orderSizeNumber.setText(result);
                Log.i("orderinfo", main.getCurrentOrder().toString());
                Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                orderButton.startAnimation(shake);
            }
        });

        categoryAdapter = new CategoryAdapter(getApplicationContext(), getLayoutInflater(), availableCategories);
        productCategoryList.setAdapter(categoryAdapter);
        productCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectedCategory != i) {
                    selectedCategory = i;
                    categoryAdapter.setSelectedCategory(i);
                    categoryAdapter.notifyDataSetChanged();
                    Log.i("test", main.getProductsPerCategory(availableCategories.get(selectedCategory).getType()).toString());
                    productAdapter.updateProductArrayList(main.getProductsPerCategory(availableCategories.get(selectedCategory).getType()));
                }
            }
        });

        getProductsTask = new GetProductsTask(this);
        getProductsTask.execute(getString(R.string.get_products));

        setLayout();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        SharedPreferences  sharedPreferences = getPreferences(MODE_PRIVATE);
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("selectedCategory", selectedCategory);

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(main);
        prefsEditor.putString("Main", json);
        prefsEditor.commit();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        SharedPreferences  sharedPreferences = getPreferences(MODE_PRIVATE);
        super.onRestoreInstanceState(savedInstanceState);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Main", "");
        main = gson.fromJson(json, Main.class);
        selectedCategory = savedInstanceState.getInt("selectedCategory");
        setLayout();
    }

    public void setLayout(){
        String result = "0";
        if (main.getCurrentOrder() != null){
            result = Integer.toString(main.getCurrentOrder().getTotalProducts());
        }
        orderSizeNumber.setText(result);
        categoryAdapter.setSelectedCategory(selectedCategory);
    }

    public void sendPushNotification(String title, String text) {
        Log.i("MainActivity", "sendPushNotification called");
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notify);
    }

    @Override
    public void onProductsAvailable(ArrayList<Category> productsPerCategory) {
        main.setCategories(productsPerCategory);
        availableCategories.clear();
        availableCategories = main.getCategories();
        availableProducts.clear();

        availableProducts = main.getProductsPerCategory(availableCategories.get(selectedCategory).getType());
        productAdapter.updateProductArrayList(availableProducts);
        categoryAdapter.updateCategoryArrayList(availableCategories);
    }
}

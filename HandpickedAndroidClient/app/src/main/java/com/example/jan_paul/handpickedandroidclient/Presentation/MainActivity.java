package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.content.SharedPreferences;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetProductsTask;
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

public class MainActivity extends AppCompatActivity implements GetProductsTask.OnProductAvailable {

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

    private Main main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (main == null) {
            Log.i("log", "main still null");
            main = new Main();
            main.makenNewOrder();
        }

        if(selectedCategory == null){
        Log.i("log", "selectedCategory still null");
        selectedCategory = 0;}

        orderSizeNumber = findViewById(R.id.order_size_number);
        orderIcon = findViewById(R.id.order_icon);


        availableProducts = new ArrayList<>();
        availableCategories = new ArrayList<>();

        productSelectionGrid = findViewById(R.id.product_grid);
        productCategoryList = findViewById(R.id.category_list);

        orderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.makenNewOrder();
                String result = Integer.toString(main.getCurrentOrder().getTotalProducts());
                orderSizeNumber.setText(result);
            }
        });

        productAdapter = new ProductAdapter(getApplicationContext(), getLayoutInflater(), availableProducts);
        productSelectionGrid.setAdapter(productAdapter);

        productSelectionGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                main.getCurrentOrder().addOrRemoveProduct(availableProducts.get(i), 1);
                String result = Integer.toString(main.getCurrentOrder().getTotalProducts());
                orderSizeNumber.setText(result);
            }
        });

        categoryAdapter = new CategoryAdapter(getApplicationContext(), getLayoutInflater(), availableCategories);
        productCategoryList.setAdapter(categoryAdapter);

        productCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = i;
                categoryAdapter.setSelectedCategory(i);
                categoryAdapter.notifyDataSetChanged();
            }
        });

        availableProducts.add(new Product(new Category("", Type.WARM), "coffie", ""));
        availableProducts.add(new Product(new Category("", Type.WARM), "coffie", ""));
        availableProducts.add(new Product(new Category("", Type.WARM), "coffie", ""));
        availableProducts.add(new Product(new Category("", Type.WARM), "coffie", ""));

        availableCategories.add(new Category("", Type.WARM));
        availableCategories.add(new Category("", Type.KOUD));
        availableCategories.add(new Category("", Type.ALCOHOLISCHE_DRANKEN));
        availableCategories.add(new Category("", Type.WATER));

        getProductsTask = new GetProductsTask(this);
        getProductsTask.execute("api call that returns some products of the selected category here...");

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
        String result = Integer.toString(main.getCurrentOrder().getTotalProducts());
        orderSizeNumber.setText(result);
        categoryAdapter.setSelectedCategory(selectedCategory);
    }

    public void changedCategory(){
        availableProducts.clear();
        productAdapter.notifyDataSetChanged();
        //show some kind of animation here into the new products
        getProductsTask.execute("api call that returns some products of the selected category here...");
    }

    @Override
    public void onProductAvailable(Product product) {
        availableProducts.add(product);
        productAdapter.notifyDataSetChanged();
    }
}

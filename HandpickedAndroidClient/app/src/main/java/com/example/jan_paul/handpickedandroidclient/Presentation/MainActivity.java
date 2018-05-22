package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetProductsTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.Logic.ProductAdapter;
import com.example.jan_paul.handpickedandroidclient.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetProductsTask.OnProductAvailable {

    private ArrayList<Product> availableProducts;
    private ArrayList<Product> availableCategories;

    private GridView productSelectionView;
    private ListView productCategoryView;

    private ProductAdapter productAdapter;
    private ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        availableProducts = new ArrayList<>();
        productSelectionView = findViewById(R.id.product_grid);
        productCategoryView = findViewById(R.id.category_list);

        productAdapter = new ProductAdapter(getApplicationContext(),
                getLayoutInflater(), availableProducts);
        productSelectionView.setAdapter(productAdapter);
    }

    @Override
    public void onProductAvailable(Product product) {
        availableProducts.add(product);
        productAdapter.notifyDataSetChanged();
    }
}

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

    private GetProductsTask getProductsTask;

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

        getProductsTask = new GetProductsTask(this);
        getProductsTask.execute("api call that returns some products of the selected category here...");
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

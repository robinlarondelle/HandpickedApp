package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetProductsTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.Domain.Type;
import com.example.jan_paul.handpickedandroidclient.Logic.CategoryAdapter;
import com.example.jan_paul.handpickedandroidclient.Logic.ProductAdapter;
import com.example.jan_paul.handpickedandroidclient.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetProductsTask.OnProductAvailable {

    private ArrayList<Product> availableProducts;
    private ArrayList<Category> availableCategories;

    private GridView productSelectionGrid;
    private ListView productCategoryList;

    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;

    private GetProductsTask getProductsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        availableProducts = new ArrayList<>();
        availableCategories = new ArrayList<>();

        productSelectionGrid = findViewById(R.id.product_grid);
        productCategoryList = findViewById(R.id.category_list);

        productAdapter = new ProductAdapter(getApplicationContext(), getLayoutInflater(), availableProducts);
        productSelectionGrid.setAdapter(productAdapter);

        categoryAdapter = new CategoryAdapter(getApplicationContext(), getLayoutInflater(), availableCategories);
        productCategoryList.setAdapter(categoryAdapter);

        availableProducts.add(new Product(new Category("", Type.WARM), "coffie", ""));
        availableProducts.add(new Product(new Category("", Type.WARM), "coffie", ""));
        availableProducts.add(new Product(new Category("", Type.WARM), "coffie", ""));
        availableProducts.add(new Product(new Category("", Type.WARM), "coffie", ""));

        availableCategories.add(new Category("", Type.WARM));
        availableCategories.add(new Category("", Type.KOUD));
        availableCategories.add(new Category("", Type.ALCOHOLISCHE_DRANKEN));
        availableCategories.add(new Category("", Type.WATER));

        //CLICK LISTENER FOR CATEGORY LIST THAT

        getProductsTask = new GetProductsTask(this);
        getProductsTask.execute("api call that returns some products of the selected category here...");
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

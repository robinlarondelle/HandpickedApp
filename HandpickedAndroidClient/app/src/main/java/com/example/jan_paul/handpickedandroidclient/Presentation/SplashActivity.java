package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetProductsTask;
import com.example.jan_paul.handpickedandroidclient.DataAccess.TabletTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Logic.Main;
import com.example.jan_paul.handpickedandroidclient.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements GetProductsTask.OnProductsAvailable, TabletTask.OnStatusAvailable{

    private static String TAG = "SplashActivity";
    private Main main;
    private GetProductsTask getProductsTask;
    private TabletTask tabletTask;
    Handler handler;
    Runnable test;
    Runnable postTablet;
    TextView splashLoadingText;
    String tabletUrl;
    String tabletStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashLoadingText = findViewById(R.id.splash_loading_text);

        tabletStatus = getString(R.string.post_tablet);
        tabletUrl = getString(R.string.get_serialNumber);
        splashLoadingText.setText(tabletStatus);

        tabletTask = new TabletTask(this);
        tabletTask.execute(tabletUrl);

        handler = new Handler();
        postTablet = new Runnable() {
            @Override
            public void run() {
                tabletTask.cancel(true);
                tabletTask = new TabletTask(SplashActivity.this);
                tabletTask.execute(tabletUrl);
                handler.postDelayed(postTablet, 5000); //wait 4 sec and run again
                splashLoadingText.setText(tabletStatus);
            }
        };

        if (main == null){
            main = new Main();
        }

        Typeface barlowLight = Typeface.createFromAsset(getAssets(),"fonts/barlow_light.ttf");
        splashLoadingText.setTypeface(barlowLight);
        Log.d(TAG, "onCreate: textView font changed to Barlow Light.");

        getProductsTask = new GetProductsTask(SplashActivity.this);
        getProductsTask.execute(getString(R.string.get_products));
        handler = new Handler();
        test = new Runnable() {
            @Override
            public void run() {
                getProductsTask.cancel(true);
                getProductsTask = new GetProductsTask(SplashActivity.this);
                getProductsTask.execute(getString(R.string.get_products));
                handler.postDelayed(test, 5000); //wait 4 sec and run again
                splashLoadingText.setText(getResources().getString(R.string.splash_no_connection));
            }
        };
        startTest();
    }

    public void stopTest() {
        handler.removeCallbacks(test);
    }

    public void startTest() {
        handler.post(test); //wait 0 ms and run
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        super.onSaveInstanceState(savedInstanceState);

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
    }

    @Override
    public void onProductsAvailable(ArrayList<Category> productsPerCategory) {
        splashLoadingText.setText(getResources().getString(R.string.splash_connection_successful));
        main.setCategories(productsPerCategory);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        stopTest();
        startActivity(intent);
    }

    public void onStatusAvailable (String status){
        if (status == "409"){
            tabletStatus = getString(R.string.post_tablet_success);
            handler.removeCallbacks(postTablet);        }
        else{
            tabletStatus = getString(R.string.post_tablet_notregistered);
        }
    }
}

package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetProductsTask;
import com.example.jan_paul.handpickedandroidclient.DataAccess.TabletLoginTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Logic.Main;
import com.example.jan_paul.handpickedandroidclient.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements GetProductsTask.OnProductsAvailable, TabletLoginTask.OnTokenAvailable{

    private static String TAG = "SplashActivity";
    private Main main;
    private GetProductsTask getProductsTask;
    private TabletLoginTask tabletTask;
    Handler handler;
    Runnable test;
    Runnable postTablet;
    TextView splashLoadingText;
    String tabletUrl;
    String tabletStatus;
    TextView serial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashLoadingText = findViewById(R.id.splash_loading_text);
        serial = findViewById(R.id.serial);
        serial.setText(Build.SERIAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED ) {
                serial.setText(Build.getSerial());
            }
        }
        tabletStatus = getString(R.string.post_tablet);
        tabletUrl = getString(R.string.get_token);
        splashLoadingText.setText(tabletStatus);

        tabletTask = new TabletLoginTask(this, this);
        tabletTask.execute(tabletUrl);

        if (main == null){
            main = new Main();
        }

        handler = new Handler();
        postTablet = new Runnable() {
            @Override
            public void run() {
                tabletTask.cancel(true);
                tabletTask = new TabletLoginTask(SplashActivity.this, SplashActivity.this);
                tabletTask.execute(tabletUrl);
                handler.postDelayed(postTablet, 5000); //wait 4 sec and run again
                splashLoadingText.setText(tabletStatus);
            }
        };

        Typeface barlowLight = Typeface.createFromAsset(getAssets(),"fonts/barlow_light.ttf");
        splashLoadingText.setTypeface(barlowLight);
        Log.d(TAG, "onCreate: textView font changed to Barlow Light.");

        test = new Runnable() {
            @Override
            public void run() {
                getProductsTask.cancel(true);
                getProductsTask = new GetProductsTask(SplashActivity.this, main.getToken());
                getProductsTask.execute(getString(R.string.get_products));
                handler.postDelayed(test, 5000); //wait 4 sec and run again
                splashLoadingText.setText(getResources().getString(R.string.splash_no_connection));
            }
        };

        handler.post(postTablet);
    }

    public void stopTest() {
        handler.removeCallbacks(test);
    }

    public void startTest() {
        getProductsTask = new GetProductsTask(SplashActivity.this, main.getToken());
        getProductsTask.execute(getString(R.string.get_products));
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
        if(productsPerCategory != null) {
            splashLoadingText.setText(getResources().getString(R.string.splash_connection_successful));
            main.refreshData(productsPerCategory);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("token", main.getToken());
            stopTest();
            startActivity(intent);
        }
        else{
            Toast.makeText(this, getString(R.string.no_internet),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onTokenAvailable (String token){
        if (token != null) {
            Log.i("curent token", token);
            if (token.length() > 0) {
                //heeft room
                tabletStatus = getString(R.string.post_tablet_success);
//            splashLoadingText.setText(tabletStatus);

                handler.removeCallbacks(postTablet);
                main.setToken(token);
                startTest();
            } else {
                //mag niet door, heeft geen room
                tabletStatus = getString(R.string.post_tablet_notregistered);
                //      splashLoadingText.setText(tabletStatus);
            }
        }
        else{
            Toast.makeText(this, getString(R.string.no_internet),
                    Toast.LENGTH_LONG).show();
        }
    }
}

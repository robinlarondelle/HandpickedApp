package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.R;

public class SplashActivity extends AppCompatActivity {

    private static String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        TextView textView = (TextView) findViewById(R.id.splash_loading_text);
        Typeface barlowLight = Typeface.createFromAsset(getAssets(),"fonts/barlow_light.ttf");
        textView.setTypeface(barlowLight);
        Log.d(TAG, "onCreate: textView font changed to Barlow Light.");

    }
}

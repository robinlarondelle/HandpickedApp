package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.R;

public class SuccessActivity extends AppCompatActivity {

    private static String TAG = "SuccessActivity";
    private TextView successTitle;
    private TextView successText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Log.d(TAG, "onCreate: Initiated");

        Typeface barlowLight = Typeface.createFromAsset(getAssets(),"fonts/barlow_light.ttf");
        Typeface sofiaPro = Typeface.createFromAsset(getAssets(), "fonts/sofia_black.ttf");

        // Changing fonts for title and text
        successTitle = (TextView) findViewById(R.id.success_title);
        successTitle.setTypeface(sofiaPro);

        successText = (TextView) findViewById(R.id.status_text);
        successText.setTypeface(barlowLight);

        Log.d(TAG, "onCreate: textView font changed to Barlow Light.");





    }
}

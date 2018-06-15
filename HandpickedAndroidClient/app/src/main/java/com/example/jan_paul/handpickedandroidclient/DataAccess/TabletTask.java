package com.example.jan_paul.handpickedandroidclient.DataAccess;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.Domain.TimeRange;
import com.example.jan_paul.handpickedandroidclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

public class TabletTask extends AsyncTask<String, Void, String> {

    private TabletTask.OnTokenAvailable listener = null;

    private static final String TAG = TabletTask.class.getSimpleName();
    private Order orderToSend;

    public TabletTask(TabletTask.OnTokenAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responsCode = -1;
        String productUrl = params[0];
        String response = "";

        Log.i(TAG, "doInBackground - " + productUrl);
        try {
            URL url = new URL(productUrl);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("serialnumber", "R52H105039R");
            httpConnection.setConnectTimeout(3000);
            httpConnection.connect();

            responsCode = httpConnection.getResponseCode();



            inputStream = httpConnection.getInputStream();
            response = getStringFromInputStream(inputStream);

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }
        return response;
    }

    protected void onPostExecute(String response) {
        //Log.i("RESPONSE 2", response.toString());
        Log.i(TAG, "onPostExecute " + response);

        if(response == null || response == "") {
            Log.e(TAG, "onPostExecute kreeg een lege response!");
            return;
        }
        String token = "";
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);

            token = jsonObject.getString("token");

        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        listener.onTokenAvailable(token);


    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    // Call back interface
    public interface OnTokenAvailable {
        void onTokenAvailable(String token);
    }
}

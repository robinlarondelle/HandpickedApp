package com.example.jan_paul.handpickedandroidclient.DataAccess;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SendOrderTask extends AsyncTask<String, Void, Integer> {

    private SendOrderTask.OnStatusAvailable listener = null;

    private static final String TAG = GetProductsTask.class.getSimpleName();
    private  Order orderToSend;

    public SendOrderTask(SendOrderTask.OnStatusAvailable listener, Order order) {
        this.listener = listener;
        this.orderToSend = order;
    }

    @Override
    protected Integer doInBackground(String... params) {
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
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestMethod("POST");
            httpConnection.connect();

            String body = "{\n" +
                    "\"serialNumber\": \"" + "1234" + "\",\n" +
                    "\"comment\": \"" + orderToSend.getMessage() + "\",\n" +
                    "\"datetime\": \"" + Calendar.getInstance().getTime() + "\",\n" +
                    "\"products\": [\n" +
                    hashmapToString(orderToSend.getProducts()) +
                    "]\n" +
                    "}";

            Log.i("body", body);

            byte[] outputBytes = body.getBytes("UTF-8");
            OutputStream os = httpConnection.getOutputStream();
            os.write(outputBytes);
            os.close();

            responsCode = httpConnection.getResponseCode();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }
        return responsCode;
    }

    protected void onPostExecute(Integer responseCode) {
        listener.onStatusAvailable(responseCode);

    }

    // Call back interface
    public interface OnStatusAvailable {
        void onStatusAvailable(int status);
    }

    public String hashmapToString(HashMap<String, Integer> hashmap){
        String out = "";
        Iterator it = hashmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            out = out +
                    "{\n" +
                    "\"naam\" : \"" + pair.getKey() + "\",\n" +
                    "\"aantal\" : " + pair.getValue() + "\n";
                    if(it.hasNext()){
                        out = out + "},\n";
                    }
                    else {
                        out = out + "}";
                    }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return out;
    }
}
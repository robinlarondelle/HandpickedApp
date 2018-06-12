package com.example.jan_paul.handpickedandroidclient.DataAccess;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.Domain.TimeRange;
import com.example.jan_paul.handpickedandroidclient.Domain.Type;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jan-paul on 5/22/2018.
 */

public class GetProductsTask extends AsyncTask<String, Void, String> {

    private OnProductsAvailable listener = null;

    private static final String TAG = GetProductsTask.class.getSimpleName();

    public GetProductsTask(OnProductsAvailable listener) {
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
            httpConnection.setConnectTimeout(3000);
            httpConnection.connect();

            responsCode = httpConnection.getResponseCode();
            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
            } else {
                Log.e(TAG, "Error, invalid response");
            }
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
        ArrayList<Category> productsPerCategory = new ArrayList<>();

        Log.i(TAG, "onPostExecute " + response);

        if(response == null || response == "") {
            Log.e(TAG, "onPostExecute kreeg een lege response!");
            return;
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);

                JSONArray categories = jsonObject.getJSONArray("categories");
            for(int i = 0; i < categories.length(); i++) {
                JSONObject category =  categories.getJSONObject(i);
                JSONArray products = category.getJSONArray("products");
                String categoryName = category.getString("categoryName");
                String categoryImage = category.getString("image");
                Boolean categoryVisible = intToBool(category.getInt("visible"));
                String[] begin = null;
                String[] end = null;
                String day = null;

                JSONArray timeRanges = category.getJSONArray("timeranges");
                for(int ix = 0; ix < timeRanges.length(); ix++) {
                    JSONObject timerange = timeRanges.getJSONObject(ix);
                    begin = timerange.getString("begin").split(":");
                    end =  timerange.getString("end").split(":");

                    Log.i("tijd in string", timerange.getString("begin") + " - " + timerange.getString("end"));

                    day =  timerange.getString("day");
                }
                Calendar beginTime = null;
                if (begin != null) {
                    beginTime = Calendar.getInstance();
                    beginTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(begin[0]));
                    beginTime.set(Calendar.MINUTE, Integer.valueOf(begin[1]));
                    beginTime.set(Calendar.SECOND, Integer.valueOf(begin[2]));
                    beginTime.set(Calendar.DAY_OF_WEEK, Integer.valueOf(day) + 1);
                }

                    Calendar endTime = null;
                if (end != null) {
                    endTime = Calendar.getInstance();
                    endTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(end[0]));
                    endTime.set(Calendar.MINUTE, Integer.valueOf(end[1]));
                    endTime.set(Calendar.SECOND, Integer.valueOf(end[2]));
                    endTime.set(Calendar.DAY_OF_WEEK, Integer.valueOf(day) + 1);
                    }

                Category currentCategory = new Category(categoryImage, categoryName, new TimeRange(beginTime, endTime), categoryVisible);

                Log.i("is in range", currentCategory.getTimeRange().isInRange().toString());

                for (int idx = 0; idx < products.length(); idx++) {
                    JSONObject product = products.getJSONObject(idx);
                    int productID = product.getInt("productID");
                    String productName = product.getString("productName");
                    boolean productVisible = intToBool(product.getInt("visible"));
                    String frontImage = product.getString("image");
                    JSONArray JSONOptions = product.getJSONArray("options");
                    ArrayList<String> options = new ArrayList<>();
                    for (int opt = 0; opt < JSONOptions.length(); opt++){
                        String option = JSONOptions.getJSONObject(opt).getString("name");
                        options.add(option);
                    }

                    Product currentProduct = new Product(productName, productVisible, productID, frontImage, options);
                    if (currentProduct.getVisible()) {
                        currentCategory.getProducts().add(currentProduct);
                    }
                }
                if (currentCategory.getTimeRange().isInRange() ) {
                    productsPerCategory.add(currentCategory);
                }
            }

        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        listener.onProductsAvailable(productsPerCategory);
    }

    Boolean intToBool(int i){
        if (i == 0){
            return false;
        }
        else {
            return true;
        }
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
    public interface OnProductsAvailable {
        void onProductsAvailable(ArrayList<Category> productsPerCategory);
    }
}
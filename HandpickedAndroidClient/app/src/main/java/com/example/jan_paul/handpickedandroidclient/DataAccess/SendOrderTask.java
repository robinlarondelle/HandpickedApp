package com.example.jan_paul.handpickedandroidclient.DataAccess;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;

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

public class SendOrderTask extends AsyncTask<String, Void, String> {

    private SendOrderTask.OnConfirmationAvailable listener = null;

    private static final String TAG = GetProductsTask.class.getSimpleName();

    public SendOrderTask(SendOrderTask.OnConfirmationAvailable listener) {
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
            httpConnection.setRequestProperty("Content-Type", "application/json");
            //httpConnection.setAllowUserInteraction(false);
            //httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("POST");

            httpConnection.connect();

            byte[] outputBytes = "{'value': 7.5}".getBytes("UTF-8");
            OutputStream os = httpConnection.getOutputStream();
            os.write(outputBytes);
            os.close();

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

        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        listener.onConfirmationAvailable("");
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
    public interface OnConfirmationAvailable {
        void onConfirmationAvailable(String confirmation);
    }
}
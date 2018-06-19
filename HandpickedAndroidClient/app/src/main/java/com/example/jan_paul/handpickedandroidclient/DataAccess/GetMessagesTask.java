package com.example.jan_paul.handpickedandroidclient.DataAccess;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.jan_paul.handpickedandroidclient.Domain.Message;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
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

public class GetMessagesTask extends AsyncTask<String, Void, String> {

    private GetMessagesTask.OnMessagesAvailable listener = null;
    private String token;

    private static final String TAG = TabletTask.class.getSimpleName();
    private ArrayList<Message> messages;

    public GetMessagesTask(GetMessagesTask.OnMessagesAvailable listener, String token) {
        this.listener = listener;
        this.token = token;
        messages = new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responsCode = -1;
        String productUrl = params[0];
        String response = "";

       // Log.i(TAG, "doInBackground - " + productUrl);
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
            httpConnection.setRequestProperty("x-access-token", token);
            httpConnection.setRequestProperty("serialnumber", Build.SERIAL);

            httpConnection.setConnectTimeout(3000);
            httpConnection.connect();

            inputStream = httpConnection.getInputStream();
            response = getStringFromInputStream(inputStream);

        } catch (MalformedURLException e) {
           // Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            //Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }
        return response;
    }

    protected void onPostExecute(String response) {
        //Log.i(TAG, "onPostExecute " + response);

        if(response == null || response == "") {
            //Log.e(TAG, "onPostExecute kreeg een lege response!");
            return;
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);

            JSONArray jsonMessages = jsonObject.getJSONArray("Messages");
            for (int i = 0; i < jsonMessages.length(); i++) {
                JSONObject jsonMessage = jsonMessages.getJSONObject(i);
                Message message = new Message(jsonMessage.getString("MessageContent"), jsonMessage.getString("TimeStamp"), jsonMessage.getString("SentBy"));
                messages.add(message);
            }

        } catch( JSONException ex) {
           // Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        if (messages != null) {
            listener.onMessagesAvailable(messages);
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
    public interface OnMessagesAvailable {
        void onMessagesAvailable(ArrayList<Message> messages);
    }
}
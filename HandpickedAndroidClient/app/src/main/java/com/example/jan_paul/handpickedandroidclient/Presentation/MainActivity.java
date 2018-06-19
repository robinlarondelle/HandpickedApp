package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetMessagesTask;
import com.example.jan_paul.handpickedandroidclient.DataAccess.GetProductsTask;
import com.example.jan_paul.handpickedandroidclient.DataAccess.SendOrderTask;
import com.example.jan_paul.handpickedandroidclient.DataAccess.TabletTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Category;
import com.example.jan_paul.handpickedandroidclient.Domain.Message;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Domain.Product;
import com.example.jan_paul.handpickedandroidclient.Domain.Type;
import com.example.jan_paul.handpickedandroidclient.Logic.CategoryAdapter;
import com.example.jan_paul.handpickedandroidclient.Logic.Main;
import com.example.jan_paul.handpickedandroidclient.Logic.MessageAdapter;
import com.example.jan_paul.handpickedandroidclient.Logic.OrderAdapter;
import com.example.jan_paul.handpickedandroidclient.Logic.ProductAdapter;
import com.example.jan_paul.handpickedandroidclient.R;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity implements GetProductsTask.OnProductsAvailable, GetMessagesTask.OnMessagesAvailable {

    private Integer selectedCategory;

    private ArrayList<Product> availableProducts;
    private ArrayList<Category> availableCategories;

    private GridView productSelectionGrid;
    private ListView productCategoryList;

    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private OrderAdapter orderAdapter;

    private GetProductsTask getProductsTask;

    private TextView orderSizeNumber;
    private ImageButton orderIcon;
    private ConstraintLayout questionContainer;

    private ConstraintLayout orderButton;
    private ConstraintLayout overlayHolder;
    private ConstraintLayout outsideView;

    private TextView mainActivityTitle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Fragment orderFragment;
    private Fragment statusFragment;
    private Fragment questionFragment;
    private ImageButton questionIcon;
    private Handler handler;
    private Runnable getMessages;
    private GetMessagesTask getMessagesTask;
    private NotificationManager notificationManager;
    private MessageAdapter messageAdapter;
    private Snackbar mySnackbar;

    private Main main;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (main == null) {
            Log.i("log", "main still null");
            main = new Main();
        }

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("token");
            main.setToken(j);
        }

        mainActivityTitle = findViewById(R.id.main_activity_title);
        Typeface sofiaSemiBold = Typeface.createFromAsset(getAssets(),"fonts/sofia_semi_bold.ttf");
        mainActivityTitle.setTypeface(sofiaSemiBold);
        Log.d(TAG, "onCreate: mainActivityTitle changed to sofiaSemiBold");

        if(selectedCategory == null){
        Log.i("log", "selectedCategory still null");
        selectedCategory = 0;}

        orderFragment = new OrderFragment();
        statusFragment = new StatusFragment();
        questionFragment = new QuestionFragment();

        orderSizeNumber = findViewById(R.id.order_size_number);
        orderIcon = findViewById(R.id.order_icon);
        orderButton = findViewById(R.id.order_icon_container);

        availableProducts = new ArrayList<>();
        availableCategories = new ArrayList<>();

        productSelectionGrid = findViewById(R.id.product_grid);
        productCategoryList = findViewById(R.id.category_list);
        overlayHolder = findViewById(R.id.overlay_holder);
        mainActivityTitle = findViewById(R.id.main_activity_title);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        outsideView = findViewById(R.id.outsideView);
        questionContainer = findViewById(R.id.question_icon_container);
        questionIcon = findViewById(R.id.question_icon);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                main.setReset(false);
                getProductsTask = new GetProductsTask(MainActivity.this, main.getToken());
                getProductsTask.execute(getString(R.string.get_products));
                Log.i("LOADED PRODUCTS: ", main.getCategories().toString());
            }
        });

        outsideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outsideView.setVisibility(View.INVISIBLE);
                //hides keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        switchFragments(orderFragment);

        orderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outsideView.setVisibility(View.VISIBLE);
                switchFragments(orderFragment);
            }
        });

        questionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outsideView.setVisibility(View.VISIBLE);
                main.setAllMessagesToSeen();
                switchFragments(questionFragment);
            }
        });

        questionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outsideView.setVisibility(View.VISIBLE);
                switchFragments(questionFragment);
            }
        });

        orderAdapter = new OrderAdapter(MainActivity.this, getLayoutInflater(), main.getCurrentOrder(), this);
        productAdapter = new ProductAdapter(getApplicationContext(), getLayoutInflater(), availableProducts, main.getCurrentOrder());
        messageAdapter = new MessageAdapter(this, getLayoutInflater(), main.getMessages());

        productSelectionGrid.setAdapter(productAdapter);
        productSelectionGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product p = main.getProductsPerCategory(availableCategories.get(selectedCategory).getType()).get(i);
                String options = "";

                CheckBox checkBox1 = view.findViewById(R.id.product_option1);
                CheckBox checkBox2 = view.findViewById(R.id.product_option2);

                if(p.getOptions().size() > 0){
                    if (checkBox1.isChecked() && checkBox1.getVisibility() == View.VISIBLE){
                        options = options + " met opties: ";
                        options = options + p.getOptions().get(0);
                        if (p.getOptions().size() > 1 && checkBox2.isChecked()){
                            options = options + ", ";
                            options = options + p.getOptions().get(1);
                        }
                    }
                    else if (checkBox2.isChecked() && checkBox2.getVisibility() == View.VISIBLE){
                        options = options + " met opties: ";
                        options = options + p.getOptions().get(1);
                    }
                }

                main.getCurrentOrder().addOrRemoveProduct(p.getName() +options + "-" +  p.getProductID(), 1);
                String result = Integer.toString(main.getCurrentOrder().getTotalProducts());
                orderSizeNumber.setText(result);

                TextView productAmount = view.findViewById(R.id.product_amount);
                productAmount.setText(Integer.toString(p.getAmount()));
                Animation click = AnimationUtils.loadAnimation(MainActivity.this, R.anim.product_click);
                view.startAnimation(click);
                Animation bop = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bop_cart);

                Log.i("current order", main.getCurrentOrder().getProducts().toString());

                orderButton.startAnimation(bop);
                orderAdapter.updateOrderItems(main.getCurrentOrder());
            }
        });

        categoryAdapter = new CategoryAdapter(getApplicationContext(), getLayoutInflater(), availableCategories);
        productCategoryList.setAdapter(categoryAdapter);
        productCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectedCategory != i) {
                    selectedCategory = i;

                    mainActivityTitle.setText(availableCategories.get(selectedCategory).getType());

                    categoryAdapter.setSelectedCategory(i);
                    categoryAdapter.notifyDataSetChanged();
                    Log.i("test", main.getProductsPerCategory(availableCategories.get(selectedCategory).getType()).toString());
                    productAdapter.updateProductArrayList(main.getProductsPerCategory(availableCategories.get(selectedCategory).getType()), main.getCurrentOrder());
                }
            }
        });

        getProductsTask = new GetProductsTask(this, main.getToken());
        getProductsTask.execute(getString(R.string.get_products));

        setLayout();

        getMessagesTask = new GetMessagesTask(this, main.getToken());

        handler = new Handler();
        getMessages = new Runnable() {
            @Override
            public void run() {
                getMessagesTask.cancel(true);
                getMessagesTask = new GetMessagesTask(MainActivity.this, main.getToken());
                getMessagesTask.execute(getString(R.string.get_messages));
                handler.postDelayed(getMessages, 500000); //wait 4 sec and run again
            }
        };
        handler.post(getMessages);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("prefs", 0);
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("selectedCategory", selectedCategory);

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        Log.i("SAVING MAIN: ", main.toString());
        String json = gson.toJson(main);
        prefsEditor.putString("Main", json);
        prefsEditor.commit();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("prefs", 0);
        super.onRestoreInstanceState(savedInstanceState);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Main", "");
        Log.i("MAIN JSON", json);
        main = gson.fromJson(json, Main.class);
        main.getCurrentOrder().setMain(main);
        Log.i("LOADED MAIN: ", main.toString());
        orderAdapter.updateOrderItems(main.getCurrentOrder());

        selectedCategory = savedInstanceState.getInt("selectedCategory");
        setLayout();
    }

    public void setLayout(){
        String result = "0";
        Log.i("checking if order is null", main.getCurrentOrder().toString());
        if (main.getCurrentOrder() != null){
            result = Integer.toString(main.getCurrentOrder().getTotalProducts());
        }
        orderSizeNumber.setText(result);
        categoryAdapter.setSelectedCategory(selectedCategory);
    }

    public void sendPushNotification(String title, String text) {
        Log.i("MainActivity", "sendPushNotification called: " + text);

        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                new Intent(), // add this
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification_builder;
        NotificationManager notification_manager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "3000";
            CharSequence name = "Channel Name";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            notification_manager.createNotificationChannel(mChannel);
            notification_builder = new NotificationCompat.Builder(this, chanel_id);
        } else {
            notification_builder = new NotificationCompat.Builder(this);
        }
        notification_builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);
                //.build();

        //notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notification_manager.notify(0, notification_builder.build());
    }

    public Main getMain() {
        return main;
    }

    public void switchFragments(Fragment fragment){
        FragmentTransaction transaction;
        transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.replace(R.id.overlay_holder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public Fragment getOrderFragment() {
        return orderFragment;
    }

    public MessageAdapter getMessageAdapter() {
        return messageAdapter;
    }

    public Fragment getStatusFragment() {
        return statusFragment;
    }

    public void updateLayout(){
        orderSizeNumber.setText(Integer.toString(main.getCurrentOrder().getTotalProducts()));
        availableProducts = main.getProductsPerCategory(availableCategories.get(selectedCategory).getType());

        productAdapter.updateProductArrayList(availableProducts, main.getCurrentOrder());
    }

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    public CategoryAdapter getCategoryAdapter() {
        return categoryAdapter;
    }

    public OrderAdapter getOrderAdapter() {
        return orderAdapter;
    }

    @Override
    public void onProductsAvailable(ArrayList<Category> productsPerCategory) {
        //main.setReset(true);
        main.refreshData(productsPerCategory);
        availableCategories.clear();
        availableCategories = main.getCategories();
        if (selectedCategory > availableCategories.size()){
            selectedCategory = 0;
        }
        if (main.getCategories().size() > 0) {
            mainActivityTitle.setText(availableCategories.get(selectedCategory).getType());

            availableProducts.clear();

            availableProducts = main.getProductsPerCategory(availableCategories.get(selectedCategory).getType());
        }
        productAdapter.updateProductArrayList(availableProducts, main.getCurrentOrder());
        categoryAdapter.updateCategoryArrayList(availableCategories);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onMessagesAvailable(ArrayList<Message> messages){
        Message m = main.setMessages(messages);
        if (m != null){
            mySnackbar = Snackbar.make(findViewById(R.id.main), "message from: " + m.getSender(), Snackbar.LENGTH_LONG);
            // get snackbar view
            View snackbarView = mySnackbar.getView();

            // change snackbar text color
            int snackbarTextId = android.support.design.R.id.snackbar_text;
            TextView textView = snackbarView.findViewById(snackbarTextId);
            textView.setTextSize(40);
            mySnackbar.show();

            Animation bop = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bop_cart);

            questionContainer.startAnimation(bop);
            //there is a change, pushing notification
            sendPushNotification(m.getSender(), m.getMessageContent());
            messageAdapter.updateMessageArrayList(main.getMessages());
        }
    }
}


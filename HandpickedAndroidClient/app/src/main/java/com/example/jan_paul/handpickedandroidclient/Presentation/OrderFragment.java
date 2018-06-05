package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.DataAccess.GetProductsTask;
import com.example.jan_paul.handpickedandroidclient.DataAccess.SendOrderTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Logic.Main;
import com.example.jan_paul.handpickedandroidclient.R;

import java.util.Calendar;

public class OrderFragment extends Fragment implements SendOrderTask.OnStatusAvailable{

    private Main main;
    private Button orderSendButton;
    private MainActivity parent;
    private TextView orderComment;
    private ListView orderItemsList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_dialog, container, false);

        // Inflate the layout for this fragment
        parent = (MainActivity)getActivity();

        main = parent.getMain();
        orderSendButton = view.findViewById(R.id.order_send_button);
        orderComment = view.findViewById(R.id.order_comment);
        orderItemsList = view.findViewById(R.id.order_items_list);
        orderItemsList.setAdapter(parent.getOrderAdapter());

        orderComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                main.getCurrentOrder().setMessage(orderComment.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                main.getCurrentOrder().setMessage(orderComment.getText().toString());
            }
        });

        orderSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.getCurrentOrder().setOrderDate(Calendar.getInstance().getTime().toString());
                if (main.validateOrder()) {
                    SendOrderTask sendOrderTask = new SendOrderTask(OrderFragment.this, main.getCurrentOrder());
                    sendOrderTask.execute(getString(R.string.post_order));
                }
                //get callback from main to check for success, than show new view...
            }
        });
        return view;
    }

    @Override
    public void onStatusAvailable(int status){
        Log.i("post", Integer.toString(status));
        if (status == 200){
            //success
            main.setCurrentOrder(new Order(false));
            parent.getOrderAdapter().updateOrderItems(main.getCurrentOrder());
            parent.updateLayout();        }
        if (status == 401){
            //slack error

        }
        else {
            //unknown error
        }
        parent.switchFragments(parent.getStatusFragment());
    }

}
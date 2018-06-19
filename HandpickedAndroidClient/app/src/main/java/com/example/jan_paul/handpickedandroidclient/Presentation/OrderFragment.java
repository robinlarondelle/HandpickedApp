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
import android.widget.Toast;

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
                main = parent.getMain();

                main.getCurrentOrder().setMessage(orderComment.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                main = parent.getMain();

                main.getCurrentOrder().setMessage(orderComment.getText().toString());
            }
        });

        orderSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main = parent.getMain();
                Log.i("SENDING ORDER", main.toString());
                main.getCurrentOrder().setOrderDate(Calendar.getInstance().getTime().toString());


                if (main.validateOrder(main.getCurrentOrder())) {
                    SendOrderTask sendOrderTask = new SendOrderTask(OrderFragment.this, main.getCurrentOrder(), main.getToken());
                    sendOrderTask.execute(getString(R.string.post_order));
                }
                else {
                    Toast.makeText(getActivity(), "Please add at least one product or message.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onStatusAvailable(Integer status){
        main = parent.getMain();

        String statusAsString = "unknown";
        if (status == null){
            statusAsString = "no connection";
        }
        else if (status == 200) {
            //success
            statusAsString = getString(R.string.success_order_message);
            orderComment.setText("");
            main.setReset(true);
            main.refreshData(main.getCategories());
            main.setCurrentOrder(new Order(main, false));
            parent.getOrderAdapter().updateOrderItems(main.getCurrentOrder());
        }
        else if (status == 401){
            //slack error
            statusAsString = getString(R.string.error_send_order);
        }
        else if (status == 412){
            statusAsString = getString(R.string.error_not_registered);
        }
        else {
            //unknown error
            statusAsString = "unknown error";
        }
        main.setLastStatus(statusAsString);
        parent.updateLayout();
        parent.switchFragments(parent.getStatusFragment());
    }
}
package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jan_paul.handpickedandroidclient.DataAccess.SendOrderTask;
import com.example.jan_paul.handpickedandroidclient.Domain.Order;
import com.example.jan_paul.handpickedandroidclient.Logic.Main;
import com.example.jan_paul.handpickedandroidclient.R;

import java.util.Calendar;

public class QuestionFragment extends Fragment implements SendOrderTask.OnStatusAvailable{

    private Main main;
    private Button orderSendButton;
    private MainActivity parent;
    private TextView orderComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container, false);

        // Inflate the layout for this fragment
        parent = (MainActivity)getActivity();

        main = parent.getMain();
        main.setMessage(new Order(main, false));

        orderSendButton = view.findViewById(R.id.order_send_button);
        orderComment = view.findViewById(R.id.order_comment);

        orderComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                main.getMessage().setMessage(orderComment.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                main.getMessage().setMessage(orderComment.getText().toString());
            }
        });

        orderSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.getMessage().setOrderDate(Calendar.getInstance().getTime().toString());
                if (main.validateOrder(main.getMessage())) {
                    SendOrderTask sendOrderTask = new SendOrderTask(QuestionFragment.this, main.getMessage());
                    sendOrderTask.execute(getString(R.string.post_order));
                }
                else {
                    Toast.makeText(getActivity(), "Please add a message.",
                            Toast.LENGTH_LONG).show();
                }
                //get callback from main to check for success, than show new view...
            }
        });
        return view;
    }

    @Override
    public void onStatusAvailable(int status){
        Log.i("post", Integer.toString(status));
        if (status == 200) {
            //success
            main.setMessage(new Order(main, false));
            orderComment.setText("");
        }
        else if (status == 401){
            //slack error

        }
        else {
            //unknown error
        }
        parent.updateLayout();
        parent.switchFragments(parent.getStatusFragment());
    }
}

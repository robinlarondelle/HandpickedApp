package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jan_paul.handpickedandroidclient.Logic.Main;
import com.example.jan_paul.handpickedandroidclient.R;

public class StatusFragment extends Fragment {
    Main main;
    MainActivity parent;
    private TextView status;
    private ConstraintLayout statusHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.send_status, container, false);
        parent = (MainActivity)getActivity();
        main = parent.getMain();
        status = view.findViewById(R.id.status_text);
        statusHolder = view.findViewById(R.id.status_holder);

        status.setText(main.getLastStatus());
        return view;
    }

    public ConstraintLayout getStatusHolder() {
        return statusHolder;
    }
}

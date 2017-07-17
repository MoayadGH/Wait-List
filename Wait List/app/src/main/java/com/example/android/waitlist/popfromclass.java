package com.example.android.waitlist;

import android.app.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.waitlist.R;

/**
 * Created by d7om7 on 7/17/2017.
 */

public class popfromclass extends DialogFragment implements View.OnClickListener {
    Button buLogin;
    View form;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        form=inflater.inflate(R.layout.popform,container,false);

        buLogin=(Button)form.findViewById(R.id.buLogin);
        getDialog().setTitle("please sign in");
        buLogin.setOnClickListener(this);
        return form;
    }


    @Override
    public void onClick(View v) {
        this.dismiss();


    }
}

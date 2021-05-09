package com.example.project.Questionnaire.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.activitis.client.ActivityProfileMenu;
import com.example.project.data.Form;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.gson.Gson;

import java.util.Date;


public class Fragment_d extends Fragment {
    private Button next;
    private CheckBox fragmentD_sign, fragmentD_research, fragmentD_research2;
    private View view;
    private EditText fragmentD_date;
    private MySheredP msp;
    private Gson gson = new Gson();
    private Form form;
    public static boolean isSign=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_d, container, false);
        msp = new MySheredP(getContext());
        findViews();
        getFromMSP();
        fragmentD_date.setText(getDateStr(new Date()));
        return view;
    }


    private String getDateStr(Date date) {
        return date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        checkData();
        putOnMSP();
    }

    private void checkData() {
        form.setDate(fragmentD_date.getText().toString());
        form.setSign(fragmentD_sign.isChecked());
        form.setResearch(fragmentD_research.isChecked());
        form.setResearch2(fragmentD_research2.isChecked());
    }

    private Form getFromMSP() {
        String dataAll = msp.getString(Constants.KEY_FORM_DATA, "NA");
        form = new Form(dataAll);
        return form;
    }

    private void putOnMSP() {
        String jsonForm = gson.toJson(form);
        msp.putString(Constants.KEY_FORM_DATA, jsonForm);

    }

    private void findViews() {
        fragmentD_date = view.findViewById(R.id.fragmentD_date);
        fragmentD_research = view.findViewById(R.id.fragmentD_research);
        fragmentD_research2 = view.findViewById(R.id.fragmentD_research2);
        fragmentD_sign = view.findViewById(R.id.fragmentD_sign);

    }

}
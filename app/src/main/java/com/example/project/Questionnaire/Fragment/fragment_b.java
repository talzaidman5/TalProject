package com.example.project.Questionnaire.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.R;
import com.example.project.data.Form;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragment_b extends Fragment {
    private View view;
    private TextView fragment_b_TXT, test;
    private Button next;
    private CheckBox one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve;
    private MySheredP msp;
    private Gson gson = new Gson();
    private Form form;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.freagment_b, container, false);
        msp = new MySheredP(getContext());

        findViews(view);
        getFromMSP();
        setDes();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        form.setAllDiseases(checkData());
        putOnMSP();
    }

    private void putOnMSP() {
        String jsonForm = gson.toJson(form);
        msp.putString(Constants.KEY_FORM_DATA, jsonForm);

    }

    private Form getFromMSP() {
        String dataAll = msp.getString(Constants.KEY_FORM_DATA, "NA");
        form = new Form(dataAll);
        return form;
    }

    private void setDes() {
        if (form.getAllDiseases().size() > 0) {
            one.setChecked(form.getAllDiseases().get("one"));
            two.setChecked(form.getAllDiseases().get("two"));
            three.setChecked(form.getAllDiseases().get("three"));
            four.setChecked(form.getAllDiseases().get("four"));
            five.setChecked(form.getAllDiseases().get("five"));
            six.setChecked(form.getAllDiseases().get("six"));
            seven.setChecked(form.getAllDiseases().get("seven"));
            eight.setChecked(form.getAllDiseases().get("eight"));
            nine.setChecked(form.getAllDiseases().get("nine"));
            ten.setChecked(form.getAllDiseases().get("ten"));
            eleven.setChecked(form.getAllDiseases().get("eleven"));
            twelve.setChecked(form.getAllDiseases().get("twelve"));
        } else {
            one.setChecked(false);
            two.setChecked(false);
            three.setChecked(false);
            four.setChecked(false);
            five.setChecked(false);
            six.setChecked(false);
            seven.setChecked(false);
            eight.setChecked(false);
            nine.setChecked(false);
            ten.setChecked(false);
            eleven.setChecked(false);
            twelve.setChecked(false);
        }
    }


    private void findViews(View view) {
        one = view.findViewById(R.id.one);
        two = view.findViewById(R.id.two);
        three = view.findViewById(R.id.three);
        four = view.findViewById(R.id.four);
        five = view.findViewById(R.id.five);
        six = view.findViewById(R.id.six);
        seven = view.findViewById(R.id.seven);
        eight = view.findViewById(R.id.eight);
        nine = view.findViewById(R.id.nine);
        ten = view.findViewById(R.id.ten);
        eleven = view.findViewById(R.id.eleven);
        twelve = view.findViewById(R.id.twelve);
        test = view.findViewById(R.id.test);
    }


    private Map checkData() {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("one", one.isChecked());
        map.put("two", two.isChecked());
        map.put("three", three.isChecked());
        map.put("four", four.isChecked());
        map.put("five", five.isChecked());
        map.put("six", six.isChecked());
        map.put("seven", seven.isChecked());
        map.put("eight", eight.isChecked());
        map.put("nine", nine.isChecked());
        map.put("ten", ten.isChecked());
        map.put("eleven", eleven.isChecked());
        map.put("twelve", twelve.isChecked());
        form.setAllDiseases(map);
        return map;
    }


}


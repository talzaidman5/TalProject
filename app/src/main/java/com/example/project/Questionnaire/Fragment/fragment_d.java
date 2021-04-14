package com.example.project.Questionnaire.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project.R;


public class fragment_d extends Fragment {
    private Button next;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.fragment_d,container,false);


        return view;
    }
}
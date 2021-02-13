package com.example.project.Questionnaire.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;

public class fragment_b extends Fragment {
    private View view;
    private TextView fragment_b_TXT;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.freagment_b,container,false);


        findViews(view);
        return view;
    }

    private void findViews(View view) {
        //fragment_b_TXT = view.findViewById(R.id.fragment_b_TXT);
    }




}
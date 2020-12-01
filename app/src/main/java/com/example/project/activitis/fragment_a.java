package com.example.project.activitis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;

public class fragment_a extends Fragment {
    private View view;
    private TextView fragment_a_TXT;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.freagment_a,container,false);


        findViews(view);
        return view;
    }

    private void findViews(View view) {
      //  fragment_a_TXT = view.findViewById(R.id.fragment_a_TXT);
    }




}
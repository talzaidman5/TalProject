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

        next = view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if(checkData()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new Fragment_e();
                ft.replace(R.id.main_LIN, fragment);
                ft.addToBackStack(null);
                ft.commit();
                //   }
            }
        });
        return view;
    }
}
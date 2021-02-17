package com.example.project.Questionnaire.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.R;

public class fragment_c extends Fragment {
    private View view;
    private TextView fragment_c_TXT;
    private Button next;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.freagment_c,container,false);

        next = view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if(checkData()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new fragment_d();
                ft.replace(R.id.main_LIN, fragment);
                ft.addToBackStack(null);
                ft.commit();
                //   }
            }
        });
        findViews(view);
        return view;
    }

    private void findViews(View view) {
    }




}
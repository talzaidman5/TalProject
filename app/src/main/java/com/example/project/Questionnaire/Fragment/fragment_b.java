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

public class fragment_b extends Fragment {
    private View view;
    private TextView fragment_b_TXT,test;
    private Button next;
    private CheckBox one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.freagment_b, container, false);

        findViews(view);
        next = view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                   if(checkData()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new fragment_c();
                ft.replace(R.id.main_LIN, fragment);
                ft.addToBackStack(null);
                ft.commit();
                //   }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
        seven = view.findViewById(R.id.eleven);
        twelve = view.findViewById(R.id.twelve);
        test = view.findViewById(R.id.test);
    }


    private boolean checkData() {
        if (one.isChecked() || two.isChecked() || three.isChecked() || four.isChecked() ||
                five.isChecked() || six.isChecked() || seven.isChecked() || eight.isChecked() ||
                nine.isChecked() || ten.isChecked() || eleven.isChecked() || twelve.isChecked())
            return false;
        return true;
    }


}


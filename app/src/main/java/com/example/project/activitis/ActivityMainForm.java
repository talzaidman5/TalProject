package com.example.project.activitis;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.Questionnaire.Fragment.Fragment_e;
import com.example.project.Questionnaire.Fragment.fragment_a;
import com.example.project.Questionnaire.Fragment.fragment_b;
import com.example.project.Questionnaire.Fragment.fragment_c;
import com.example.project.Questionnaire.Fragment.fragment_d;
import com.example.project.R;

public class ActivityMainForm extends AppCompatActivity {
    private ImageView a, b;
    private Button main_form_BTN_prev, main_form_BTN_next;
    private int currentFragment = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        main_form_BTN_next = findViewById(R.id.main_form_BTN_next);
        main_form_BTN_prev = findViewById(R.id.main_form_BTN_prev);
        showA();

        main_form_BTN_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment--;
                openCurrentFragment();
            }
        });
        main_form_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment++;
                openCurrentFragment();
            }
        });

    }

    private void openCurrentFragment(){
        switch (currentFragment) {
            case (0): {
                showA();
            }
            case(1):{
                showB();
            }
            case(2):{
                showC();
            }
            case(3):{
                showD();
            }
            case(4):{
                showE();
            }
        }
    }


        private void showA () {

            fragment_a fragment_a = new fragment_a();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FRAME, fragment_a);
            transaction.commit();
        }

        private void showB () {
            fragment_b fragment_b = new fragment_b();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FRAME, fragment_b);
            transaction.commit();
        }

    private void showC () {
        fragment_c fragment_c = new fragment_c();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME, fragment_c);
        transaction.commit();
    }

    private void showD () {
        fragment_d fragment_d = new fragment_d();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME, fragment_d);
        transaction.commit();
    }

    private void showE () {
        Fragment_e fragment_e = new Fragment_e();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME, fragment_e);
        transaction.commit();
    }


}
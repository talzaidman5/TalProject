package com.example.project.activitis.client;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.Questionnaire.Fragment.Fragment_e;
import com.example.project.Questionnaire.Fragment.Fragment_a;
import com.example.project.Questionnaire.Fragment.Fragment_b;
import com.example.project.Questionnaire.Fragment.Fragment_c;
import com.example.project.Questionnaire.Fragment.Fragment_d;
import com.example.project.R;

public class ActivityMainForm extends AppCompatActivity {
    private Button main_form_BTN_prev, main_form_BTN_next;
    private int currentFragment = 0;
    private CheckBox isSign;

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
                break;
            }
            case(1):{
                showB();
                break;
            }
            case(2):{
                showC();
                break;
            }
            case(3):{
                showD();
                break;
            }
            case(4):{
                showE();
                break;
            }
            default:
                showE();
        }
    }


        private void showA () {
            Fragment_a fragment_a = new Fragment_a();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FRAME, fragment_a);
            transaction.commit();
        }

        private void showB () {
            Fragment_b fragment_b = new Fragment_b();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FRAME, fragment_b);
            transaction.commit();
        }

    private void showC () {
        Fragment_c fragment_c = new Fragment_c();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME, fragment_c);
        transaction.commit();
    }

    private void showD () {
        Fragment_d fragment_d = new Fragment_d();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME, fragment_d);
        transaction.commit();
    }

    private void showE () {
        isSign = findViewById(R.id.fragmentD_sign);

        if (isSign.isChecked()) {

            Fragment_e fragment_e = new Fragment_e();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FRAME, fragment_e);
            transaction.commit();
        }
        else{
            Toast.makeText(getApplicationContext(),"אנא אשר",Toast.LENGTH_LONG).show();

        }
    }


}
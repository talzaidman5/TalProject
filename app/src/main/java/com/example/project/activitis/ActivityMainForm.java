package com.example.project.activitis;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.Questionnaire.Fragment.fragment_a;
import com.example.project.Questionnaire.Fragment.fragment_b;
import com.example.project.R;

public class ActivityMainForm extends AppCompatActivity {
    ImageView a, b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        showA();


    }


    private void showA() {

        fragment_a fragment_a = new fragment_a();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME,fragment_a);
        transaction.commit();
    }

    private void showB() {
        fragment_b fragment_b = new fragment_b();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME,fragment_b);
        transaction.commit();
    }

}
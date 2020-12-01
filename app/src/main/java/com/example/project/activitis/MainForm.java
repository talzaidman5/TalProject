package com.example.project.activitis;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.R;

public class MainForm extends AppCompatActivity {
 ImageView a, b, c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        getSupportActionBar().hide();
        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        showA();
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showC();
            }
        });

a.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showA();
    }
});

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showB();
            }
        });

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
    private void showC() {
        fragment_c fragment_c = new fragment_c();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME,fragment_c);
        transaction.commit();
    }
}
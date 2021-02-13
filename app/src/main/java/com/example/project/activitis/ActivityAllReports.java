package com.example.project.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.project.R;

public class ActivityAllReports extends AppCompatActivity {
    private FrameLayout allReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports);
        allReports = findViewById(R.id.allReports);

        showMyList();
    }


    private void showMyList() {
        AgeFragment ageFragment = new AgeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.allReports, ageFragment);
        transaction.commit();
    }


}
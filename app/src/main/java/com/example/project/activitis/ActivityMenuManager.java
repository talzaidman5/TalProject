package com.example.project.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Questionnaire.ViewFullQuestionnairesActivity;
import com.example.project.R;

public class ActivityMenuManager extends AppCompatActivity {


    private Button menu_BTN_ExportDonations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu_manager);
        getSupportActionBar().hide();
//        menuManager_BTN_publishingReports = findViewById(R.id.menuManager_BTN_publishingReports);
        menu_BTN_ExportDonations = findViewById(R.id.menu_BTN_ExportDonations);

        menu_BTN_ExportDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMenuManager.this, ActivityAllReports.class);
                startActivity(intent);

            }
        });

    }
}
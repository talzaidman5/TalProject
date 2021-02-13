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


    private Button menuManager_BTN_publishingReports,menuManager_BTN_view_full_questionnaires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu_manager);
        getSupportActionBar().hide();
        menuManager_BTN_publishingReports = findViewById(R.id.menuManager_BTN_publishingReports);
        menuManager_BTN_view_full_questionnaires = findViewById(R.id.menuManager_BTN_view_full_questionnaires);
        menuManager_BTN_view_full_questionnaires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMenuManager.this, ViewFullQuestionnairesActivity.class);
                startActivity(intent);

            }
        });
        menuManager_BTN_publishingReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMenuManager.this, ActivityPublishingReportsActivity.class);
                startActivity(intent);
            }
        });
    }
}
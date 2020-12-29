package com.example.project.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.data.Position;
import com.example.project.utils.MySheredP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MenuManager extends AppCompatActivity {


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
                Intent intent = new Intent(MenuManager.this, ViewFullQuestionnairesActivity.class);
                startActivity(intent);

            }
        });
        menuManager_BTN_publishingReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuManager.this, PublishingReportsActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.project.Questionnaire;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.project.R;

public class ViewFullQuestionnairesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_questionnaires);
        getSupportActionBar().hide();

    }
}
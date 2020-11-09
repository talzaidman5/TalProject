package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    Button menu_BTN_fillQ,menu_BTN_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        menu_BTN_fillQ = findViewById(R.id.menu_BTN_fillQ);
        menu_BTN_profile = findViewById(R.id.menu_BTN_profile);
        menu_BTN_fillQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuesPage();
            }
        });
        menu_BTN_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openmenu();
            }
        });
    }


    void openQuesPage(){
        startActivity(new Intent(Menu.this, QuestionnairePage.class));
    }
    void openmenu(){ startActivity(new Intent(Menu.this, MyProfile.class));
    }

}
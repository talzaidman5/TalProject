package com.example.talproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {
    Button profilePage_BTN_fillQ,profilePage_BTN_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.profile_page);
        profilePage_BTN_fillQ = findViewById(R.id.profilePage_BTN_fillQ);
        profilePage_BTN_profile = findViewById(R.id.profilePage_BTN_profile);
        profilePage_BTN_fillQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuesPage();
            }
        });
        profilePage_BTN_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openProfilePage();
            }
        });
    }


    void openQuesPage(){
        startActivity(new Intent(ProfilePage.this, QuestionnairePage.class));
    }
    void openProfilePage(){ startActivity(new Intent(ProfilePage.this, MyProfile.class));
    }

}
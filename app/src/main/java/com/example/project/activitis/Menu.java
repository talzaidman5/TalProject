package com.example.project.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;

public class Menu extends AppCompatActivity {
    Button menu_BTN_fillQ,menu_BTN_profile,menu_BTN_activityPosition;
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
        menu_BTN_activityPosition = findViewById(R.id.menu_BTN_activityPosition);
        menu_BTN_activityPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, activityPosition.class);
                startActivity(intent);
            }
        });
        menu_BTN_fillQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        newEventDialog exNewEventDialog = new newEventDialog();
                        exNewEventDialog.show(getSupportFragmentManager(),"exe");

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
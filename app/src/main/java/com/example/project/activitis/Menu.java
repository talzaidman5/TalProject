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

public class Menu extends AppCompatActivity {
    private static final String KEY_MSP_POS = "allPositions1";
    private MySheredP msp;
    private Gson gson = new Gson();

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");

    Button menu_BTN_fillQ,menu_BTN_profile,menu_BTN_activityPosition;
    private ArrayList<Position> positions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        msp = new MySheredP(this);

        readDataPositions();
        menu_BTN_fillQ = findViewById(R.id.menu_BTN_fillQ);
        menu_BTN_profile = findViewById(R.id.menu_BTN_profile);
        menu_BTN_activityPosition = findViewById(R.id.menu_BTN_activityPosition);
        menu_BTN_activityPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ActivityPosition.class);
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

    public void readDataPositions() {
        myRef.child("ActivityPosition").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Position position = ds.getValue(Position.class);
                    positions.add(position);
                }
                putOnMSP();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }



    private void putOnMSP(){
        String jsonPositions = gson.toJson(positions);
        msp.putString(KEY_MSP_POS,jsonPositions);
    }
}
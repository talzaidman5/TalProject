package com.example.project.activitis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.example.project.R;
import com.example.project.activitis.client.ActivityProfileMenu;
import com.example.project.activitis.manager.ActivityAllReports;
import com.example.project.data.AllUsers;
import com.example.project.data.Position;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FlashScreen extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private AllUsers allUsers = new AllUsers();
    private MySheredP msp;
    private Gson gson = new Gson();
    private ArrayList<Position> positions = new ArrayList<>();
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        getSupportActionBar().hide();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        msp = new MySheredP(this);
        readDataPositions();
    }

    public void readFB() {
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User tempUser = ds.getValue(User.class);
                    allUsers.addToList(tempUser);
                }
                putOnMSP();
                try {
                    userExists();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void readDataPositions() {
        myRef.child("ActivityPosition").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Position position = ds.getValue(Position.class);
                    positions.add(position);
                }
                readFB();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
    private void userExists() throws Exception {
        if (firebaseUser != null) {
            User tempUser = allUsers.getUserByEmail(firebaseUser.getEmail());
            if (tempUser != null) {
                if (tempUser.getUuID() != null) {
                    String json = gson.toJson(tempUser);
                    msp.putString(Constants.KEY_MSP, json);
                    if(tempUser.getID().equals(Constants.MANAGER_ID)) {
                        startActivity(new Intent(FlashScreen.this, ActivityAllReports.class));
                        finish();
                    } else {
                        startActivity(new Intent(FlashScreen.this, ActivityProfileMenu.class));
                        finish();
                    }
                }
            }
            else
            {
                msp.putString(Constants.KEY_MSP, null);
                startActivity(new Intent(FlashScreen.this, ActivityLogIn.class));
                finish();
            }
        }
        else {
            startActivity(new Intent(FlashScreen.this, ActivityLogIn.class));
            finish();
        }



    }
    private void putOnMSP() {
        String jsonAllUsers = gson.toJson(allUsers);
        msp.putString(Constants.KEY_MSP_ALL, jsonAllUsers);

        String jsonAllPos = gson.toJson(positions);
        msp.putString(Constants.KEY_MSP_POS, jsonAllPos);
    }
}
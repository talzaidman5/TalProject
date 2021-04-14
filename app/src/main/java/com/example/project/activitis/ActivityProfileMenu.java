package com.example.project.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.data.Position;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivityProfileMenu extends AppCompatActivity {

    private MySheredP msp;
    private Gson gson = new Gson();
    private TextView main_TXT_name;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");

    private Button menu_BTN_fillQ, menu_BTN_profile, menu_BTN_activityPosition, main_BTN_history;
    private ImageButton menu_IMG_fillQ, menu_IMG_profile, menu_IMG_activityPosition, main_IMG_history;
    private ArrayList<Position> positions = new ArrayList<>();
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().hide();
        msp = new MySheredP(this);
        findViews();
        getFromMSP();
        // readDataPositions();
        main_TXT_name.setText("היי " + newUser.getFirstName());
        menu_BTN_activityPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfileMenu.this, ActivityPosition.class);
                startActivity(intent);
            }
        });

        menu_IMG_activityPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfileMenu.this, ActivityPosition.class);
                startActivity(intent);
            }
        });
        menu_BTN_fillQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEventDialog exNewEventDialog = new newEventDialog();
                exNewEventDialog.show(getSupportFragmentManager(), "exe");


            }
        });

        menu_IMG_fillQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEventDialog exNewEventDialog = new newEventDialog();
                exNewEventDialog.show(getSupportFragmentManager(), "exe");


            }
        });

        main_BTN_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistoryPage();
            }
        });

        main_IMG_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistoryPage();
            }
        });

        menu_BTN_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });

        menu_IMG_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });
    }

    private void findViews() {
        menu_BTN_fillQ = findViewById(R.id.menu_BTN_fillQ);
        menu_BTN_profile = findViewById(R.id.menu_BTN_profile);
        menu_BTN_activityPosition = findViewById(R.id.menu_BTN_activityPosition);
        main_TXT_name = findViewById(R.id.main_TXT_name);
        main_BTN_history = findViewById(R.id.main_BTN_history);
        menu_IMG_fillQ = findViewById(R.id.menu_IMG_fillQ);
        menu_IMG_profile = findViewById(R.id.menu_IMG_profile);
        menu_IMG_activityPosition = findViewById(R.id.menu_IMG_activityPosition);
        main_IMG_history = findViewById(R.id.main_IMG_history);
    }


    void openHistoryPage() {
        startActivity(new Intent(ActivityProfileMenu.this, ActivityHistoryBloodDonations.class));
    }

    void openMenu() {
        startActivity(new Intent(ActivityProfileMenu.this, ActivityMyProfile.class));
    }

//    public void readDataPositions() {
//        myRef.child("ActivityPosition").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Position position = ds.getValue(Position.class);
//                    positions.add(position);
//                }
//                putOnMSP();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });
//
//    }

    private void getFromMSP() {
        String dataPos = msp.getString(Constants.KEY_MSP_POS, "NA");
        positions = new Gson().fromJson(dataPos, ArrayList.class);

        String data = msp.getString(Constants.KEY_MSP, "NA");
        newUser = new User(data);

    }

    private void putOnMSP() {
        String jsonPositions = gson.toJson(positions);
        msp.putString(Constants.KEY_MSP_POS, jsonPositions);
    }

    public static class ActivityQuestionnairePage extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
            setContentView(R.layout.questionnaire_page);
        }
    }
}
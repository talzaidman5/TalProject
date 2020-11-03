package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class MainPage extends AppCompatActivity {
    public static final String KEY_MSP  = "user";
    public static final String KEY_MSP_ALL  = "allUsers1";

    private Button main_page_BTN_signUp, mainPage_BTN_signIn;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private AllUsers allUsers = new AllUsers();
    private User newUser;
    private EditText mainPage_EDIT_id, mainPage_EDIT_password;
    private MySheredP msp;
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_page);
        getSupportActionBar().hide();

        mainPage_EDIT_id = findViewById(R.id.mainPage_EDIT_id);
        mainPage_BTN_signIn = findViewById(R.id.mainPage_BTN_signIn);
        mainPage_EDIT_password = findViewById(R.id.mainPage_EDIT_password);
        main_page_BTN_signUp = findViewById(R.id.main_page_BTN_signUp);
        msp = new MySheredP(this);
      //  getFromMSP();
        mainPage_BTN_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFromFireBase();
            }
        });
        main_page_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();
            }
        });

    }

    private AllUsers getFromMSP(){
        String data  = msp.getString(KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(data);
        return allUsers;
    }
    public void openSignUpPage() {

        startActivity(new Intent(MainPage.this, SignUpPage.class));
    }

    public void readFromFireBase() {
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User tempUser = ds.getValue(User.class);
                    allUsers.addToList(tempUser);
                }
                if (allUsers.getAllUser().size()!= 0) {
                    newUser = allUsers.getUserByID(mainPage_EDIT_id.getText().toString());
                    putOnMSP();

                    if (newUser != null)
                    if (newUser.getPassword().equals(mainPage_EDIT_password.getText().toString()))
                        startActivity(new Intent(MainPage.this, ProfilePage.class));
                    else
                        mainPage_EDIT_id.setText("invalid");
                    else
                        mainPage_EDIT_id.setText("invalid");
                }
                else
                    mainPage_EDIT_id.setText("invalid");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

        });

    }
    private void putOnMSP(){
        String jsonAll = gson.toJson(allUsers);
        String json = gson.toJson(newUser);
        msp.putString(KEY_MSP_ALL,jsonAll);
        msp.putString(KEY_MSP,json);
    }
}
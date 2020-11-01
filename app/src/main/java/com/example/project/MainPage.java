package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {

    private Button main_page_BTN_signUp,mainPage_BTN_signIn;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    public static User currentUser;
    public static AllUsers allUsers;
    private EditText mainPage_EDIT_id,mainPage_EDIT_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allUsers = new AllUsers();
        readFromFireBase(myRef);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_page);
        mainPage_EDIT_id= findViewById(R.id.mainPage_EDIT_id);
        mainPage_BTN_signIn= findViewById(R.id.mainPage_BTN_signIn);
        mainPage_EDIT_password= findViewById(R.id.mainPage_EDIT_password);
        main_page_BTN_signUp = findViewById(R.id.main_page_BTN_signUp);

        mainPage_BTN_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainPage_EDIT_id!=null &&mainPage_EDIT_password != null){
                    User temp = allUsers.checkUser(mainPage_EDIT_id.getText().toString());
                    if(temp!=null) {
                        if (temp.getPassword().equals(mainPage_EDIT_password.getText().toString())) {
                            currentUser = temp;
                            startActivity(new Intent(MainPage.this, ProfilePage.class));
                        }
                    }
                }
                else
                    Toast.makeText(MainPage.this, "Error data!", Toast.LENGTH_SHORT).show();

            }
        });
        main_page_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();
            }

        });

    }


    void openSignUpPage() {
        startActivity(new Intent(MainPage.this, SignUpPage.class));
    }

    void readFromFireBase(DatabaseReference myRef) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allUsers = dataSnapshot.getValue(AllUsers.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
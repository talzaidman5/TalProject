package com.example.talproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MyProfile extends AppCompatActivity {

    private EditText myProfile_TXT_nameToFill;
    private TextView myProfile_TXT_IDToFill;
    private Button myProfile_BTN_back;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private Button myProfile_BTN_nameEdit,myProfile_BTN_nameDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        myProfile_BTN_back =findViewById(R.id.myProfile_BTN_back);
        myProfile_TXT_IDToFill=findViewById(R.id.myProfile_TXT_IDToFill);
        myProfile_TXT_nameToFill = findViewById(R.id.myProfile_TXT_nameToFill);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                myProfile_TXT_nameToFill.setText(value.getFullName());
                myProfile_TXT_IDToFill.setText(value.getID());
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        myProfile_BTN_nameEdit=findViewById(R.id.myProfile_BTN_nameEdit);
        myProfile_BTN_nameDone=findViewById(R.id.myProfile_BTN_nameDone);
        myProfile_BTN_nameDone.setVisibility(View.INVISIBLE);
        myProfile_TXT_nameToFill.setEnabled(false);

        myProfile_BTN_nameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProfile_BTN_nameDone.setVisibility(View.VISIBLE);
                myProfile_BTN_nameEdit.setVisibility(View.INVISIBLE);
                myProfile_TXT_nameToFill.setEnabled(true);
                myProfile_BTN_nameDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: to search the user into the DB and change the details
                        myProfile_TXT_nameToFill.setEnabled(false);
                        myProfile_BTN_nameEdit.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}
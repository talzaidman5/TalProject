package com.example.project;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activityPosition extends AppCompatActivity {

    private TableRow table_row1;
    private TextView date1,address1,endHour1,startHour1;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private ArrayList<Position>allPositions= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        getSupportActionBar().hide();
        date1=findViewById(R.id.date1);
        address1=findViewById(R.id.address1);
        endHour1=findViewById(R.id.endHour1);
        startHour1=findViewById(R.id.startHour1);
        readData();
        if(allPositions.size()!=0)
            updateData();

    }

    private void readData() {
        myRef.child("ActivityPosition").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Position position = ds.getValue(Position.class);
                    allPositions.add(position);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

     }

    private void updateData() {
        date1.setText(allPositions.get(0).getDate().toString());
        address1.setText(allPositions.get(0).getCityName()+" "+allPositions.get(0).getStreetName()+" "+allPositions.get(0).getNumber());
        startHour1.setText(allPositions.get(0).getStartHour());
        endHour1.setText(allPositions.get(0).getEndHour());
    }
}
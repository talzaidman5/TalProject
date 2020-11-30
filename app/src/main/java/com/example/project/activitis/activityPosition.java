package com.example.project.activitis;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.data.Position;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activityPosition extends AppCompatActivity {

    private TableRow table_row1;
    private TableLayout table_layout;
    private TextView date1,address1,endHour1,startHour1;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private ArrayList<Position>allPositions= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        getSupportActionBar().hide();
      //  date1=findViewById(R.id.date1);
     //   address1=findViewById(R.id.address1);
     //   endHour1=findViewById(R.id.endHour1);
     //   startHour1=findViewById(R.id.startHour1);
        table_layout=findViewById(R.id.table_layout);

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
                updateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateData() {
        for (int i = 0; i < allPositions.size(); i++) {
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            date1 = new TextView(this);
            date1.setText(allPositions.get(i).getDate()+"");
            row.addView(date1);

            address1 = new TextView(this);
            address1.setText(allPositions.get(i).getStreetName()+"");
            row.addView(address1);

            endHour1 = new TextView(this);
            endHour1.setText(allPositions.get(i).getEndHour()+"");
            row.addView(endHour1);

            startHour1 = new TextView(this);
            startHour1.setText(allPositions.get(i).getStartHour()+"");
            row.addView(startHour1);
            table_layout.addView(row,i);
        }



      /*  date1.setText(allPositions.get(0).getDate().toString());
        address1.setText(allPositions.get(0).getCityName()+" "+allPositions.get(0).getStreetName()+" "+allPositions.get(0).getNumber());
        startHour1.setText(allPositions.get(0).getStartHour());
        endHour1.setText(allPositions.get(0).getEndHour());*/
    }
}
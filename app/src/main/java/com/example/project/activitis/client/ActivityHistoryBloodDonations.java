package com.example.project.activitis.client;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.data.Adapter_History;
import com.example.project.data.BloodDonation;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivityHistoryBloodDonations extends AppCompatActivity {
    private RecyclerView history_LST_past;
    private TextView history_TXT_empty;
    private ArrayList<BloodDonation> bloodDonations;
    private User user;
    private MySheredP msp;
    private Gson gson = new Gson();
    private ImageView history_IMG_empty;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private ArrayList<BloodDonation> bloodDonationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();
        msp = new MySheredP(this);

        findViews();
        getFromMSP();
     //   showAll();
           getFromFB();
    }

    private void getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP, "NA");
        user = new User(data);
    }
    public void getFromFB() {
    myRef.child("Blood donations").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            bloodDonationList.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                BloodDonation bloodDonation = ds.getValue(BloodDonation.class);
                if (bloodDonation.getUserID().equals(user.getID()))
                    bloodDonationList.add(bloodDonation);
            }
            showAll();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    });

}
    public void showAll() {
        Log.d("count", user.getCountBloodDonations()+"");
        Log.d("count", bloodDonationList.size()+"");

        if (user.getCountBloodDonations() > 0) {
            history_IMG_empty.setVisibility(View.INVISIBLE);
            Adapter_History adapter_history = new Adapter_History(this, bloodDonationList);
            history_LST_past.setLayoutManager(new LinearLayoutManager(this));
            history_LST_past.setItemAnimator(new DefaultItemAnimator());
            history_LST_past.setAdapter(adapter_history);
        } else {
            history_TXT_empty.setText("לא קיימים נתונים במערכת");
            history_IMG_empty.setVisibility(View.VISIBLE);
        }
    }

    void findViews(){
        history_LST_past = findViewById(R.id.history_LST_past);
        history_TXT_empty = findViewById(R.id.history_TXT_empty);
        history_IMG_empty = findViewById(R.id.history_IMG_empty);

    }
}
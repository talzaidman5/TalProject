package com.example.project.activitis.client;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();
        msp = new MySheredP(this);

        findViews();
        getFromMSP();
        showAll();

    }

    private void getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP, "NA");
        user = new User(data);
    }

    public void showAll() {
        if (user.getAllBloodDonations().size() > 0) {
            history_IMG_empty.setVisibility(View.INVISIBLE);
            Adapter_History adapter_history = new Adapter_History(this, user.getAllBloodDonations());
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
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
import com.example.project.data.Adapter_Position;
import com.example.project.data.Position;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ActivityPosition extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private RecyclerView main_LST_news;
    ArrayList<Position> positions = new ArrayList<>();
    private MySheredP msp;
    private Gson gson = new Gson();
    private ImageView activityPosition_IMG_empty;
    private TextView activityPosition_TXT_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        getSupportActionBar().hide();

        msp = new MySheredP(this);

        findViews();
        positions = getFromMSP();

        showAll();

    }

    private void findViews() {
        main_LST_news = findViewById(R.id.main_LST_news);
        activityPosition_IMG_empty = findViewById(R.id.activityPosition_IMG_empty);
        activityPosition_TXT_empty = findViewById(R.id.activityPosition_TXT_empty);
    }

    public ArrayList<Position> getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP_POS, "NA");
        if (!data.equals("NA")) {
            TypeToken<ArrayList<Position>> token = new TypeToken<ArrayList<Position>>() {
            };
            positions = gson.fromJson(data, token.getType());
        }
        return positions;
    }

    public void showAll() {
        if (positions.size() > 0) {
            activityPosition_IMG_empty.setVisibility(View.INVISIBLE);

            Adapter_Position adapter_position = new Adapter_Position(this, positions);
            main_LST_news.setLayoutManager(new LinearLayoutManager(this));
            main_LST_news.setItemAnimator(new DefaultItemAnimator());
            main_LST_news.setAdapter(adapter_position);
        } else {
            activityPosition_TXT_empty.setText("לא קיימים נתונים במערכת");
            activityPosition_IMG_empty.setVisibility(View.VISIBLE);
        }

    }

}
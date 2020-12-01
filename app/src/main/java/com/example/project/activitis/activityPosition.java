package com.example.project.activitis;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.data.Adapter_Position;
import com.example.project.data.Position;
import com.example.project.utils.MySheredP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class activityPosition extends AppCompatActivity {

    private TableRow table_row1;
    private TableLayout table_layout;
    private TextView date1,address1,endHour1,startHour1;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
 //   private ArrayList<Position>allPositions= new ArrayList<>();
    private RecyclerView main_LST_news;
    ArrayList<Position> positions = new ArrayList<>();
    private  MySheredP msp;
    private  final String KEY_MSP_POS = "allPositions";
    private  Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        getSupportActionBar().hide();
      //  date1=findViewById(R.id.date1);
     //   address1=findViewById(R.id.address1);
     //   endHour1=findViewById(R.id.endHour1);
     //   startHour1=findViewById(R.id.startHour1);
      //  table_layout=findViewById(R.id.table_layout);
        msp= new MySheredP(this);
        main_LST_news = findViewById(R.id.main_LST_news);

        positions = getFromMSP();
        Adapter_Position adapter_position = new Adapter_Position(this, positions);
        main_LST_news.setLayoutManager(new LinearLayoutManager(this));
        main_LST_news.setItemAnimator(new DefaultItemAnimator());
        main_LST_news.setAdapter(adapter_position);


    }

    public  ArrayList<Position> getFromMSP(){
        String data  = msp.getString(KEY_MSP_POS, "NA");
        if (!data.equals("NA"))
        {
            TypeToken< ArrayList<Position>> token = new TypeToken<ArrayList<Position>>() {};
            positions = gson.fromJson(data, token.getType());
        }
        //   String data  = msp.getString(KEY_MSP_POS, "NA");
        // positions = new Gson().fromJson(data, ArrayList<Position>.class);

        return positions;
    }


}
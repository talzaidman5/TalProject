package com.example.project.data;

import android.content.Context;

import com.example.project.utils.MySheredP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DataManager {
    static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    static final DatabaseReference myRef = database.getReference("message");
   private static ArrayList<Position> positions = new ArrayList<>();

    private static Context context;
    private static MySheredP msp= new MySheredP(context);
    private static final String KEY_MSP_POS = "allPositions";
    private static Gson gson = new Gson();


    public static ArrayList<Position> generateArticles() {
        Position temp = new Position();
                temp.setCityName("or");
                positions.add(temp);


        for (int i = 0; i < 20; i++) {
            Position tempi = new Position();
            tempi.setCityName(i+"");
            positions.add(tempi);
        }
        return positions;
    }



    public static ArrayList<Position> getFromMSP(){
        String data  = msp.getString(KEY_MSP_POS, "NA");
if (!data.equals("NA"))
{
    TypeToken< ArrayList<Position>> token = new TypeToken<ArrayList<Position>>() {};
         positions = gson.fromJson(data, token.getType());
}
        return positions;
    }
}
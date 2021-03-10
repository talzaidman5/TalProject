package com.example.project.activitis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.project.R;
import com.example.project.data.BloodDonation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActivityAllReports extends AppCompatActivity {
    private FrameLayout allReports;
    private Spinner main_BTN_allOptionToExport;
    private Button activity_all_reports_BTN_export;
    private AnyChartView anyChartView;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private ArrayList<BloodDonation>bloodDonationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports);
        readDataFB("Blood donations");
        allReports = findViewById(R.id.allReports);
        activity_all_reports_BTN_export = findViewById(R.id.activity_all_reports_BTN_export);
        main_BTN_allOptionToExport = findViewById(R.id.activity_all_reports_BTN_allOptionToExport);
        anyChartView = findViewById(R.id.activity_all_reports_CHART_chart);
        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this, R.array.export, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_BTN_allOptionToExport.setAdapter(adapterBloodTypes);
        activity_all_reports_BTN_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ( main_BTN_allOptionToExport.getSelectedItem().toString()){
                    case "איזור":
                    {
                        Map<String, Integer> mapToLists=new HashMap<>();
                        mapToLists = returnLists(bloodDonationList, "איזור");
                        setupPie(mapToLists.keySet().toArray(new String[mapToLists.keySet().size()]),mapToLists.values().toArray(new Integer[mapToLists.values().size()]));

                    }

                }
            }
        });
    }


    private Map<String, Integer> returnLists(ArrayList<BloodDonation> allBloodDonation, String type) {
        Map<String, Integer> bloodDonations = new HashMap<String, Integer>();
        boolean isAdd=false;
        switch (type){
            case "איזור":
                for (BloodDonation bloodDonation: allBloodDonation) {
                    if(bloodDonations.containsKey(bloodDonation.getCity()))
                        bloodDonations.put(bloodDonation.getCity(), bloodDonations.get(bloodDonations.get(bloodDonation.getCity()) + 1));
                    else
                        bloodDonations.put(bloodDonation.getCity(),1);
                }
        }
        return bloodDonations;
    }

    public void readDataFB(String type) {
        myRef.child("Blood donations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    BloodDonation bloodDonation = ds.getValue(BloodDonation.class);
                    bloodDonationList.add(bloodDonation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void setupPie(String [] list, Integer [] present){
        Pie pie = AnyChart.pie();
        List<DataEntry>dataEntries = new ArrayList<>();
        Toast.makeText(ActivityAllReports.this,list[0].toString(),Toast.LENGTH_SHORT).show();

        for (int i=0;i<list.length;i++){
            dataEntries.add(new ValueDataEntry(list[i],present[i]));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }




}
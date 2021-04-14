package com.example.project.activitis.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.BloodDonation;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAllReports extends AppCompatActivity {
    private Spinner main_BTN_allOptionToExport;
    private Button activity_all_reports_BTN_export;
    private AnyChartView anyChartView;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private ArrayList<BloodDonation>bloodDonationList = new ArrayList<>();
    private TextView activity_all_reports_TXT_date,activity_all_reports_TXT_data;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public  Date date;
    private MySheredP msp;
    private AllUsers allUsers;
    private Map<String, Integer> bloodDonations = new HashMap<String, Integer>();
    private List<User>bloodDonationsUsers = new ArrayList<>();
    private Map<User.GENDER, Integer> bloodDonationsGander = new HashMap<User.GENDER, Integer>();
    private Map<String, Integer> bloodDonationsBlood = new HashMap<String, Integer>();
    private ProgressBar activity_all_reports_PRB_progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports);
        readDataFB("Blood donations");
        activity_all_reports_TXT_data = findViewById(R.id.activity_all_reports_TXT_data);
        activity_all_reports_PRB_progressBar = findViewById(R.id.activity_all_reports_PRB_progressBar);
        msp = new MySheredP(this);
        activity_all_reports_PRB_progressBar.setVisibility(View.INVISIBLE);
        getFromMSP();
        getSupportActionBar().hide();
        activity_all_reports_BTN_export = findViewById(R.id.activity_all_reports_BTN_export);
        activity_all_reports_TXT_date = findViewById(R.id.activity_all_reports_TXT_date);
        main_BTN_allOptionToExport = findViewById(R.id.activity_all_reports_BTN_allOptionToExport);
        anyChartView = findViewById(R.id.activity_all_reports_CHART_chart);
        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this, R.array.export, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_BTN_allOptionToExport.setAdapter(adapterBloodTypes);
        activity_all_reports_TXT_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(ActivityAllReports.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date date = calendar.getTime();

                String finalDate = getDateStr(date);
                activity_all_reports_TXT_date.setText(finalDate);

            }
        };
        main_BTN_allOptionToExport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(parentView.getItemAtPosition(position).equals("תאריך"))
                    activity_all_reports_TXT_date.setVisibility(View.VISIBLE);
                else
                    activity_all_reports_TXT_date.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        activity_all_reports_BTN_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_all_reports_PRB_progressBar.setVisibility(View.VISIBLE);

                switch ( main_BTN_allOptionToExport.getSelectedItem().toString()){
                    case "איזור":
                    {
                         returnLists(bloodDonationList, "איזור");
                        setupPie(bloodDonations.keySet().toArray(new String[bloodDonations.keySet().size()]),bloodDonations.values().toArray(new Integer[bloodDonations.values().size()]));

                    }break;
                    case "תאריך":
                        if(!activity_all_reports_TXT_date.getText().equals("בחר תאריך")) {
                            returnLists(bloodDonationList, "תאריך");
                            setupResourceChart(bloodDonationsUsers);
                        }break;
                    case "מגדר": {
                        returnLists(bloodDonationList, "מגדר");
                        setupPie(returnListString(bloodDonationsGander), returnListInteger(bloodDonationsGander));
                    }break;
                    case "סוג דם": {
                        returnLists(bloodDonationList, "סוג דם");
                        setupPie(returnListStringBlood(bloodDonationsBlood), returnListIntegerBlood(bloodDonationsBlood));
                    }break;
                }
            }
        });
    }

    private String[] returnListString(Map<User.GENDER, Integer>map){
        String[]strings = new String[map.size()];
            for(int i=0;i<map.size();i++)
            strings[i]=map.keySet().toArray()[i].toString();

        return strings;
    }
    private String[] returnListStringBlood(Map<String, Integer>map){
        String[]strings = new String[map.size()];
            for(int i=0;i<map.size();i++)
            strings[i]=map.keySet().toArray()[i].toString();

        return strings;
    }

    private String getDateStr (Date date){
        return date.getDay()+"/"+date.getMonth()+"/"+date.getYear();
    }


    private Integer[] returnListInteger(Map<User.GENDER, Integer>map){
        Integer[]integers = new Integer[map.size()];
            for(int i=0;i<map.size();i++)
                integers[i]=(Integer) map.values().toArray()[i];

        return integers;
    }
    private Integer[] returnListIntegerBlood(Map<String, Integer>map){
        Integer[]integers = new Integer[map.size()];
            for(int i=0;i<map.size();i++)
                integers[i]=(Integer) map.values().toArray()[i];

        return integers;
    }
    private void returnLists(ArrayList<BloodDonation> allBloodDonation, String type) {
        switch (type) {
            case "איזור":
                activity_all_reports_TXT_data.setVisibility(View.INVISIBLE);
                for (BloodDonation bloodDonation : allBloodDonation) {
                    if (bloodDonations.containsKey(bloodDonation.getCity()))
                        bloodDonations.put(bloodDonation.getCity(), bloodDonations.get(bloodDonation.getCity()) + 1);
                    else
                        bloodDonations.put(bloodDonation.getCity(), 1);
                }break;
                case "מגדר":
                    activity_all_reports_TXT_data.setVisibility(View.INVISIBLE);
                    for (BloodDonation bloodDonation : allBloodDonation) {
                    if (bloodDonationsGander.containsKey(allUsers.getUserByID(bloodDonation.getUserID()).getGender()))
                        bloodDonationsGander.put(allUsers.getUserByID(bloodDonation.getUserID()).getGender(),bloodDonationsGander.get(allUsers.getUserByID(bloodDonation.getUserID()).getGender())+ 1);
                    else
                        bloodDonationsGander.put(allUsers.getUserByID(bloodDonation.getUserID()).getGender(), 1);

                } break;
            case "תאריך":
                for (BloodDonation bloodDonation : allBloodDonation) {
                    if (bloodDonation.getDate().getDay()==date.getDay() &&bloodDonation.getDate().getMonth()==date.getMonth() &&
                            bloodDonation.getDate().getYear()==date.getYear())
                        bloodDonationsUsers.add(allUsers.getUserByID(bloodDonation.getUserID()));
                }break;
                case "סוג דם":
                for (BloodDonation bloodDonation : allBloodDonation) {
                    String[] stringArray = getResources().getStringArray(R.array.bloods);
                    if (bloodDonations.containsKey(allUsers.getUserByID(bloodDonation.getUserID()).getBloodType()))
                        bloodDonationsBlood.put(stringArray[allUsers.getUserByID(bloodDonation.getUserID()).getBloodType()], bloodDonationsBlood.get(allUsers.getUserByID(bloodDonation.getUserID()).getBloodType()) + 1);
                    else
                        bloodDonationsBlood.put(stringArray[allUsers.getUserByID(bloodDonation.getUserID()).getBloodType()], 1);
                }break;

        }
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

    public void setupPie(String [] list, Integer [] present) {
        if (list.length != 0) {
            Pie pie = AnyChart.pie();
            List<DataEntry> dataEntries = new ArrayList<>();
            for (int i = 0; i < list.length; i++) {
                dataEntries.add(new ValueDataEntry(list[i], present[i]));
            }
            pie.data(dataEntries);
            activity_all_reports_PRB_progressBar.setVisibility(View.INVISIBLE);
            anyChartView.setChart(pie);
        }
        else
        {
            Toast.makeText(this, "אין מידע", Toast.LENGTH_SHORT).show();
        }
    }
 public void setupResourceChart(List<User>allUsersTemp) {
     activity_all_reports_PRB_progressBar.setVisibility(View.INVISIBLE);
     if (allUsersTemp.size() != 0)
         activity_all_reports_TXT_data.setText("תרמו ביום הנבחר " + (allUsersTemp.size()) + " התרמות דם");
      else
         Toast.makeText(this, "אין מידע", Toast.LENGTH_SHORT).show();
 }

    private AllUsers getFromMSP() {
        String dataAll = msp.getString(Constants.KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(dataAll);
        return allUsers;
    }


}
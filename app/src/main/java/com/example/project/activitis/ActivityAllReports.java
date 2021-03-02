package com.example.project.activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityAllReports extends AppCompatActivity {
    private FrameLayout allReports;
    private Spinner main_BTN_allOptionToExport;
    private Button activity_all_reports_BTN_export;
    private AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports);
        allReports = findViewById(R.id.allReports);
        activity_all_reports_BTN_export = findViewById(R.id.activity_all_reports_BTN_export);
        main_BTN_allOptionToExport = findViewById(R.id.activity_all_reports_BTN_allOptionToExport);
        anyChartView = findViewById(R.id.activity_all_reports_CHART_chart);
        setupPie();
        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this, R.array.export, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_BTN_allOptionToExport.setAdapter(adapterBloodTypes);
        activity_all_reports_BTN_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_BTN_allOptionToExport.getSelectedItem().toString();
            }
        });
    }

    public void setupPie(){
        String [] day = {"1","2","3"};
        int [] preisent = {500,800,2000};

        Pie pie = AnyChart.pie();
        List<DataEntry>dataEntries = new ArrayList<>();

        for (int i=0;i<day.length;i++){
            dataEntries.add(new ValueDataEntry(day[i],preisent[i]));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }




}
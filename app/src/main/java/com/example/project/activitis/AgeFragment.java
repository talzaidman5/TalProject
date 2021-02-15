package com.example.project.activitis;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.BloodDonation;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;

import java.util.ArrayList;
import java.util.List;

public class AgeFragment extends Fragment {
    private View view;
    private MySheredP msp;
    private AllUsers allUsers =  new AllUsers();
    ArrayList<BloodDonation> months;// = {"one", "two", "Three"};
    int[] ear = {500, 800, 10};
    AnyChartView anyChartView;
//        private RecyclerView new_Family_LST_allFamily;
//        private ImageView panel_IMG_back;
//        private User user;
//        private Family family = new Family();
//        FloatingActionButton new_Family_BTN_add;


    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.age_fragment, container, false);
        msp = new MySheredP(getContext());
        anyChartView = view.findViewById(R.id.any_chart_view);
        allUsers = getFromMSP();
        setup();

        return view;
    }


    private AllUsers getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(data);
        return allUsers;
    }

    public void setup() {
        months = allUsers.getAllReportsData();
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntry = new ArrayList<>();
        for (int i = 0; i < months.size(); i++) {
            dataEntry.add(new ValueDataEntry(months.get(i).getCity(), 50));
        }
        pie.data(dataEntry);
        pie.title("OR");
        anyChartView.setChart(pie);
    }
}
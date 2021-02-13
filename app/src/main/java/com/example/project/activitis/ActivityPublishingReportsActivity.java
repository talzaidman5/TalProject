package com.example.project.activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.project.R;

public class ActivityPublishingReportsActivity extends AppCompatActivity {
    private Spinner activity_publishing_reports_SPI_year,activity_publishing_reports_SPI_month,activity_publishing_reports_SPI_day,
            activity_publishing_reports_SPI_BloodDonationPosition,activity_publishing_reports_SPI_age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishing_reports);
        getSupportActionBar().hide();

      initSpinners();

    }

    private void initSpinners() {

        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,
                R.array.year, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_publishing_reports_SPI_year.setAdapter(adapterYear);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this,
                R.array.month, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_publishing_reports_SPI_year.setAdapter(adapterMonth);

        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this,
                R.array.day, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_publishing_reports_SPI_year.setAdapter(adapterDay);

    }

}
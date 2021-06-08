package com.example.project.activitis.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.project.data.Encryption;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAllReports extends AppCompatActivity {
    private Spinner main_BTN_allOptionToExport;
    private Button activity_all_reports_BTN_export,activity_all_reports_BTN_exportPDF;
    private AnyChartView anyChartView;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private ArrayList<BloodDonation> bloodDonationList = new ArrayList<>();
    private TextView activity_all_reports_TXT_date, activity_all_reports_TXT_data;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public Date date;
    private MySheredP msp;
    private AllUsers allUsers;
    private Map<String, Integer> bloodDonations = new HashMap<String, Integer>();
    private List<User> bloodDonationsUsers = new ArrayList<>();
    private Map<User.GENDER, Integer> bloodDonationsGander = new HashMap<User.GENDER, Integer>();
    private Map<String, Integer> bloodDonationsBlood = new HashMap<String, Integer>();
    private String finalDate;
    private PieChart chart;
    private int pageHeight = 1120,pagewidth = 792;
    private Bitmap bmp, scaledbmp;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private ArrayList<PieEntry>arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports);
        readDataFB("Blood donations");
        activity_all_reports_TXT_data = findViewById(R.id.activity_all_reports_TXT_data);
        msp = new MySheredP(this);
        getFromMSP();
        getSupportActionBar().hide();
        findViews();
        arrayList = new ArrayList<>();
        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this, R.array.export, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_BTN_allOptionToExport.setAdapter(adapterBloodTypes);
        chart = (PieChart) findViewById(R.id.activity_all_reports_CHART_chart);
        activity_all_reports_BTN_exportPDF.setVisibility(View.INVISIBLE);
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
                calendar.set(Calendar.MONTH, month+1);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date date = calendar.getTime();

                 finalDate = getDateStr(date);
                activity_all_reports_TXT_date.setText(finalDate);

            }
        };
        main_BTN_allOptionToExport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                activity_all_reports_TXT_date.setVisibility(View.VISIBLE);
                if (parentView.getItemAtPosition(position).equals("תאריך")) {
                    activity_all_reports_TXT_date.setVisibility(View.VISIBLE);
                    chart.setVisibility(View.INVISIBLE);
                } else {
                    activity_all_reports_TXT_date.setVisibility(View.INVISIBLE);
                    chart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        activity_all_reports_BTN_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodDonations = new HashMap<>();
                switch (main_BTN_allOptionToExport.getSelectedItem().toString()) {
                    case "עיר": {
                        try {
                            returnLists(bloodDonationList, "עיר");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setupPie(bloodDonations.keySet().toArray(new String[bloodDonations.keySet().size()]), bloodDonations.values().toArray(new Integer[bloodDonations.values().size()]));

                    }
                    break;
                    case "תאריך":
                        if (!activity_all_reports_TXT_date.getText().equals("בחר תאריך")) {
                            try {
                                returnLists(bloodDonationList, "תאריך");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            setupResourceChart(bloodDonationsUsers);
                        }
                        break;
                    case "מגדר": {
                        try {
                            returnLists(bloodDonationList, "מגדר");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setupPie(returnListString(bloodDonationsGander), returnListInteger(bloodDonationsGander));
                    }
                    break;
                    case "סוג דם": {
                        try {
                            returnLists(bloodDonationList, "סוג דם");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setupPie(returnListStringBlood(bloodDonationsBlood), returnListIntegerBlood(bloodDonationsBlood));
                    }
                    break;
                }
            }
        });
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        activity_all_reports_BTN_exportPDF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        generatePDF();
                    }
                }
        );
        if (!checkPermission())
            requestPermission();
    }

    private void findViews() {
        activity_all_reports_BTN_export = findViewById(R.id.activity_all_reports_BTN_export);
        activity_all_reports_TXT_date = findViewById(R.id.activity_all_reports_TXT_date);
        main_BTN_allOptionToExport = findViewById(R.id.activity_all_reports_BTN_allOptionToExport);
        activity_all_reports_BTN_exportPDF = findViewById(R.id.activity_all_reports_BTN_exportPDF);

    }

    private String[] returnListString(Map<User.GENDER, Integer> map) {
        String[] strings = new String[map.size()];
        for (int i = 0; i < map.size(); i++)
            strings[i] = map.keySet().toArray()[i].toString();

        return strings;
    }

    private String[] returnListStringBlood(Map<String, Integer> map) {
        String[] strings = new String[map.size()];
        for (int i = 0; i < map.size(); i++)
            strings[i] = map.keySet().toArray()[i].toString();

        return strings;
    }

    private String getDateStr(Date date) {
        return date.getDate() + "/" + date.getMonth() + "/" + (date.getYear()+1900);
    }


    private Integer[] returnListInteger(Map<User.GENDER, Integer> map) {
        Integer[] integers = new Integer[map.size()];
        for (int i = 0; i < map.size(); i++)
            integers[i] = (Integer) map.values().toArray()[i];

        return integers;
    }

    private Integer[] returnListIntegerBlood(Map<String, Integer> map) {
        Integer[] integers = new Integer[map.size()];
        for (int i = 0; i < map.size(); i++)
            integers[i] = (Integer) map.values().toArray()[i];

        return integers;
    }

    private void returnLists(ArrayList<BloodDonation> allBloodDonation, String type) throws Exception {
        switch (type) {
            case "עיר":
                activity_all_reports_TXT_data.setVisibility(View.INVISIBLE);
                for (BloodDonation bloodDonation : allBloodDonation) {
                    if (bloodDonations.containsKey(bloodDonation.getCity()))
                        bloodDonations.put(bloodDonation.getCity(), bloodDonations.get(bloodDonation.getCity()) + 1);
                    else
                        bloodDonations.put(bloodDonation.getCity(), 1);
                }
                break;
            case "מגדר":
                activity_all_reports_TXT_data.setVisibility(View.INVISIBLE);
                for (BloodDonation bloodDonation : allBloodDonation) {
                    if (bloodDonationsGander.containsKey(allUsers.getUserByID(bloodDonation.getUserID()).getGender()))
                        bloodDonationsGander.put(allUsers.getUserByID(bloodDonation.getUserID()).getGender(), bloodDonationsGander.get(allUsers.getUserByID(bloodDonation.getUserID()).getGender()) + 1);
                    else
                        bloodDonationsGander.put(allUsers.getUserByID(bloodDonation.getUserID()).getGender(), 1);

                }
                break;
            case "תאריך":
                for (BloodDonation bloodDonation : allBloodDonation) {
                    if (bloodDonation.getDate().equals(finalDate))
                        bloodDonationsUsers.add(allUsers.getUserByID(bloodDonation.getUserID()));
                }
                break;
            case "סוג דם":
                for (BloodDonation bloodDonation : allBloodDonation) {
                    if (bloodDonations.containsKey(Encryption.decrypt(allUsers.getUserByID(bloodDonation.getUserID()).getBloodType())))
                        bloodDonationsBlood.put(Encryption.decrypt(allUsers.getUserByID(bloodDonation.getUserID()).getBloodType()), bloodDonationsBlood.get(allUsers.getUserByID(bloodDonation.getUserID()).getBloodType()) + 1);
                    else
                        bloodDonationsBlood.put(Encryption.decrypt(allUsers.getUserByID(bloodDonation.getUserID()).getBloodType()), 1);
                }
                break;
        }
    }

    public void readDataFB(String type) {
        myRef.child("Blood donations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodDonationList = new ArrayList<>();
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

    public void setupPie(String[] list, Integer[] present) {
        arrayList = new ArrayList<>();
        if (list.length != 0) {
            for (int i = 0; i < list.length; i++) {
                arrayList.add(new PieEntry( present[i],list[i]));
            }
            PieDataSet pieDataSet = new PieDataSet(arrayList,"מקרא");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setValueTextColor(Color.WHITE);
            pieDataSet.setValueTextSize(16f);
            PieData pieData = new PieData(pieDataSet);
            chart.setData(pieData);
            chart.getDescription().setEnabled(false);
            chart.animate();
            Toast.makeText(this, "אנא לחץ על התרשים כדי לצפות בו", Toast.LENGTH_SHORT).show();
            activity_all_reports_BTN_exportPDF.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "אין מידע", Toast.LENGTH_SHORT).show();
            activity_all_reports_BTN_exportPDF.setVisibility(View.INVISIBLE);
        }
    }

    public void setupResourceChart(List<User> allUsersTemp) {
        activity_all_reports_TXT_data.setVisibility(View.VISIBLE);
        if (allUsersTemp.size() != 0)
            activity_all_reports_TXT_data.setText("תרמו " +allUsersTemp.size() + " התרמות דם");
        else
            Toast.makeText(this, "אין מידע", Toast.LENGTH_SHORT).show();
    }

    private AllUsers getFromMSP() {
        String dataAll = msp.getString(Constants.KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(dataAll);
        return allUsers;
    }

    private void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();
        Paint date = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();
        paint.setTextSize(50);
        canvas.drawText("הפקת דוחות לפי "+main_BTN_allOptionToExport.getSelectedItem().toString(), 250, 150, paint);
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        date.setTextSize(15);
        canvas.drawText(Calendar.getInstance().getTime().toString(), 510, 50, date);
        canvas.drawBitmap(scaledbmp, 56, 40, date);

        int x=0;
        title.setTextAlign(Paint.Align.CENTER);

        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextSize(25);
        for (PieEntry pie:arrayList) {
            canvas.drawText(pie.getLabel()+" - "+(int)pie.getValue(), 700, 300+x, title);
            x+=60;
        }

        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory(), "file.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            pdfDocument.close();

            Uri path = Uri.fromFile(file);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent .setType("vnd.android.cursor.dir/email");
            String to[] = {"talzaidman5@gmail.com"};
            emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
            emailIntent .putExtra(Intent.EXTRA_STREAM, path);
            emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
            startActivity(Intent.createChooser(emailIntent , "Send email..."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}


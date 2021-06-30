package com.example.project.activitis.client;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.project.R;
import com.example.project.activitis.ActivityLogIn;
import com.example.project.data.AllUsers;
import com.example.project.data.BloodDonation;
import com.example.project.data.CheckValidation;
import com.example.project.data.Encryption;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ActivityMyProfile extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private TextInputLayout myProfile_TXT_emailToFill, myProfile_TXT_phoneNumberToFill, myProfile_TXT_passwordToFill;
    private TextInputLayout myProfile_TXT_IDToFill, myProfile_TXT_lastDonation, myProfile_SPI_bloodTypes,myProfile_TXT_dateBirthToFill;
    private TextView myProfile_TXT_nameToFill;
    private ImageButton myProfile_BTN_addBloodDonation, myProfile_BTN_logout;
    private MaterialButton myProfile_BTN_edit;
    private MySheredP msp;
    private SwitchCompat my_profile_SWI_can_donate_blood;
    private Gson gson = new Gson();
    private User currentUser = new User();
    private AllUsers allUsers;
    private Date date;
    private Date dateLast;
    private ArrayList<String> spinnerArray;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private Spinner spn_my_spinner;
    private TextView add_blood_donation_TXT_date;
    private boolean editFlag = false;
    private StorageReference storageReference;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        msp = new MySheredP(this);
        getSupportActionBar().hide();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        findView();
        getFromMSP();
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent myIntent = new Intent(getApplicationContext(), NotifyService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60, pendingIntent);

        myProfile_TXT_nameToFill.setEnabled(false);

        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this,
                R.array.bloods, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try {
            myProfile_SPI_bloodTypes.getEditText().setText(Encryption.decrypt(currentUser.getBloodType()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        myProfile_BTN_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editFlag == false) {
                    editFlag = true;

                    changeTextsEnabled(true);
                    myProfile_BTN_edit.setBackgroundResource(R.drawable.save);

                } else {
                    if (checkData()) {
                        changeTextsEnabled(false);
                        myProfile_BTN_edit.setBackgroundResource(R.drawable.pencil);
                        editFlag = false;
                        updateUserInfo();
                        putOnMSP_user(currentUser);
                        Toast.makeText(getApplicationContext(), "הפרטים נשמרו!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        myProfile_BTN_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ActivityMyProfile.this, ActivityLogIn.class));
                finish();

            }
        });
        myProfile_BTN_addBloodDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBloodDonation();
            }
        });


        myProfile_TXT_dateBirthToFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(ActivityMyProfile.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
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
                String finalDate = dayOfMonth + "/" + month + "/" + year;
                myProfile_TXT_dateBirthToFill.getEditText().setText(finalDate);
            }
        };


    }


    private void changeTextsEnabled(boolean status) {
        myProfile_TXT_IDToFill.setEnabled(status);
        myProfile_TXT_passwordToFill.setEnabled(status);
//        myProfile_TXT_dateBirthToFill.setEnabled(status);
        myProfile_TXT_emailToFill.setEnabled(status);
        myProfile_TXT_phoneNumberToFill.setEnabled(status);
    }

    private void updateUserInfo() {
        currentUser.setID(myProfile_TXT_IDToFill.getEditText().getText().toString());
        currentUser.setPassword(myProfile_TXT_passwordToFill.getEditText().getText().toString());
        currentUser.setLastBloodDonation(getStringDate(myProfile_TXT_lastDonation.getEditText().getText().toString()));
        if (date != null)
            currentUser.setBirthDate(date);
        currentUser.setEmail(myProfile_TXT_emailToFill.getEditText().getText().toString());
        currentUser.setPhoneNumber(myProfile_TXT_phoneNumberToFill.getEditText().getText().toString());
    }

    private boolean checkData() {
        boolean data = true;
        if (!CheckValidation.checkID(myProfile_TXT_IDToFill.getEditText().getText().toString())) {
            myProfile_TXT_IDToFill.setError("INVALID ID");
            data = false;
        }
        if (!CheckValidation.checkMail(myProfile_TXT_emailToFill.getEditText().getText().toString())) {
            myProfile_TXT_emailToFill.setError("INVALID EMAIL");
            data = false;
        }
        if (!CheckValidation.checkPhoneNumberISRAEL(myProfile_TXT_phoneNumberToFill.getEditText().getText().toString())) {
            myProfile_TXT_phoneNumberToFill.setError("INVALID PHONE");
            data = false;
        }
        if (myProfile_TXT_passwordToFill.getEditText().getText().length() < 6) {
            myProfile_TXT_passwordToFill.setError("INVALID PASSWORD");
            data = false;
        }

//        if (myProfile_TXT_dateBirthToFill.getEditText().getText().length() == 0) {
//            myProfile_TXT_dateBirthToFill.setError("INVALID DATE");
//            data = false;
//        }
        return data;
    }


    private void addBloodDonation() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_add_blood_donation);
        dialog.setTitle("Title...");
        // set the custom dialog components - text, image and button
        spn_my_spinner = dialog.findViewById(R.id.spinner);

        Button dialogButton = dialog.findViewById(R.id.popup_BTN_add);
        add_blood_donation_TXT_date = dialog.findViewById(R.id.add_blood_donation_TXT_date);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name= "name";
            int impro = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit",name,impro);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        add_blood_donation_TXT_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(ActivityMyProfile.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener2, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String finalDate = dayOfMonth + "/" + month + "/" + (year );
                myProfile_TXT_lastDonation.getEditText().setText(finalDate);
                dateLast = new Date(year-1900,month-1,dayOfMonth);
                currentUser.setLastBloodDonation(dateLast);
                add_blood_donation_TXT_date.setText(finalDate);
            }
        };
        spinnerArray = new ArrayList<String>();

        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spn_my_spinner.setAdapter(spinnerArrayAdapter);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),NotifyService.class);
                PendingIntent pendingIntent =PendingIntent.getBroadcast(getApplicationContext(),0,intent1,0);
                AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtClick =dateLast.getTime(); //System.currentTimeMillis();
                long tenSec = 7884000;

                alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtClick+tenSec,pendingIntent);
                currentUser.setCanDonateBlood(false);
                String userIdEncrypt = null;
                try {
                    userIdEncrypt = Encryption.encrypt(currentUser.getID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentUser.updateCountBlood();

                myRef.child("Users").child(userIdEncrypt).setValue(currentUser);
                Toast.makeText(getApplicationContext(), "תודה שתרמת דם! נשמח לראותך שוב", Toast.LENGTH_SHORT).show();
                try {
                    saveToFirebase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });


       dialog.show();
    }

    private void saveToFirebase() throws Exception {
        BloodDonation bloodDonation = new BloodDonation(spn_my_spinner.getSelectedItem().toString(), getDateStr(dateLast), currentUser.getID());
        String bloodDonationID = currentUser.getID() + "-" + currentUser.getCountBloodDonations();
        bloodDonation.setBooldDonationId(bloodDonationID);
        myRef.child("Users").child(currentUser.getID()).setValue(currentUser);
        myRef.child("Blood donations").child(bloodDonationID).setValue(bloodDonation);
        putOnMSP_user(currentUser);
    }


    public void findView() {
        myProfile_TXT_emailToFill = findViewById(R.id.myProfile_TXT_emailToFill);
        myProfile_TXT_phoneNumberToFill = findViewById(R.id.myProfile_TXT_phoneNumberToFill);
        myProfile_TXT_passwordToFill = findViewById(R.id.myProfile_TXT_passwordToFill);
        myProfile_TXT_nameToFill = findViewById(R.id.myProfile_TXT_nameToFill);
        myProfile_TXT_dateBirthToFill = findViewById(R.id.myProfile_TXT_dateBirthToFill);
        myProfile_TXT_IDToFill = findViewById(R.id.myProfile_TXT_IDToFill);
        myProfile_BTN_edit = findViewById(R.id.myProfile_BTN_edit);
        myProfile_BTN_addBloodDonation = findViewById(R.id.myProfile_BTN_addBloodDonation);
        myProfile_BTN_logout = findViewById(R.id.myProfile_BTN_logout);
        add_blood_donation_TXT_date = findViewById(R.id.add_blood_donation_TXT_date);
        myProfile_SPI_bloodTypes = findViewById(R.id.myProfile_SPI_bloodTypes);
        myProfile_TXT_lastDonation = findViewById(R.id.myProfile_TXT_lastDonation);
        my_profile_SWI_can_donate_blood = findViewById(R.id.my_profile_SWI_can_donate_blood);

    }

    public void initData() throws Exception {
        changeTextsEnabled(false);
        if (currentUser.getID() != null) {
            myProfile_TXT_phoneNumberToFill.getEditText().setText(Encryption.decrypt(currentUser.getPhoneNumber()));
            myProfile_TXT_passwordToFill.getEditText().setText(Encryption.decrypt(currentUser.getPassword()));
            myProfile_TXT_nameToFill.setText(Encryption.decrypt(currentUser.getFirstName()));
            myProfile_TXT_dateBirthToFill.getEditText().setText(getDateStr(currentUser.getBirthDate()));
            myProfile_TXT_IDToFill.getEditText().setText(currentUser.getID());
            myProfile_TXT_emailToFill.getEditText().setText(Encryption.decrypt(currentUser.getEmail()));
            myProfile_TXT_lastDonation.getEditText().setText(getDateStr(currentUser.getLastBloodDonation()));
            my_profile_SWI_can_donate_blood.setChecked(currentUser.getCanDonateBlood());
        }

    }

    private void putOnMSP_user(User s) {
        String json = gson.toJson(s);
        msp.putString(Constants.KEY_MSP, json);
    }

    private String getDateStr(Date date) {
        if (date != null)
            return date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear() + 1900);
        else
            return "N/A";
    }
    private Date getStringDate(String str) {
        String[] s = str.split("/");
        Date date = new Date();
        date.setDate(Integer.parseInt(s[0]));
        date.setMonth(Integer.parseInt(s[1]));
        date.setYear(Integer.parseInt(s[2]));
            return date;
    }

    private AllUsers getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP, "NA");
        String dataAll = msp.getString(Constants.KEY_MSP_ALL, "NA");
        currentUser = new User(data);
        allUsers = new AllUsers(dataAll);
        return allUsers;
    }

    private void readFile() throws IOException {
        BufferedReader reader;

        try {
            final InputStream file = getAssets().open("cities.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                spinnerArray.add(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
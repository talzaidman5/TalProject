package com.example.talproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class SignUpPage extends AppCompatActivity {
    private Button signUp_BTN_signUp;
    private Spinner signUp_SPI_country,signUp_SPI_bloodTypes;
    private EditText signUp_EDT_name,signUp_EDT_id,signUp_EDT_email,signUp_EDT_phone,signUp_EDT_password;
    private View view;
    private TextView signUp_TXT_birthDatePicker;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static Date date;
    // Write a message to the database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.sign_up_page);
        findView(view);
        ArrayAdapter<CharSequence> adapterCountries = ArrayAdapter.createFromResource(this,
                R.array.countries, android.R.layout.simple_spinner_item);
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_SPI_country.setAdapter(adapterCountries);


        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this,
                R.array.bloods, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_SPI_bloodTypes.setAdapter(adapterBloodTypes);



        signUp_TXT_birthDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(SignUpPage.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date=new Date(year,month,dayOfMonth);
                signUp_TXT_birthDatePicker.setText(date.toString());

            }
        };


        signUp_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(signUp_EDT_name.getText().toString(),signUp_EDT_id.getText().toString(),signUp_EDT_phone.getText().toString(),signUp_EDT_email.getText().toString(),
                        signUp_EDT_phone.getText().toString(),signUp_EDT_password.getText().toString(), signUp_SPI_bloodTypes.getSelectedItem().toString(),date);    ;

                myRef.child("or").setValue("Hello, World!");
                myRef.setValue(user);
                startActivity(new Intent(SignUpPage.this, ProfilePage.class));
            }
        });


    }

    private void findView(View view) {
        signUp_EDT_name = findViewById(R.id.signUp_EDT_name);
        signUp_EDT_id = findViewById(R.id.signUp_EDT_id);
        signUp_EDT_email = findViewById(R.id.signUp_EDT_email);
        signUp_EDT_phone = findViewById(R.id.signUp_EDT_phone);
        signUp_EDT_password = findViewById(R.id.signUp_EDT_password);
        signUp_BTN_signUp = findViewById(R.id.signUp_BTN_signUp);
        signUp_SPI_country =  findViewById(R.id.signUp_SPI_country);
        signUp_SPI_bloodTypes =  findViewById(R.id.signUp_SPI_bloodTypes);
        signUp_TXT_birthDatePicker = findViewById(R.id.signUp_TXT_birthDatePicker);
    }


}
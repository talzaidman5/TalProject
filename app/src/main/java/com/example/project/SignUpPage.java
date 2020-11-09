package com.example.project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class SignUpPage extends AppCompatActivity {
    public static final String KEY_MSP  = "user";
    public static final String KEY_MSP_ALL  = "allUsers1";
    public static final int PICK_IMAGE = 1;
    public String imageData;
    private Button signUp_BTN_signUp,sign_up_BTN_logo;
    private Spinner signUp_SPI_country,signUp_SPI_bloodTypes;
    private EditText signUp_EDT_name,signUp_EDT_id,signUp_EDT_email,signUp_EDT_phone,signUp_EDT_password;
    private View view;
    private TextView signUp_TXT_birthDatePicker;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static SharedPreferences sharedpreferences;
    public boolean data=true;
    public static Date date;
    private MySheredP msp;
    private Gson gson = new Gson();
    InputStream inputStream = null;

    // Write a message to the database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private AllUsers allUsers =  new AllUsers();
    private  User newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        msp = new MySheredP(this);
        setContentView(R.layout.sign_up_page);
        findView(view);
        getSupportActionBar().hide();

        ArrayAdapter<CharSequence> adapterCountries = ArrayAdapter.createFromResource(this,
                R.array.countries, android.R.layout.simple_spinner_item);
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_SPI_country.setAdapter(adapterCountries);



        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this,
                R.array.bloods, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_SPI_bloodTypes.setAdapter(adapterBloodTypes);

        sign_up_BTN_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

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
                if(checkData()) {
                    newUser = new User(signUp_EDT_name.getText().toString(), signUp_EDT_id.getText().toString(), signUp_EDT_email.getText().toString(),
                            signUp_EDT_phone.getText().toString(), signUp_EDT_password.getText().toString(), signUp_SPI_country.getSelectedItem().toString(), signUp_SPI_bloodTypes.getSelectedItem().toString(), date);
                    getFromMSP();
                    putOnMSP();
                    allUsers.addToList(newUser);
                    myRef.child("Users").child(signUp_EDT_id.getText().toString()).setValue(newUser);
                    putOnMSP();
                    startActivity(new Intent(SignUpPage.this, ProfilePage.class));
                }
            }
        });

    }

    private boolean checkData(){
        if(signUp_EDT_name.getText().toString().equals("")) {
            signUp_EDT_name.setError("");
            data=false;
        }
        if(signUp_EDT_id.getText().toString().equals("")) {
            signUp_EDT_id.setError("");
            data=false;
        }
        if(signUp_EDT_email.getText().toString().equals("")) {
            signUp_EDT_email.setError("");
            data=false;
        }
        if(signUp_EDT_phone.getText().toString().equals("")) {
            signUp_EDT_phone.setError("");
            data=false;
        }
        if(signUp_EDT_password.getText().toString().equals("")) {
            signUp_EDT_password.setError("");
            data=false;
        }
        if(signUp_SPI_country.getSelectedItem().toString().equals("")) {
            ((TextView)signUp_SPI_country.getSelectedView()).setError("Error message");
            data=false;
        }
        if(signUp_SPI_bloodTypes.getSelectedItem().toString().equals("")) {
            ((TextView)signUp_SPI_bloodTypes.getSelectedView()).setError("Error message");
            data=false;
        }
        if(signUp_TXT_birthDatePicker.getText().toString().equals("")) {
            signUp_TXT_birthDatePicker.setError("Error message");
            data=false;
        }
        return data;
    }

    private AllUsers getFromMSP(){
        String data  = msp.getString(KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(data);
        return allUsers;
    }
    private void putOnMSP(){
        String json = gson.toJson(newUser);
        msp.putString(KEY_MSP,json);
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
        sign_up_BTN_logo = findViewById(R.id.sign_up_BTN_logo);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageData = data.getData().toString();

            try {
                Uri myUri = Uri.parse(imageData);
                inputStream = getContentResolver().openInputStream(myUri);
                sign_up_BTN_logo.setBackground(Drawable.createFromStream(inputStream, myUri.toString()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
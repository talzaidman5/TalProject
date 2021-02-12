package com.example.project.activitis;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.BloodDonation;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfile extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextInputLayout myProfile_TXT_emailToFill, myProfile_TXT_phoneNumberToFill, myProfile_TXT_passwordToFill, myProfile_TXT_dateBirthToFill, myProfile_TXT_bloodTypeToFill;
    private TextInputLayout myProfile_TXT_IDToFill;
    private EditText myProfile_TXT_nameToFill;
    private CircleImageView myProfile_BTN_logo;
    private ImageButton myProfile_BTN_edit, myProfile_BTN_add;
    private MySheredP msp;
    private Gson gson = new Gson();
    private User currentUser = new User();
    private AllUsers allUsers;
    private Date date;
    private ArrayList<String> spinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        msp = new MySheredP(this);
        getSupportActionBar().hide();

        findView();
        getFromMSP();
        initData();


        myProfile_TXT_nameToFill.setEnabled(false);

        myProfile_BTN_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProfile_TXT_IDToFill.setEnabled(true);
                myProfile_TXT_passwordToFill.setEnabled(true);
                myProfile_TXT_bloodTypeToFill.setEnabled(true);
                myProfile_TXT_dateBirthToFill.setEnabled(true);
                myProfile_TXT_emailToFill.setEnabled(true);
                myProfile_TXT_nameToFill.setEnabled(true);
                myProfile_TXT_phoneNumberToFill.setEnabled(true);
            }
        });

        myProfile_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   openPopUp();
                open();
            }
        });


    }

    private void open() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        SearchableSpinner spn_my_spinner = dialog.findViewById(R.id.spinner);

        Button dialogButton = (Button) dialog.findViewById(R.id.popup_BTN_yes);


        spinnerArray = new ArrayList<String>();
      /*  spinnerArray.add("one");
        spinnerArray.add("two");
        spinnerArray.add("three");
        spinnerArray.add("four");
        spinnerArray.add("five");*/

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
                dialog.dismiss();
            }
        });

        dialog.show();
}

    private void openPopUp() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add Blood Donations");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final Spinner cities = new Spinner(this);
        spinnerArray = new ArrayList<String>();
      /*  spinnerArray.add("one");
        spinnerArray.add("two");
        spinnerArray.add("three");
        spinnerArray.add("four");
        spinnerArray.add("five");*/

        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        cities.setAdapter(spinnerArrayAdapter);
        layout.addView(cities);


        final TextView dateTxt = new TextView(this);
        dateTxt.setText("Click to pick date");

        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(MyProfile.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = new Date(year, month, dayOfMonth);
                dateTxt.setText(dayOfMonth + "/" + month + "/" + year);

            }
        };

        layout.addView(dateTxt);
        final Button add = new Button(this);
        add.setText("Add");
        layout.addView(add);
        alert.setView(layout); // Again this is a set method, not add
        alert.show();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloodDonation bloodDonation = new BloodDonation(cities.getSelectedItem().toString(), date);
                currentUser.addBloodDonation(bloodDonation);
                putOnMSP();


            }
        });
    }


    public void findView() {
        myProfile_BTN_logo = findViewById(R.id.myProfile_BTN_logo);
        myProfile_TXT_emailToFill = findViewById(R.id.myProfile_TXT_emailToFill);
        myProfile_TXT_phoneNumberToFill = findViewById(R.id.myProfile_TXT_phoneNumberToFill);
        myProfile_TXT_passwordToFill = findViewById(R.id.myProfile_TXT_passwordToFill);
        myProfile_TXT_nameToFill = findViewById(R.id.myProfile_TXT_nameToFill);
        myProfile_TXT_dateBirthToFill = findViewById(R.id.myProfile_TXT_dateBirthToFill);
        // myProfile_BTN_back =findViewById(R.id.myProfile_BTN_back);
        myProfile_TXT_IDToFill = findViewById(R.id.myProfile_TXT_IDToFill);
        myProfile_TXT_bloodTypeToFill = findViewById(R.id.myProfile_TXT_bloodTypeToFill);
        myProfile_BTN_edit = findViewById(R.id.myProfile_BTN_edit);
        myProfile_BTN_add = findViewById(R.id.myProfile_BTN_add);

    }

    public void initData() {
        myProfile_TXT_IDToFill.setEnabled(false);
        myProfile_TXT_passwordToFill.setEnabled(false);
        myProfile_TXT_bloodTypeToFill.setEnabled(false);
        myProfile_TXT_dateBirthToFill.setEnabled(false);
        myProfile_TXT_emailToFill.setEnabled(false);
        myProfile_TXT_nameToFill.setEnabled(false);
        myProfile_TXT_phoneNumberToFill.setEnabled(false);

        if (currentUser != null) {
            myProfile_TXT_phoneNumberToFill.getEditText().setText(currentUser.getPhoneNumber());
            myProfile_TXT_passwordToFill.getEditText().setText(currentUser.getPassword());
            myProfile_TXT_nameToFill.setText(currentUser.getFullName());
            myProfile_TXT_dateBirthToFill.getEditText().setText(currentUser.getBirthDate().getDay() + "/" + currentUser.getBirthDate().getMonth() + "/" + currentUser.getBirthDate().getYear());
            myProfile_TXT_IDToFill.getEditText().setText(currentUser.getID());
            myProfile_TXT_emailToFill.getEditText().setText(currentUser.getEmail());
            myProfile_TXT_bloodTypeToFill.getEditText().setText(currentUser.getBloodType());


            String urlImage = currentUser.getImageUser();
            myProfile_BTN_logo.setImageResource(android.R.color.transparent);
            Glide.with(MyProfile.this)
                    .load(urlImage)
                    .circleCrop()
                    .into(myProfile_BTN_logo);

        }

    }

    private void putOnMSP() {
        String json = gson.toJson(currentUser);
        msp.putString(Constants.KEY_MSP, json);
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
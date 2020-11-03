package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;


public class MyProfile extends AppCompatActivity {
    public static final String KEY_MSP  = "user";
    public static final String KEY_MSP_ALL  = "allUsers1";

    private EditText myProfile_TXT_nameToFill,myProfile_TXT_emailToFill,myProfile_TXT_phoneNumberToFill,myProfile_TXT_passwordToFill,
            myProfile_TXT_countryToFill,myProfile_TXT_dateBirthToFill,myProfile_TXT_bloodTypeToFill;
    private TextView myProfile_TXT_IDToFill;
    private Button myProfile_BTN_back;
    ImageButton myProfile_BTN_edit;
    private MySheredP msp;
    private Gson gson = new Gson();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private User currentUser = new User();
    private AllUsers allUsers;

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
                myProfile_TXT_countryToFill.setEnabled(true);
                myProfile_TXT_dateBirthToFill.setEnabled(true);
                myProfile_TXT_emailToFill.setEnabled(true);
                myProfile_TXT_nameToFill.setEnabled(true);
                myProfile_TXT_phoneNumberToFill.setEnabled(true);
            }
        });
    }
    public void findView(){
        myProfile_BTN_edit=findViewById(R.id.myProfile_BTN_edit);
        myProfile_TXT_emailToFill=findViewById(R.id.myProfile_TXT_emailToFill);
        myProfile_TXT_phoneNumberToFill=findViewById(R.id.myProfile_TXT_phoneNumberToFill);
        myProfile_TXT_passwordToFill=findViewById(R.id.myProfile_TXT_passwordToFill);
        myProfile_TXT_countryToFill=findViewById(R.id.myProfile_TXT_countryToFill);
        myProfile_TXT_nameToFill=findViewById(R.id.myProfile_TXT_nameToFill);
        myProfile_TXT_dateBirthToFill=findViewById(R.id.myProfile_TXT_dateBirthToFill);
        myProfile_BTN_back =findViewById(R.id.myProfile_BTN_back);
        myProfile_TXT_IDToFill=findViewById(R.id.myProfile_TXT_IDToFill);
        myProfile_TXT_bloodTypeToFill=findViewById(R.id.myProfile_TXT_bloodTypeToFill);

    }
    public void initData(){
        myProfile_TXT_IDToFill.setEnabled(false);
        myProfile_TXT_passwordToFill.setEnabled(false);
        myProfile_TXT_bloodTypeToFill.setEnabled(false);
        myProfile_TXT_countryToFill.setEnabled(false);
        myProfile_TXT_dateBirthToFill.setEnabled(false);
        myProfile_TXT_emailToFill.setEnabled(false);
        myProfile_TXT_nameToFill.setEnabled(false);
        myProfile_TXT_phoneNumberToFill.setEnabled(false);

        if(currentUser!=null) {
            myProfile_TXT_phoneNumberToFill.setText(currentUser.getPhoneNumber());
            myProfile_TXT_passwordToFill.setText(currentUser.getPassword());
            myProfile_TXT_countryToFill.setText(currentUser.getCountry());
            myProfile_TXT_nameToFill.setText(currentUser.getFullName());
            myProfile_TXT_dateBirthToFill.setText(currentUser.getBirthDate().toString());
            myProfile_TXT_IDToFill.setText(currentUser.getID());
            myProfile_TXT_bloodTypeToFill.setText(currentUser.getBloodType());
        }
        
    }

    private AllUsers getFromMSP(){
        String data  = msp.getString(KEY_MSP, "NA");
        String dataAll  = msp.getString(KEY_MSP_ALL, "NA");
        currentUser = new User(data);
        allUsers = new AllUsers(dataAll);
        return allUsers;
    }

}
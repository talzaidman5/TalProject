package com.example.project.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class ActivityLogIn extends AppCompatActivity {

    private Button main_page_BTN_signUp;
    MaterialButton mainPage_BTN_signIn;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private AllUsers allUsers = new AllUsers();
    private User newUser;
    private TextInputLayout mainPage_EDIT_id, mainPage_EDIT_password;
    private MySheredP msp;
    private Gson gson = new Gson();
    private CheckBox main_page_CHECK_remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();
        readFB();


 /*     myRef.child("ActivityPosition").child("0").setValue(new Position("בית חולים תה\"ש רמת גן, - שרותי הדם מד\"א",2,new Date(),"09:00","17:00","https://www.mdais.org/images/whatsup.jpg"));
         myRef.child("ActivityPosition").child("1").setValue(new Position("עראבה, מרכז העיר\t",14,new Date(),"16:00","19:00","https://www.mdais.org/images/whatsup.jpg"));
        myRef.child("ActivityPosition").child("2").setValue(new Position("כרכום, מועדון\t",22,new Date(),"09:00","20:00:00","https://www.mdais.org/images/whatsup.jpg"));
        myRef.child("ActivityPosition").child("3").setValue(new Position("דרך משה פלימן 4 חיפה, קניון חיפה מול קסטרו\t",151,new Date(),"16:00","20:00:00","https://www.mdais.org/images/whatsup.jpg"));
*/
        mainPage_EDIT_id = findViewById(R.id.mainPage_EDIT_id);
        mainPage_BTN_signIn = findViewById(R.id.mainPage_BTN_signIn);
        mainPage_EDIT_password = findViewById(R.id.mainPage_EDIT_password);
        main_page_BTN_signUp = findViewById(R.id.main_page_BTN_signUp);
        main_page_CHECK_remember = findViewById(R.id.main_page_CHECK_remember);
        mainPage_BTN_signIn.setEnabled(false);
        msp = new MySheredP(this);

        getFromMSP();
        mainPage_BTN_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkUser();
            }
        });
        main_page_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();
            }
        });


    }

    private User getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP, "NA");
        newUser = new User(data);
        if (newUser.getRemember())
            startActivity(new Intent(ActivityLogIn.this, ActivityProfileMenu.class));
        return newUser;
    }

    public void openSignUpPage() {

        startActivity(new Intent(ActivityLogIn.this, ActivitySignUpPage.class));
    }

    public void readFB() {
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User tempUser = ds.getValue(User.class);
                    allUsers.addToList(tempUser);
                }
                mainPage_BTN_signIn.setEnabled(true);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void checkUser() {
        if (allUsers.getAllUser().size() != 0){
            if (isUser()) {
                updateRemember();
                putOnMSP();
                    if (newUser.getPassword().equals(mainPage_EDIT_password.getEditText().getText().toString())) {
                        if (newUser.getUserType().equals(User.USER_TYPE.MANAGER))
                            startActivity(new Intent(ActivityLogIn.this, ActivityMenuManager.class));
                        else
                            startActivity(new Intent(ActivityLogIn.this, ActivityProfileMenu.class));
                    }

            } }else
                mainPage_EDIT_id.getEditText().setText("invalid");
        if(mainPage_EDIT_password.getEditText().getText().length() == 0 || mainPage_EDIT_id.getEditText().getText().length() == 0 )
            mainPage_EDIT_id.getEditText().setText("הכנס תעודה מזההה וסיסמא");

    }

    private void updateRemember() {
        if (main_page_CHECK_remember.isChecked()) {
            newUser.setRemember(true);
            myRef.child("Users").child(newUser.getID()).setValue(newUser);
        }
    }

    private boolean isUser() {
        String uuidCheck = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        newUser = allUsers.getUserByID(mainPage_EDIT_id.getEditText().getText().toString());
        if(newUser!= null) {
            if (newUser.getuuID().equals(uuidCheck) && newUser != null)
                return true;
        }
            return false;

    }

    private void putOnMSP() {
        String jsonAll = gson.toJson(allUsers);
        String json = gson.toJson(newUser);
        msp.putString(Constants.KEY_MSP_ALL, jsonAll);
        msp.putString(Constants.KEY_MSP, json);

    }
}
package com.example.project.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.activitis.client.ActivityProfileMenu;
import com.example.project.activitis.manager.ActivityAllReports;
import com.example.project.data.AllUsers;
import com.example.project.data.Position;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class ActivityLogIn extends AppCompatActivity {

    private Button main_page_BTN_signUp, viewForgotPassword;
    private MaterialButton mainPage_BTN_signIn;
    private AllUsers allUsers = new AllUsers();
    private User newUser;
    private TextInputLayout mainPage_EDIT_password, mainPage_EDIT_email;
    private MySheredP msp;
    private Gson gson = new Gson();
    private TextView login_TXT_forgot;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");

    public static FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();


     /*  myRef.child("ActivityPosition").child("0").setValue(new Position("בית חולים תה\"ש רמת גן, - שרותי הדם מד\"א",2,new Date(),"09:00","17:00","https://www.mdais.org/images/whatsup.jpg"));
        myRef.child("ActivityPosition").child("1").setValue(new Position("עראבה, מרכז העיר\t",14,new Date(),"16:00","19:00","https://www.mdais.org/images/whatsup.jpg"));
        myRef.child("ActivityPosition").child("2").setValue(new Position("כרכום, מועדון\t",22,new Date(),"09:00","20:00:00","https://www.mdais.org/images/whatsup.jpg"));
        myRef.child("ActivityPosition").child("3").setValue(new Position("דרך משה פלימן 4 חיפה, קניון חיפה מול קסטרו\t",151,new Date(),"16:00","20:00:00","https://www.mdais.org/images/whatsup.jpg"));
*/
        findViews();
        msp = new MySheredP(this);
        getFromMSP();
        getAllFromMSP();
        mainPage_BTN_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    checkUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        main_page_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();
            }
        });
        viewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgotPasswordClicked();

            }
        });
    }

    private void findViews() {
        mainPage_EDIT_email = findViewById(R.id.mainPage_EDIT_email);
        mainPage_BTN_signIn = findViewById(R.id.mainPage_BTN_signIn);
        mainPage_EDIT_password = findViewById(R.id.mainPage_EDIT_password);
        main_page_BTN_signUp = findViewById(R.id.main_page_BTN_signUp);
        this.viewForgotPassword = findViewById(R.id.viewForgotPassword);
        login_TXT_forgot = findViewById(R.id.login_TXT_forgot);

    }

    public void onForgotPasswordClicked() {
        String mail = mainPage_EDIT_email.getEditText().getText().toString().trim();

        if (mail.isEmpty()) {
            mainPage_EDIT_email.setError("Invalid email");
            mainPage_EDIT_email.requestFocus();
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ActivityLogIn.this, "הסיסמא נשלחה לכתובת המייל שלך", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ActivityLogIn.this, "כתובת המייל שהוזנה שגויה", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private User getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP, "NA");
        newUser = new User(data);
        if (newUser.getRemember())
            if (newUser.getUserType().equals(User.USER_TYPE.CLIENT))
                startActivity(new Intent(ActivityLogIn.this, ActivityProfileMenu.class));
            else
                startActivity(new Intent(ActivityLogIn.this, ActivityAllReports.class));

        return newUser;
    }

    private AllUsers getAllFromMSP() {

        String data = msp.getString(Constants.KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(data);
        return allUsers;
    }

    public void openSignUpPage() {
        startActivity(new Intent(ActivityLogIn.this, ActivitySignUpPage.class));
        finish();
    }


    public void checkUser() throws Exception {
        String email_txt = mainPage_EDIT_email.getEditText().getText().toString();
        String password_txt = mainPage_EDIT_password.getEditText().getText().toString();
        newUser = allUsers.getUserByEmail(email_txt);
        if ((TextUtils.isEmpty(email_txt)) || (TextUtils.isEmpty(password_txt)))
            if (TextUtils.isEmpty(email_txt))
                mainPage_EDIT_email.getEditText().setError("אנא הכנס איימיל");
        if (TextUtils.isEmpty(password_txt))
            mainPage_EDIT_password.getEditText().setError("אנא הכנס סיסמא");
        else {
            auth.signInWithEmailAndPassword(email_txt, password_txt)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                login_TXT_forgot.setVisibility(View.INVISIBLE);
                                putOnMSP();
                                Intent intent;
                                if (newUser != null) {
                                    if (newUser.getID() != null) {
                                        if (newUser.getUserType().equals(User.USER_TYPE.CLIENT))
                                            intent = new Intent(ActivityLogIn.this, ActivityProfileMenu.class);
                                        else
                                            intent = new Intent(ActivityLogIn.this, ActivityAllReports.class);

                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            } else
                                login_TXT_forgot.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }


    private void putOnMSP() {
        String jsonAll = gson.toJson(allUsers);
        String json = gson.toJson(newUser);
        msp.putString(Constants.KEY_MSP_ALL, jsonAll);
        msp.putString(Constants.KEY_MSP, json);


    }
}
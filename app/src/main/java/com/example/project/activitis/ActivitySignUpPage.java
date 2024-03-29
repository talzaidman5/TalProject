package com.example.project.activitis;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.activitis.client.ActivityProfileMenu;
import com.example.project.activitis.manager.ActivityAllReports;
import com.example.project.data.CheckValidation;
import com.example.project.data.Encryption;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.data.AllUsers;
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
import com.google.firebase.auth.FirebaseUser;
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

public class ActivitySignUpPage extends AppCompatActivity {
    private MaterialButton signUp_BTN_signUp;
    private Spinner signUp_SPI_bloodTypes;
    private TextInputLayout signUp_EDT_id, signUp_EDT_email, signUp_EDT_phone, signUp_EDT_password, signUp_EDT_lastName, signUp_EDT_firstName;
    private TextView signUp_TXT_birthDatePicker,signUp_TXT_birthDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public boolean data = true;
    public Date date;
    private MySheredP msp;
    private Gson gson = new Gson();
    private String uuid;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private AllUsers allUsers = new AllUsers();
    private User newUser = new User();
    private ArrayList<String> spinnerArray;
    private Spinner signUp_EDT_city;
    private RadioButton signup_CHB_female;
    private int age;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        msp = new MySheredP(this);
        setContentView(R.layout.activity_sign_up_page);
        findView();
        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this,
                R.array.bloods, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_SPI_bloodTypes.setAdapter(adapterBloodTypes);

        uuid = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        getFromMSP();



        signUp_TXT_birthDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(ActivitySignUpPage.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
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
                date = calendar.getTime();
                String finalDate = dayOfMonth + "/" + (month+1) + "/" + year;
                signUp_TXT_birthDatePicker.setText(finalDate);

            }
        };


        signUp_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(signUp_EDT_id.getEditText().getText().toString(), signUp_EDT_email.getEditText().getText().toString(),
                        signUp_EDT_password.getEditText().getText().toString());
            }

        });

        spinnerArray = new ArrayList<String>();
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        signUp_EDT_city.setAdapter(spinnerArrayAdapter);


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

    private boolean checkData() {
        data = true;
        if (signUp_EDT_firstName.getEditText().getText().length() < 2) {
            signUp_EDT_firstName.setError("שם לא תקין");
            data = false;
        } else
            signUp_EDT_firstName.setError(null);

        if (signUp_EDT_lastName.getEditText().getText().length() < 2) {
            signUp_EDT_lastName.setError("שם לא תקין");
            data = false;
        } else
            signUp_EDT_lastName.setError(null);
        if (!CheckValidation.checkID(signUp_EDT_id.getEditText().getText().toString())) {
            signUp_EDT_id.setError("תעודת זהות לא תקינה");
            data = false;
        } else
            signUp_EDT_id.setError(null);

        if (!CheckValidation.checkMail(signUp_EDT_email.getEditText().getText().toString())) {
            signUp_EDT_email.setError("כתובת מייל לא תקינה");
            data = false;
        } else
            signUp_EDT_email.setError(null);
        if (!CheckValidation.checkPhoneNumberISRAEL(signUp_EDT_phone.getEditText().getText().toString())) {
            signUp_EDT_phone.setError("מספר טלפון לא תקין");
            data = false;
        } else
            signUp_EDT_phone.setError(null);


        if (signUp_EDT_password.getEditText().getText().length() < 5) {
            signUp_EDT_password.setError("סיסמא חייבת להיות לפחות בת 6 תווים");
            data = false;
        } else
            signUp_EDT_password.setError(null);

        Calendar today = Calendar.getInstance();
        age = today.get(Calendar.YEAR) - date.getYear() - 1900;
        if (age< 17 || age > 65) {
            signUp_TXT_birthDate.setTextColor(16711680);
            signUp_TXT_birthDate.setError("גיל צריך להיות מעל 17 ועד 65");
            data = false;
        }
        return data;
    }

    private AllUsers getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(data);
        return allUsers;
    }

    private void putOnMSP() {
        String json = gson.toJson(newUser);
        msp.putString(Constants.KEY_MSP, json);
    }

    private void findView() {
        signUp_EDT_firstName = findViewById(R.id.signUp_EDT_firstName);
        signUp_EDT_lastName = findViewById(R.id.signUp_EDT_lastName);
        signUp_EDT_id = findViewById(R.id.signUp_EDT_id);
        signUp_EDT_email = findViewById(R.id.signUp_EDT_email);
        signUp_EDT_phone = findViewById(R.id.signUp_EDT_phone);
        signUp_EDT_password = findViewById(R.id.signUp_EDT_password);
        signUp_BTN_signUp = findViewById(R.id.signUp_BTN_signUp);
        signUp_SPI_bloodTypes = findViewById(R.id.signUp_SPI_bloodTypes);
        signUp_TXT_birthDatePicker = findViewById(R.id.signUp_TXT_birthDatePicker);
        signUp_EDT_city = findViewById(R.id.signUp_EDT_city);
        signup_CHB_female = findViewById(R.id.signup_CHB_female);
        signUp_TXT_birthDate = findViewById(R.id.signUp_TXT_birthDate);

    }


    private void register(String usernameTemp, String emailTemp, String passwordTemp) {
        if (checkData()) {
            auth.createUserWithEmailAndPassword(emailTemp, passwordTemp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        assert firebaseUser != null;
                        buildUser();
                        try {
                            String city = Encryption.encrypt(newUser.getCity());
                            newUser.setCity(city);

                            String email = Encryption.encrypt(newUser.getEmail());
                            newUser.setEmail(email);

                            String password = Encryption.encrypt(newUser.getPassword());
                            newUser.setPassword(password);

                            String phone = Encryption.encrypt(newUser.getPhoneNumber());
                            newUser.setPhoneNumber(phone);

                            String bloodType = Encryption.encrypt(newUser.getBloodType());
                            newUser.setBloodType(bloodType);

                            String name = Encryption.encrypt(newUser.getFirstName().toString());
                            newUser.setFirstName(name);

                            String fName = Encryption.encrypt(newUser.getLastName().toString());
                            newUser.setLastName(fName);

                            String age = Encryption.encrypt(newUser.getAge()+"");
                            newUser.setAge(Integer.parseInt(age));




                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                            myRef.child("Users").child(newUser.getID()).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    getFromMSP();
                                    allUsers.addToList(newUser);
                                    putOnMSP();
                                    if (newUser.getUserType().equals(User.USER_TYPE.CLIENT)) {
                                        startActivity(new Intent(ActivitySignUpPage.this, ActivityProfileMenu.class));
                                        finish();
                                    }  else {
                                        startActivity(new Intent(ActivitySignUpPage.this, ActivityAllReports.class));
                                        finish();
                                    }
                                } else
                                    Toast.makeText(ActivitySignUpPage.this, "error", Toast.LENGTH_SHORT);
                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ActivitySignUpPage.this, e.getMessage(), Toast.LENGTH_SHORT);
                }
            });
        }
    }


    private void buildUser() {
        newUser.setFirstName(signUp_EDT_firstName.getEditText().getText().toString());
        newUser.setLastName(signUp_EDT_lastName.getEditText().getText().toString());
        if (signUp_EDT_id.getEditText().getText().toString().equals((Constants.MANAGER_ID)))
            newUser.setUserType(signUp_EDT_id.getEditText().getText().toString());
        newUser.setID(signUp_EDT_id.getEditText().getText().toString());
        newUser.setEmail(signUp_EDT_email.getEditText().getText().toString());
        newUser.setPhoneNumber(signUp_EDT_phone.getEditText().getText().toString());
        newUser.setPassword(signUp_EDT_password.getEditText().getText().toString());
        newUser.setBloodType(signUp_SPI_bloodTypes.getSelectedItem().toString());
        newUser.setBirthDate(date);
        newUser.setUuID(uuid);
        newUser.setGender(selectedGender());

        newUser.setAge(age);
        newUser.setCity(signUp_EDT_city.getSelectedItem().toString());
        newUser.setCanDonateBlood(false);

    }

    private User.GENDER selectedGender() {
        if (signup_CHB_female.isChecked())
            return User.GENDER.FEMALE;
        else
            return User.GENDER.MALE;
    }

}
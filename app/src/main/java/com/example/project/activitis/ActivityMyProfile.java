package com.example.project.activitis;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.BloodDonation;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class ActivityMyProfile extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextInputLayout myProfile_TXT_emailToFill, myProfile_TXT_phoneNumberToFill, myProfile_TXT_passwordToFill, myProfile_TXT_dateBirthToFill, myProfile_TXT_bloodTypeToFill;
    private TextInputLayout myProfile_TXT_IDToFill;
    private EditText myProfile_TXT_nameToFill;
    private ImageView myProfile_BTN_logo;
    private ImageButton myProfile_BTN_add, myProfile_BTN_logout;
    private MaterialButton myProfile_BTN_edit;
    private MySheredP msp;
    private Gson gson = new Gson();
    private User currentUser = new User();
    private AllUsers allUsers;
    private Date date;
    private ArrayList<String> spinnerArray;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private Spinner spn_my_spinner;
    private TextView add_blood_donation_TXT_date;
    private boolean editFlag = false;
    private StorageReference storageReference;
    private FirebaseStorage storage;

    private String filePath = "";
    private Uri fileUri;
    private String tempName ="";


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
        initData();


        myProfile_TXT_nameToFill.setEnabled(false);

        myProfile_BTN_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editFlag == false) {
                    editFlag = true;

                    changeTextsEnabled(true);
                    myProfile_BTN_edit.setBackgroundResource(R.drawable.save);
//                    myProfile_TXT_nameToFill.setEnabled(true);
                    myProfile_TXT_phoneNumberToFill.setEnabled(true);
                } else {

                    if (checkData()) {
                        changeTextsEnabled(false);
                        myProfile_BTN_edit.setBackgroundResource(R.drawable.pencil);
                        editFlag = false;
                        updateUserInfo();
                        putOnMSP_user();
                    }
                }
            }
        });

        myProfile_BTN_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editFlag == true)
                    getImage();
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
        myProfile_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   openPopUp();
                open();
            }
        });


    }

    private void getImage() {
        ImagePicker.Companion
                .with(this)
                .crop()
                .cropOval()
                .cropSquare()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    private void changeTextsEnabled(boolean status) {
        myProfile_TXT_IDToFill.setEnabled(status);
        myProfile_TXT_passwordToFill.setEnabled(status);
        myProfile_TXT_bloodTypeToFill.setEnabled(status);
        myProfile_TXT_dateBirthToFill.setEnabled(status);
        myProfile_TXT_emailToFill.setEnabled(status);
    }

    private void updateUserInfo() {
        currentUser.setID(myProfile_TXT_IDToFill.getEditText().getText().toString());
        currentUser.setPassword(myProfile_TXT_passwordToFill.getEditText().getText().toString());
        currentUser.setBloodType(myProfile_TXT_bloodTypeToFill.getEditText().getText().toString());
//        currentUser.setBirthDate(myProfile_TXT_dateBirthToFill.getEditText().getText().toString());
        currentUser.setEmail(myProfile_TXT_emailToFill.getEditText().getText().toString());
        currentUser.setPhoneNumber(myProfile_TXT_phoneNumberToFill.getEditText().getText().toString());
        currentUser.setImageUser(fileUri.toString());
    }

    private boolean checkData() {
        boolean data = true;
//        if (myProfile_TXT_nameToFill.getText().length() == 0) {
//            myProfile_TXT_nameToFill.setError("ENTER NAME");
//            data = false;
//        }

        if (myProfile_TXT_IDToFill.getEditText().getText().length() == 0) {
            myProfile_TXT_IDToFill.setError("ENTER ID");
            data = false;
        }
        if (myProfile_TXT_emailToFill.getEditText().getText().length() == 0) {
            myProfile_TXT_emailToFill.setError("ENTER EMAIL");
            data = false;
        }
        if (myProfile_TXT_phoneNumberToFill.getEditText().getText().length() == 0) {
            myProfile_TXT_phoneNumberToFill.setError("ENTER PHONE");
            data = false;
        }
        if (myProfile_TXT_passwordToFill.getEditText().getText().length() == 0) {
            myProfile_TXT_passwordToFill.setError("ENTER PASSWORD");
            data = false;
        }

        if (myProfile_TXT_bloodTypeToFill.getEditText().getText().length() == 0) {
            myProfile_TXT_bloodTypeToFill.setError("ENTER BLOOD TYPE");
            data = false;
        }
        if (myProfile_TXT_dateBirthToFill.getEditText().getText().length() == 0) {
            myProfile_TXT_dateBirthToFill.setError("ENTER DATE");
            data = false;
        }
        return data;
    }


    private void open() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_add_blood_donation);
        dialog.setTitle("Title...");
        // set the custom dialog components - text, image and button
        spn_my_spinner = dialog.findViewById(R.id.spinner);

        Button dialogButton = dialog.findViewById(R.id.popup_BTN_add);
        add_blood_donation_TXT_date = dialog.findViewById(R.id.add_blood_donation_TXT_date);
        add_blood_donation_TXT_date.setOnClickListener(new View.OnClickListener() {
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

                date = new Date(year, month, dayOfMonth);
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

                String finalDate = dateFormat2.format(date);
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
                saveToFirebase();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void saveToFirebase() {
        BloodDonation bloodDonation = new BloodDonation(spn_my_spinner.getSelectedItem().toString(), date, currentUser.getID());
        currentUser.addBloodDonation(bloodDonation);
        String bloodDonationID = currentUser.getID() + "-" + currentUser.getAllBloodDonations().size();
        bloodDonation.setBooldDonationId(bloodDonationID);
        myRef.child("Users").child(currentUser.getID()).setValue(currentUser);
        myRef.child("Blood donations").child(bloodDonationID).setValue(bloodDonation);

        putOnMSP_user();
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
        myProfile_BTN_logout = findViewById(R.id.myProfile_BTN_logout);
        add_blood_donation_TXT_date = findViewById(R.id.add_blood_donation_TXT_date);

    }

    public void initData() {
        changeTextsEnabled(false);

        if (currentUser.getID() != null) {
            myProfile_TXT_phoneNumberToFill.getEditText().setText(currentUser.getPhoneNumber());
            myProfile_TXT_passwordToFill.getEditText().setText(currentUser.getPassword());
            myProfile_TXT_nameToFill.setText(currentUser.getFirstName());
            myProfile_TXT_dateBirthToFill.getEditText().setText(currentUser.getBirthDate().getDay() + "/" + currentUser.getBirthDate().getMonth() + "/" + currentUser.getBirthDate().getYear());
            myProfile_TXT_IDToFill.getEditText().setText(currentUser.getID());
            myProfile_TXT_emailToFill.getEditText().setText(currentUser.getEmail());
            myProfile_TXT_bloodTypeToFill.getEditText().setText(currentUser.getBloodType());



            String urlImage = currentUser.getImageUser();
            myProfile_BTN_logo.setImageResource(android.R.color.transparent);
            Glide.with(ActivityMyProfile.this)
                    .load(urlImage)
                    .circleCrop()
                    .into(myProfile_BTN_logo);

        }

    }

    private void putOnMSP_user() {
        String json = gson.toJson(currentUser);
        msp.putString(Constants.KEY_MSP, json);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            fileUri = data.getData();
            myProfile_BTN_logo.setImageURI(fileUri);


            //You can also get File Path from intent
            filePath = new ImagePicker().Companion.getFilePath(data);
            uploadImage();
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, new ImagePicker().Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            StorageReference ref = storageReference.child(fileUri.toString());
            currentUser.setImageUser(fileUri.toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(
                        UploadTask.TaskSnapshot taskSnapshot) {

                }
            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            });
        }
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
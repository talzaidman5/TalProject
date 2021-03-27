package com.example.project.activitis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;


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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivitySignUpPage extends AppCompatActivity {
    private MaterialButton signUp_BTN_signUp;
    private ImageView sign_up_IMG_logo;
    private Spinner signUp_SPI_bloodTypes;
    private TextInputLayout signUp_EDT_id, signUp_EDT_email, signUp_EDT_phone, signUp_EDT_password, signUp_EDT_lastName, signUp_EDT_firstName;
    private TextView signUp_TXT_birthDatePicker;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public boolean data = true;
    public static Date date;
    private MySheredP msp;
    private Gson gson = new Gson();
    private String imageUrl, uuid;
    private final int PICK_IMAGE_REQUEST = 22;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");
    private AllUsers allUsers = new AllUsers();
    private User newUser = new User();
    private Boolean isImage = false;
    private ArrayList<String> spinnerArray;
    private Spinner spn_my_spinner;
    private CheckBox signup_CHB_female, signup_CHB_male;


    private String filePath = "";
    private Uri fileUri;
    private String tempName;


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
        storageReference = storage.getReference();
        auth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this,
                R.array.bloods, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_SPI_bloodTypes.setAdapter(adapterBloodTypes);

        uuid = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        getFromMSP();

        sign_up_IMG_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImage = true;
                getImage();
            }
        });

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

                date = new Date(year, month, dayOfMonth);
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                String finalDate = dayOfMonth + "/"+ month + "/" + year;
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

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spn_my_spinner.setAdapter(spinnerArrayAdapter);

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
        if (signUp_EDT_firstName.getEditText().getText().toString().equals("")) {
            signUp_EDT_firstName.setError("");
            data = false;
        }
        if (signUp_EDT_lastName.getEditText().getText().toString().equals("")) {
            signUp_EDT_lastName.setError("");
            data = false;
        }
        if (signUp_EDT_id.getEditText().getText().toString().equals("")) {
            signUp_EDT_id.setError("");
            data = false;
        }
        if (signUp_EDT_email.getEditText().getText().toString().equals("")) {
            signUp_EDT_email.setError("");
            data = false;
        }
        if (signUp_EDT_phone.getEditText().getText().toString().equals("")) {
            signUp_EDT_phone.setError("");
            data = false;
        }
        if (signUp_EDT_password.getEditText().getText().toString().equals("")) {
            signUp_EDT_password.setError("");
            data = false;
        }

        if (signUp_SPI_bloodTypes.getSelectedItem().toString().equals("")) {
            ((TextView) signUp_SPI_bloodTypes.getSelectedView()).setError("Error message");
            data = false;
        }
        if (signUp_TXT_birthDatePicker.getText().toString().equals("")) {
            signUp_TXT_birthDatePicker.setError("Error message");
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
        sign_up_IMG_logo = findViewById(R.id.sign_up_IMG_logo);
        spn_my_spinner = findViewById(R.id.signUp_EDT_city);
        signup_CHB_female = findViewById(R.id.signup_CHB_female);
        signup_CHB_male = findViewById(R.id.signup_CHB_male);

    }

    // Select Image method
    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            fileUri = data.getData();
            sign_up_IMG_logo.setImageURI(fileUri);


            //You can also get File Path from intent
            filePath = new ImagePicker().Companion.getFilePath(data);
            uploadImage();
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, new ImagePicker().Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadImageByDrawableName() {
        Glide
                .with(this)
                .load(R.drawable.background)
                .into(sign_up_IMG_logo);
    }

    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {
            StorageReference ref = storageReference.child(fileUri.toString());
            newUser.setImageUser(fileUri.toString());
            tempName = fileUri.toString();
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

//    private void uploadImageOld() {
//        if (filePath != null) {
//
//            // Code for showing progressDialog while uploading
//            ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            // Defining the child of storageReference
//            imageUrl = "images/" + UUID.randomUUID().toString();
//            StorageReference ref = storageReference.child(imageUrl);
//            newUser.setImageUser(imageUrl);
//            // adding listeners on upload
//            // or failure of image
//            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(
//                        UploadTask.TaskSnapshot taskSnapshot) {
//                    // Image uploaded successfully
//                    // Dismiss dialog
//                    progressDialog.dismiss();
//                    Toast.makeText(ActivitySignUpPage.this,
//                            "Image Uploaded!!",
//                            Toast.LENGTH_SHORT)
//                            .show();
//                }
//            })
//
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(ActivitySignUpPage.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress
//                                    = (100.0
//                                    * taskSnapshot.getBytesTransferred()
//                                    / taskSnapshot.getTotalByteCount());
//                            progressDialog.setMessage(
//                                    "Uploaded "
//                                            + (int) progress + "%");
//                        }
//                    });
//        }
//    }

    private void register(String usernameTemp, String emailTemp, String passwordTemp) {
        if (!usernameTemp.equals("") && !emailTemp.equals("") && !passwordTemp.equals("")) {
            auth.createUserWithEmailAndPassword(emailTemp, passwordTemp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        assert firebaseUser != null;
                        buildUser();
                        myRef.child("Users").child(signUp_EDT_id.getEditText().getText().toString()).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    getFromMSP();
                                    allUsers.addToList(newUser);
                                    putOnMSP();
                                    uploadImage();
                                    if (newUser.getUserType().equals(User.USER_TYPE.CLIENT))
                                        startActivity(new Intent(ActivitySignUpPage.this, ActivityProfileMenu.class));
                                    else
                                        startActivity(new Intent(ActivitySignUpPage.this, ActivityMenuManager.class));

                                } else
                                    Toast.makeText(ActivitySignUpPage.this, "error", Toast.LENGTH_SHORT);
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
        } else {
            checkData();
        }

    }

    private void buildUser() {
        newUser.setFirstName(signUp_EDT_firstName.getEditText().getText().toString());
        newUser.setLastName(signUp_EDT_lastName.getEditText().getText().toString());
        newUser.setUserType(signUp_EDT_lastName.getEditText().getText().toString());
        newUser.setID(signUp_EDT_id.getEditText().getText().toString());
        newUser.setEmail(signUp_EDT_email.getEditText().getText().toString());
        newUser.setPhoneNumber(signUp_EDT_phone.getEditText().getText().toString());
        newUser.setPassword(signUp_EDT_password.getEditText().getText().toString());
        newUser.setBloodType(signUp_SPI_bloodTypes.getSelectedItemPosition());
        newUser.setBirthDate(date);
        newUser.setUuID(uuid);
        newUser.setGender(selectedGender());
        if (filePath.equals(""))
            newUser.setImageUser("https://firebasestorage.googleapis.com/v0/b/final-project-ff1e8.appspot.com/o/images%2Fprofile.png?alt=media&token=b177f2a3-f5fd-4dc7-a749-cd3fff20827e");
        else
            newUser.setImageUser(filePath);

    }


    private User.GENDER selectedGender() {
        if (signup_CHB_female.isSelected())
            return User.GENDER.FEMALE;
        else
            return User.GENDER.MALE;
    }

}
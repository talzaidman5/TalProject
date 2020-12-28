package com.example.project.activitis;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.User;
import com.example.project.utils.MySheredP;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpPage extends AppCompatActivity {
    public static final String KEY_MSP  = "user";
    public static final String KEY_MSP_ALL  = "allUsers1";
    public static final int PICK_IMAGE = 1;
    public String imageData;
    private MaterialButton signUp_BTN_signUp;
    CircleImageView sign_up_IMG_logo;
    private Spinner signUp_SPI_bloodTypes;
    private TextInputLayout signUp_EDT_id,signUp_EDT_email,signUp_EDT_phone,signUp_EDT_password,signUp_EDT_name;
    private View view;
    private TextView signUp_TXT_birthDatePicker;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static SharedPreferences sharedpreferences;
    public boolean data=true;
    public static Date date;
    private MySheredP msp;
    private Gson gson = new Gson();
    String imageUrl;

    TextInputLayout till;
    AutoCompleteTextView act;
    ArrayList<String> arrayLisSessson;
    ArrayAdapter<String>sessons;

    // InputStream inputStream = null;
  //  private ImageView imgView;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;

    // Write a message to the database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private AllUsers allUsers =  new AllUsers();
    private User newUser;
    private Boolean isImage= false;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        msp = new MySheredP(this);
        setContentView(R.layout.sign_up_page);
        findView(view);
        getSupportActionBar().hide();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        ArrayAdapter<CharSequence> adapterBloodTypes = ArrayAdapter.createFromResource(this,
                R.array.bloods, android.R.layout.simple_spinner_item);
        adapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_SPI_bloodTypes.setAdapter(adapterBloodTypes);

        sign_up_IMG_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImage = true;
                SelectImage();
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
                    if(isImage)
                    newUser = new User(signUp_EDT_name.getEditText().getText().toString(), signUp_EDT_id.getEditText().getText().toString(), signUp_EDT_email.getEditText().getText().toString(),
                            signUp_EDT_phone.getEditText().getText().toString(), signUp_EDT_password.getEditText().getText().toString(),  signUp_SPI_bloodTypes.getSelectedItem().toString(), date,filePath.toString(),false);
                 else
                        newUser = new User(signUp_EDT_name.getEditText().getText().toString(), signUp_EDT_id.getEditText().getText().toString(), signUp_EDT_email.getEditText().getText().toString(),
                                signUp_EDT_phone.getEditText().getText().toString(), signUp_EDT_password.getEditText().getText().toString(),  signUp_SPI_bloodTypes.getSelectedItem().toString(), date,"https://firebasestorage.googleapis.com/v0/b/final-project-ff1e8.appspot.com/o/images%2Fprofile.png?alt=media&token=b177f2a3-f5fd-4dc7-a749-cd3fff20827e",false);

                    getFromMSP();
                    putOnMSP();
                    allUsers.addToList(newUser);
                    myRef.child("Users").child(signUp_EDT_id.getEditText().getText().toString()).setValue(newUser);
                    putOnMSP();
                    startActivity(new Intent(SignUpPage.this, Menu.class));
                    uploadImage();
                }
            }
        });

    }

    private boolean checkData(){
        if(signUp_EDT_name.getEditText().getText().toString().equals("")) {
            signUp_EDT_name.setError("");
            data=false;
        }
        if(signUp_EDT_id.getEditText().getText().toString().equals("")) {
            signUp_EDT_id.setError("");
            data=false;
        }
        if(signUp_EDT_email.getEditText().getText().toString().equals("")) {
            signUp_EDT_email.setError("");
            data=false;
        }
        if(signUp_EDT_phone.getEditText().getText().toString().equals("")) {
            signUp_EDT_phone.setError("");
            data=false;
        }
        if(signUp_EDT_password.getEditText().getText().toString().equals("")) {
            signUp_EDT_password.setError("");
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
        signUp_SPI_bloodTypes =  findViewById(R.id.signUp_SPI_bloodTypes);
        signUp_TXT_birthDatePicker = findViewById(R.id.signUp_TXT_birthDatePicker);
        sign_up_IMG_logo = findViewById(R.id.sign_up_IMG_logo);

    }




    // Select Image method
    private void SelectImage()
    {

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

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(getContentResolver(), filePath);
                sign_up_IMG_logo.setImageBitmap(bitmap);

            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            imageUrl = "images/"+ UUID.randomUUID().toString();
            StorageReference ref = storageReference.child(imageUrl);
            newUser.setImageUser(imageUrl);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(SignUpPage.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(SignUpPage.this,"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
}
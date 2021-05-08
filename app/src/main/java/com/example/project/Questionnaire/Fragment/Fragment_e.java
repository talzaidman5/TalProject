package com.example.project.Questionnaire.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.activitis.client.ActivityMainForm;
import com.example.project.activitis.client.ActivityMyProfile;
import com.example.project.activitis.client.ActivityProfileMenu;
import com.example.project.data.AllUsers;
import com.example.project.data.Form;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;


public class Fragment_e extends Fragment {

    String fileName = "determination_algorithm.txt";
    private ArrayList<String> algo = new ArrayList<>();
    private View view;
    private MySheredP msp;
    private AllUsers allUsers;
    private User user;
    private Form form;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("FB");
    private SearchableSpinner spn_my_spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_e, container, false);
        spn_my_spinner = view.findViewById(R.id.spinner);
        msp = new MySheredP(getContext());
        getFromMSP();
        checkAlgo();

        readFile();
        user = allUsers.getUserByEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//        if (checkAlgo()) {
//            if (user != null) {
//                user.setCanDonateBlood(true);
//                myRef.child("Users").child(user.getID()).setValue(user);
//                Toast.makeText(getContext(), "אתה יכול לתרום דם!", Toast.LENGTH_SHORT);
//                canDonateAlert();
//            }
//        }
        return view;
    }




    private void checkAlgo() {
       // if (form.getSign()) {
            if (form.checkForm())
                canDonateAlert(true);
            else
                canDonateAlert(false);


    }
    private void canDonateAlert(boolean isCan) {
        String res= "";
        if(isCan)
            res = "אתה יכול לתרום!";
        else
            res = "אינך יכול לתרום!";

        new AlertDialog.Builder(getContext())
                .setTitle("")
                .setMessage(res)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), ActivityProfileMenu.class);
                        startActivity(intent);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }



    private AllUsers getFromMSP() {
        String dataAll = msp.getString(Constants.KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(dataAll);
        String dataForm = msp.getString(Constants.KEY_FORM_DATA, "NA");
        form = new Form(dataForm);
        return allUsers;
    }

    private void readFile() {

        BufferedReader reader;
        String[] temp;
        try {
            final InputStream file = getActivity().getAssets().open(fileName);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    temp = line.split("-");
                    if (temp.length > 1) {
                        String s = temp[0] + " " + temp[2];
                        algo.add(s);

                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
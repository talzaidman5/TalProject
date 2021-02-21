package com.example.project.Questionnaire.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Fragment_e extends Fragment {

    String fileName = "determination_algorithm.txt";
    private ArrayList<String> algo = new ArrayList<>();
    private View view;
    private Spinner fragmentc_TXT_Health_problem;
    private MySheredP msp;
    private AllUsers allUsers;
    private User user;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("FB");
    private SearchableSpinner spn_my_spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_e, container, false);
        fragmentc_TXT_Health_problem= view.findViewById(R.id.fragmentc_TXT_Health_problem);
        getFromMSP();
         spn_my_spinner = view.findViewById(R.id.spinner);

        msp = new MySheredP(getContext());
        readFile();

        if(checkAlgo()) {
            user = allUsers.getUserByUUID(android.provider.Settings.Secure.getString(this.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
            myRef.child("Users").child(user.getID()).setValue(user);
            Toast.makeText(getContext(), "אתה יכול לתרום דם!", Toast.LENGTH_SHORT);

            }
        return view;
    }

    private boolean checkAlgo() {
        for (String str: algo)
            if(str.contains(fragmentc_TXT_Health_problem.getSelectedItem().toString()))
                return false;
            return true;
    }
    private AllUsers getFromMSP() {
        String dataAll = msp.getString(Constants.KEY_MSP_ALL, "NA");
        allUsers = new AllUsers(dataAll);
        return allUsers;
    }

    private void readFile()  {

        BufferedReader reader;
        String []temp;
        try {
            final InputStream file = getActivity().getAssets().open(fileName);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if(line != null) {
                    temp = line.split("-");
                    String s = temp[0] + " " + temp[2];
                    algo.add(s);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
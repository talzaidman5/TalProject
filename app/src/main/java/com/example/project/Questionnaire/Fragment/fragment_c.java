package com.example.project.Questionnaire.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.R;
import com.example.project.utils.MySheredP;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class fragment_c extends Fragment {
    private View view;
    private Button next;
    private ArrayList<String> spinnerArray;
    public static Spinner spn_my_spinner;
    private ArrayList<String> algo = new ArrayList<>();
    String fileName = "determination_algorithm.txt";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.freagment_c,container,false);

        spn_my_spinner = view.findViewById(R.id.fragmentc_TXT_Health_problem);

        spinnerArray = new ArrayList<String>();
      /*  spinnerArray.add("one");
        spinnerArray.add("two");
        spinnerArray.add("three");
        spinnerArray.add("four");
        spinnerArray.add("five");*/

        readFile();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, algo);
        spn_my_spinner.setAdapter(spinnerArrayAdapter);

        next = view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if(checkData()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new fragment_d();
                ft.replace(R.id.main_LIN, fragment);
                ft.addToBackStack(null);
                ft.commit();
                //   }
            }
        });
        findViews(view);
        return view;
    }

    private void findViews(View view) {
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
                    if(temp.length>1)
                    algo.add(temp[2]);
                    else
                        algo.add(temp[0]);

                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }



}
package com.example.project.Questionnaire.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.R;
import com.example.project.data.Form;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_c extends Fragment {
    private View view;
    public static Spinner spn_my_spinner;
    private ArrayList<String> algo = new ArrayList<>();
    String fileName = "determination_algorithm.txt";
    private CheckBox one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve,
            thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen;
    private MySheredP msp;
    private Gson gson = new Gson();
    private Form form;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.freagment_c, container, false);

        findViews(view);
        msp = new MySheredP(getContext());

        readFile();
        getFromMSP();
        setDes();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, algo);
        spn_my_spinner.setAdapter(spinnerArrayAdapter);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        checkData();
        putOnMSP();
    }


    private void checkData() {
        form.getFragmentC().put("one", one.isChecked());
        form.getFragmentC().put("two", two.isChecked());
        form.getFragmentC().put("three", three.isChecked());
        form.getFragmentC().put("four", four.isChecked());
        form.getFragmentC().put("five", five.isChecked());
        form.getFragmentC().put("six", six.isChecked());
        form.getFragmentC().put("seven", seven.isChecked());
        form.getFragmentC().put("eight", eight.isChecked());
        form.getFragmentC().put("nine", nine.isChecked());
        form.getFragmentC().put("ten", ten.isChecked());
        form.getFragmentC().put("eleven", eleven.isChecked());
        form.getFragmentC().put("twelve", twelve.isChecked());
        form.getFragmentC().put("thirteen", thirteen.isChecked());
        form.getFragmentC().put("fourteen", fourteen.isChecked());
        form.getFragmentC().put("fifteen", fifteen.isChecked());
        form.getFragmentC().put("sixteen", sixteen.isChecked());
        form.getFragmentC().put("seventeen", seventeen.isChecked());
        form.getFragmentC().put("eighteen", eighteen.isChecked());
        form.getFragmentC().put("nineteen", nineteen.isChecked());
        form.getFragmentC().put("twelve", twelve.isChecked());
    }

    private void putOnMSP() {
        String jsonForm = gson.toJson(form);
        msp.putString(Constants.KEY_FORM_DATA, jsonForm);

    }

    private void setDes() {
        if (form.getFragmentC().size() > 0) {
            one.setChecked(form.getFragmentC().get("one"));
            two.setChecked(form.getFragmentC().get("two"));
            three.setChecked(form.getFragmentC().get("three"));
            four.setChecked(form.getFragmentC().get("four"));
            five.setChecked(form.getFragmentC().get("five"));
            six.setChecked(form.getFragmentC().get("six"));
            seven.setChecked(form.getFragmentC().get("seven"));
            eight.setChecked(form.getFragmentC().get("eight"));
            nine.setChecked(form.getFragmentC().get("nine"));
            ten.setChecked(form.getFragmentC().get("ten"));
            eleven.setChecked(form.getFragmentC().get("eleven"));
            twelve.setChecked(form.getFragmentC().get("twelve"));
            thirteen.setChecked(form.getFragmentC().get("thirteen"));
            fourteen.setChecked(form.getFragmentC().get("fourteen"));
            fifteen.setChecked(form.getFragmentC().get("fifteen"));
            sixteen.setChecked(form.getFragmentC().get("sixteen"));
            seventeen.setChecked(form.getFragmentC().get("seventeen"));
            eighteen.setChecked(form.getFragmentC().get("eighteen"));
            nineteen.setChecked(form.getFragmentC().get("nineteen"));
            twelve.setChecked(form.getFragmentC().get("twelve"));
        } else {
            one.setChecked(false);
            two.setChecked(false);
            three.setChecked(false);
            four.setChecked(false);
            five.setChecked(false);
            six.setChecked(false);
            seven.setChecked(false);
            eight.setChecked(false);
            nine.setChecked(false);
            ten.setChecked(false);
            eleven.setChecked(false);
            twelve.setChecked(false);
            thirteen.setChecked(false);
            fourteen.setChecked(false);
            fifteen.setChecked(false);
            sixteen.setChecked(false);
            seventeen.setChecked(false);
            eighteen.setChecked(false);
            nineteen.setChecked(false);
            twelve.setChecked(false);

        }
    }

    private Form getFromMSP() {
        String dataAll = msp.getString(Constants.KEY_FORM_DATA, "NA");
        form = new Form(dataAll);
        return form;
    }

    private void findViews(View view) {
        spn_my_spinner = view.findViewById(R.id.fragmentc_TXT_Health_problem);
        one = view.findViewById(R.id.one);
        two = view.findViewById(R.id.two);
        three = view.findViewById(R.id.three);
        four = view.findViewById(R.id.four);
        five = view.findViewById(R.id.five);
        six = view.findViewById(R.id.six);
        seven = view.findViewById(R.id.seven);
        eight = view.findViewById(R.id.eight);
        nine = view.findViewById(R.id.nine);
        ten = view.findViewById(R.id.ten);
        eleven = view.findViewById(R.id.eleven);
        twelve = view.findViewById(R.id.twelve);
        thirteen = view.findViewById(R.id.thirteen);
        fourteen = view.findViewById(R.id.fourteen);
        fifteen = view.findViewById(R.id.fifteen);
        sixteen = view.findViewById(R.id.sixteen);
        seventeen = view.findViewById(R.id.seventeen);
        eighteen = view.findViewById(R.id.eighteen);
        nineteen = view.findViewById(R.id.nineteen);
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
                    if (temp.length > 1)
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
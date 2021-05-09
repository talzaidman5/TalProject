package com.example.project.activitis.client;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.Questionnaire.Fragment.Fragment_e;
import com.example.project.Questionnaire.Fragment.Fragment_a;
import com.example.project.Questionnaire.Fragment.Fragment_b;
import com.example.project.Questionnaire.Fragment.Fragment_c;
import com.example.project.Questionnaire.Fragment.Fragment_d;
import com.example.project.R;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityMainForm extends AppCompatActivity {
    private Button main_form_BTN_prev, main_form_BTN_next;
    private int currentFragment = 0;
    private CheckBox isSign;
    private TextInputLayout questionnairePage_EDT_id, questionnairePage_EDT_first_name, questionnairePage_EDT_family_name,
            questionnairePage_EDT_dateBirth, questionnairePage_EDT_age, questionnairePage_EDT_previous_family_name, questionnairePage_EDT_city, questionnairePage_EDT_postal,
            questionnairePage_EDT_street, questionnairePage_EDT_email, questionnairePage_EDT_mobilePhone, questionnairePage_EDT_OfficePhone, questionnairePage_EDT_HomePhone,
            questionnairePage_EDT_MotherCountry, questionnairePage_EDT_fatherCountry, questionnairePage_EDT_yearImmigration,
            questionnairePage_EDT_patientName,questionnairePage_EDT_CountryBirth;
    private CheckBox questionnairePage_EDT_bloodDonationAgain;
    private boolean isData=false;
    private String buttonClickPrev="prev";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        main_form_BTN_next = findViewById(R.id.main_form_BTN_next);
        main_form_BTN_prev = findViewById(R.id.main_form_BTN_prev);
        showA();

        main_form_BTN_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCurrentFragment("prev");
            }
        });
        main_form_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentFragment("next");
            }
        });

    }

    private void openCurrentFragment(String click){
        switch (currentFragment) {
            case (0): {
                showA(click);
                break;
            }
            case(1):{
                showB(click);
                break;
            }
            case(2):{
                showC(click);
                break;
            }
            case(3):{
                showD(click);
                break;
            }
            case(4):{
                showE(click);
                break;
            }
            default:
                showE(click);
        }
    }


        private void showA (String click) {
            if(click.equals(main_form_BTN_prev))
                currentFragment--;
            else
                currentFragment++;
            Fragment_a fragment_a = new Fragment_a();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FRAME, fragment_a);
            transaction.commit();
        }

        private void showB (String click) {
            if (checkData()) {
                if(click.equals(main_form_BTN_prev))
                    currentFragment--;
                else
                    currentFragment++;
                Fragment_b fragment_b = new Fragment_b();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_FRAME, fragment_b);
                transaction.commit();

        }
            else{
                Toast.makeText(getApplicationContext(),"אנא השלם את הפרטים",Toast.LENGTH_LONG).show();

            }

    }


    private void showC (String click) {
        if(click.equals(main_form_BTN_prev))
            currentFragment--;
        else
            currentFragment++;
            Fragment_c fragment_c = new Fragment_c();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FRAME, fragment_c);
            transaction.commit();
    }

    private void showD (String click) {
        if(click.equals(main_form_BTN_prev))
            currentFragment--;
        else
            currentFragment++;
        Fragment_d fragment_d = new Fragment_d();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME, fragment_d);
        transaction.commit();
    }

    private void showE (String click) {
        isSign = findViewById(R.id.fragmentD_sign);

        if (isSign.isChecked()) {
            if(click.equals(main_form_BTN_prev))
                currentFragment--;
            else
                currentFragment++;
            Fragment_e fragment_e = new Fragment_e();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FRAME, fragment_e);
            transaction.commit();
        }
        else{
            Toast.makeText(getApplicationContext(),"אנא אשר",Toast.LENGTH_LONG).show();

        }
    }

    public boolean checkData() {
        findViews();
        isData=true;
        if (questionnairePage_EDT_first_name.getEditText().getText().length() == 0) {
            questionnairePage_EDT_first_name.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_first_name.setError("");
        if (questionnairePage_EDT_family_name.getEditText().getText().length() == 0) {
            questionnairePage_EDT_family_name.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_family_name.setError("");
        if (questionnairePage_EDT_dateBirth.getEditText().getText().length() == 0) {
            questionnairePage_EDT_dateBirth.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_dateBirth.setError("");
        if (questionnairePage_EDT_age.getEditText().getText().length() == 0) {
            questionnairePage_EDT_age.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_age.setError("");
        if (questionnairePage_EDT_previous_family_name.getEditText().getText().length() == 0) {
            questionnairePage_EDT_previous_family_name.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_previous_family_name.setError("");
        if (questionnairePage_EDT_city.getEditText().getText().length() == 0) {
            questionnairePage_EDT_city.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_city.setError("");
        if (questionnairePage_EDT_postal.getEditText().getText().length() == 0) {
            questionnairePage_EDT_postal.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_postal.setError("");
        if (questionnairePage_EDT_street.getEditText().getText().length() == 0) {
            questionnairePage_EDT_street.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_street.setError("");
        if (questionnairePage_EDT_email.getEditText().getText().length() == 0) {
            questionnairePage_EDT_email.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_email.setError("");
        if (questionnairePage_EDT_mobilePhone.getEditText().getText().length() == 0) {
            questionnairePage_EDT_mobilePhone.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_mobilePhone.setError("");
        if (questionnairePage_EDT_OfficePhone.getEditText().getText().length() == 0) {
            questionnairePage_EDT_OfficePhone.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_OfficePhone.setError("");
        if (questionnairePage_EDT_HomePhone.getEditText().getText().length() == 0)
            if (questionnairePage_EDT_MotherCountry.getEditText().getText().length() == 0) {
                questionnairePage_EDT_MotherCountry.setError("אנא השלם");
                isData = false;
            } else questionnairePage_EDT_MotherCountry.setError("");
            if (questionnairePage_EDT_fatherCountry.getEditText().getText().length() == 0) {
                questionnairePage_EDT_fatherCountry.setError("אנא השלם");
                isData = false;
            } else questionnairePage_EDT_fatherCountry.setError("");
            if (questionnairePage_EDT_fatherCountry.getEditText().getText().length() == 0) {
                questionnairePage_EDT_fatherCountry.setError("אנא השלם");
                isData = false;
            } else questionnairePage_EDT_fatherCountry.setError("");
            if(questionnairePage_EDT_CountryBirth.getEditText().getText().length() == 0) {
                isData = false;
                questionnairePage_EDT_CountryBirth.setError("אנא השלם");
            }
        return isData;
        }
        private void findViews() {
        questionnairePage_EDT_id = findViewById(R.id.questionnairePage_EDT_id);
        questionnairePage_EDT_first_name = findViewById(R.id.questionnairePage_EDT_first_name);
        questionnairePage_EDT_family_name = findViewById(R.id.questionnairePage_EDT_family_name);
        questionnairePage_EDT_dateBirth = findViewById(R.id.questionnairePage_EDT_dateBirth);
        questionnairePage_EDT_age = findViewById(R.id.questionnairePage_EDT_age);
        questionnairePage_EDT_previous_family_name = findViewById(R.id.questionnairePage_EDT_previous_family_name);
        questionnairePage_EDT_city = findViewById(R.id.questionnairePage_EDT_city);
        questionnairePage_EDT_postal = findViewById(R.id.questionnairePage_EDT_postal);
        questionnairePage_EDT_street = findViewById(R.id.questionnairePage_EDT_street);
        questionnairePage_EDT_email = findViewById(R.id.questionnairePage_EDT_email);
        questionnairePage_EDT_mobilePhone = findViewById(R.id.questionnairePage_EDT_mobilePhone);
        questionnairePage_EDT_OfficePhone = findViewById(R.id.questionnairePage_EDT_OfficePhone);
        questionnairePage_EDT_HomePhone = findViewById(R.id.questionnairePage_EDT_HomePhone);
        questionnairePage_EDT_MotherCountry = findViewById(R.id.questionnairePage_EDT_MotherCountry);
        questionnairePage_EDT_fatherCountry = findViewById(R.id.questionnairePage_EDT_fatherCountry);
        questionnairePage_EDT_yearImmigration = findViewById(R.id.questionnairePage_EDT_yearImmigration);
        questionnairePage_EDT_patientName = findViewById(R.id.questionnairePage_EDT_patientName);
        questionnairePage_EDT_bloodDonationAgain = findViewById(R.id.questionnairePage_EDT_bloodDonationAgain);
        questionnairePage_EDT_CountryBirth = findViewById(R.id.questionnairePage_EDT_CountryBirth);
    }

}
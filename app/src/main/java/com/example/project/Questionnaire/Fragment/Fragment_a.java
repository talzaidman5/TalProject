package com.example.project.Questionnaire.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.Encryption;
import com.example.project.data.Form;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Date;

public class Fragment_a extends Fragment {
    private View view;
    private MySheredP msp;
    private User user;
    private Gson gson = new Gson();
    private Form form;

    private RadioButton radio_female, radio_male;
    private TextInputLayout questionnairePage_EDT_id, questionnairePage_EDT_first_name, questionnairePage_EDT_family_name,
            questionnairePage_EDT_dateBirth, questionnairePage_EDT_age, questionnairePage_EDT_previous_family_name, questionnairePage_EDT_city, questionnairePage_EDT_postal,
            questionnairePage_EDT_street, questionnairePage_EDT_email, questionnairePage_EDT_mobilePhone, questionnairePage_EDT_OfficePhone, questionnairePage_EDT_HomePhone,
            questionnairePage_EDT_MotherCountry, questionnairePage_EDT_fatherCountry, questionnairePage_EDT_CountryBirth, questionnairePage_EDT_yearImmigration;
    private CheckBox questionnairePage_EDT_bloodDonationAgain;
    public static boolean isData = true;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.freagment_a, container, false);
        msp = new MySheredP(getContext());

        findViews(view);
        try {
            fillDataFromUserProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        checkData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        checkData();
        form = new Form(user, questionnairePage_EDT_previous_family_name.getEditText().getText().toString(),
                questionnairePage_EDT_city.getEditText().getText().toString(),
                questionnairePage_EDT_postal.getEditText().getText().toString(),
                questionnairePage_EDT_street.getEditText().getText().toString(),
                questionnairePage_EDT_OfficePhone.getEditText().getText().toString(),
                questionnairePage_EDT_HomePhone.getEditText().getText().toString(),
                questionnairePage_EDT_MotherCountry.getEditText().getText().toString(),
                questionnairePage_EDT_fatherCountry.getEditText().getText().toString(),
                questionnairePage_EDT_yearImmigration.getEditText().getText().toString(),
                questionnairePage_EDT_bloodDonationAgain.isChecked(),
                questionnairePage_EDT_CountryBirth.getEditText().getText().toString());

        putOnMSP();

    }

    private User getFromMSP() {
        String dataAll = msp.getString(Constants.KEY_FORM_DATA, "NA");
        String dataUser = msp.getString(Constants.KEY_MSP, "NA");
        form = new Form(dataAll);
        user = new User(dataUser);
        form.setUser(user);
        return user;
    }

    private void fillDataFromUserProfile() throws Exception {
       user= getFromMSP();
        if (user != null) {
            questionnairePage_EDT_id.getEditText().setText(user.getID());
            questionnairePage_EDT_first_name.getEditText().setText(Encryption.decrypt(user.getFirstName()));
            questionnairePage_EDT_family_name.getEditText().setText(Encryption.decrypt(user.getLastName()));
            questionnairePage_EDT_city.getEditText().setText(Encryption.decrypt(user.getCity()));
            if (user.getGender().equals("MALE"))
                radio_male.setChecked(true);
            else
                radio_female.setChecked(true);

            String finalDate = getDateStr(user.getBirthDate());
            questionnairePage_EDT_dateBirth.getEditText().setText(finalDate);
            questionnairePage_EDT_age.getEditText().setText(user.getAge() + "");
            questionnairePage_EDT_email.getEditText().setText(Encryption.decrypt(user.getEmail()));
            questionnairePage_EDT_previous_family_name.getEditText().setText(Encryption.decrypt(form.getPrevious_family_name()));
            questionnairePage_EDT_mobilePhone.getEditText().setText(Encryption.decrypt(user.getPhoneNumber()));

            questionnairePage_EDT_postal.getEditText().setText(form.getPostal());
            questionnairePage_EDT_street.getEditText().setText(form.getStreet());
            questionnairePage_EDT_mobilePhone.getEditText().setText(user.getPhoneNumber());
            questionnairePage_EDT_OfficePhone.getEditText().setText(form.getOfficePhone());
            questionnairePage_EDT_HomePhone.getEditText().setText(form.getHomePhone());
            questionnairePage_EDT_MotherCountry.getEditText().setText(form.getMotherCountry());
            questionnairePage_EDT_fatherCountry.getEditText().setText(form.getFatherCountry());
            questionnairePage_EDT_CountryBirth.getEditText().setText(form.getCountryBirth());
            questionnairePage_EDT_yearImmigration.getEditText().setText(form.getYearImmigration());
            questionnairePage_EDT_bloodDonationAgain.setChecked(form.isBloodDonationAgain());
        }
    }

    private String getDateStr(Date date) {
        String res = date.getDate() + "/" + date.getMonth() + "/" + date.getYear();
        return res;
    }


    private void findViews(View view) {
        questionnairePage_EDT_id = view.findViewById(R.id.questionnairePage_EDT_id);
        questionnairePage_EDT_first_name = view.findViewById(R.id.questionnairePage_EDT_first_name);
        questionnairePage_EDT_family_name = view.findViewById(R.id.questionnairePage_EDT_family_name);
        questionnairePage_EDT_dateBirth = view.findViewById(R.id.questionnairePage_EDT_dateBirth);
        questionnairePage_EDT_age = view.findViewById(R.id.questionnairePage_EDT_age);
        questionnairePage_EDT_previous_family_name = view.findViewById(R.id.questionnairePage_EDT_previous_family_name);
        questionnairePage_EDT_city = view.findViewById(R.id.questionnairePage_EDT_city);
        questionnairePage_EDT_postal = view.findViewById(R.id.questionnairePage_EDT_postal);
        questionnairePage_EDT_street = view.findViewById(R.id.questionnairePage_EDT_street);
        questionnairePage_EDT_email = view.findViewById(R.id.questionnairePage_EDT_email);
        questionnairePage_EDT_mobilePhone = view.findViewById(R.id.questionnairePage_EDT_mobilePhone);
        questionnairePage_EDT_OfficePhone = view.findViewById(R.id.questionnairePage_EDT_OfficePhone);
        questionnairePage_EDT_HomePhone = view.findViewById(R.id.questionnairePage_EDT_HomePhone);
        questionnairePage_EDT_MotherCountry = view.findViewById(R.id.questionnairePage_EDT_MotherCountry);
        questionnairePage_EDT_fatherCountry = view.findViewById(R.id.questionnairePage_EDT_fatherCountry);
        questionnairePage_EDT_CountryBirth = view.findViewById(R.id.questionnairePage_EDT_CountryBirth);
        questionnairePage_EDT_yearImmigration = view.findViewById(R.id.questionnairePage_EDT_yearImmigration);
        radio_female = view.findViewById(R.id.radio_female);
        radio_male = view.findViewById(R.id.radio_male);
        questionnairePage_EDT_bloodDonationAgain = view.findViewById(R.id.questionnairePage_EDT_bloodDonationAgain);
    }

    private void putOnMSP() {
        String jsonForm = gson.toJson(form);
        msp.putString(Constants.KEY_FORM_DATA, jsonForm);

    }

    public boolean checkData() {
        if (questionnairePage_EDT_id.getEditText().getText().length() == 0) {
            questionnairePage_EDT_id.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_id.setError("");

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


        if (questionnairePage_EDT_HomePhone.getEditText().getText().length() == 0) {
            questionnairePage_EDT_HomePhone.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_HomePhone.setError("");

        if (questionnairePage_EDT_MotherCountry.getEditText().getText().length() == 0) {
            questionnairePage_EDT_MotherCountry.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_MotherCountry.setError("");

        if (questionnairePage_EDT_fatherCountry.getEditText().getText().length() == 0) {
            questionnairePage_EDT_fatherCountry.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_fatherCountry.setError("");

           if (questionnairePage_EDT_CountryBirth.getEditText().getText().length() == 0) {
               questionnairePage_EDT_CountryBirth.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_CountryBirth.setError("");

        if (questionnairePage_EDT_yearImmigration.getEditText().getText().length() == 0) {
            questionnairePage_EDT_yearImmigration.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_yearImmigration.setError("");
        if (questionnairePage_EDT_fatherCountry.getEditText().getText().length() == 0) {
            questionnairePage_EDT_fatherCountry.setError("אנא השלם");
            isData = false;
        } else questionnairePage_EDT_fatherCountry.setError("");
        return isData;
    }
}
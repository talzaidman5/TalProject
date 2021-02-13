package com.example.project.Questionnaire.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class fragment_a extends Fragment {
    private View view;

    private TextInputLayout questionnairePage_EDT_id,questionnairePage_EDT_first_name,questionnairePage_EDT_family_name,
            questionnairePage_EDT_dateBirth,questionnairePage_EDT_age,questionnairePage_EDT_lastName,questionnairePage_EDT_city,questionnairePage_EDT_postal,
            questionnairePage_EDT_street,questionnairePage_EDT_email,questionnairePage_EDT_mobilePhone,questionnairePage_EDT_OfficePhone,questionnairePage_EDT_HomePhone,
            questionnairePage_EDT_MotherCountry,questionnairePage_EDT_fatherCountry,questionnairePage_EDT_yearImmigration,
            questionnairePage_EDT_CountryBirth,questionnairePage_EDT_patientName;
    private CheckBox questionnairePage_EDT_bloodDonationAgain;
    private RadioButton radio_male,radio_female;
    public static boolean isData=true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.freagment_a,container,false);

        findViews(view);
        isData= checkData();
        return view;
    }

    private void findViews(View view) {
        questionnairePage_EDT_id = view.findViewById(R.id.questionnairePage_EDT_id);
        questionnairePage_EDT_first_name= view.findViewById(R.id.questionnairePage_EDT_first_name);
        questionnairePage_EDT_family_name= view.findViewById(R.id.questionnairePage_EDT_family_name);
        questionnairePage_EDT_dateBirth= view.findViewById(R.id.questionnairePage_EDT_dateBirth);
        questionnairePage_EDT_age= view.findViewById(R.id.questionnairePage_EDT_age);
        questionnairePage_EDT_lastName= view.findViewById(R.id.questionnairePage_EDT_lastName);
        questionnairePage_EDT_city= view.findViewById(R.id.questionnairePage_EDT_city);
        questionnairePage_EDT_postal= view.findViewById(R.id.questionnairePage_EDT_postal);
        questionnairePage_EDT_street= view.findViewById(R.id.questionnairePage_EDT_street);
        questionnairePage_EDT_email= view.findViewById(R.id.questionnairePage_EDT_email);
        questionnairePage_EDT_mobilePhone= view.findViewById(R.id.questionnairePage_EDT_mobilePhone);
        questionnairePage_EDT_OfficePhone= view.findViewById(R.id.questionnairePage_EDT_OfficePhone);
        questionnairePage_EDT_HomePhone= view.findViewById(R.id.questionnairePage_EDT_HomePhone);
        questionnairePage_EDT_bloodDonationAgain= view.findViewById(R.id.questionnairePage_EDT_bloodDonationAgain);
        questionnairePage_EDT_MotherCountry= view.findViewById(R.id.questionnairePage_EDT_MotherCountry);
        questionnairePage_EDT_fatherCountry= view.findViewById(R.id.questionnairePage_EDT_fatherCountry);
        questionnairePage_EDT_yearImmigration= view.findViewById(R.id.questionnairePage_EDT_yearImmigration);
        questionnairePage_EDT_CountryBirth= view.findViewById(R.id.questionnairePage_EDT_CountryBirth);
        questionnairePage_EDT_patientName= view.findViewById(R.id.questionnairePage_EDT_patientName);
    }


    public boolean checkData(){
       if(questionnairePage_EDT_id.getEditText().getText().length()==0){ questionnairePage_EDT_id.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_first_name.getEditText().getText().length()==0){ questionnairePage_EDT_first_name.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_family_name.getEditText().getText().length()==0){ questionnairePage_EDT_family_name.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_dateBirth.getEditText().getText().length()==0) {questionnairePage_EDT_dateBirth.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_age.getEditText().getText().length()==0) {questionnairePage_EDT_age.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_lastName.getEditText().getText().length()==0){ questionnairePage_EDT_lastName.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_city.getEditText().getText().length()==0){ questionnairePage_EDT_city.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_postal.getEditText().getText().length()==0){ questionnairePage_EDT_postal.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_street.getEditText().getText().length()==0){ questionnairePage_EDT_street.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_email.getEditText().getText().length()==0){ questionnairePage_EDT_email.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_mobilePhone.getEditText().getText().length()==0){ questionnairePage_EDT_mobilePhone.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_OfficePhone.getEditText().getText().length()==0){ questionnairePage_EDT_OfficePhone.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_HomePhone.getEditText().getText().length()==0){ questionnairePage_EDT_HomePhone.setError("Can't be null"); isData=false;}
       if(!questionnairePage_EDT_bloodDonationAgain.isSelected()){ questionnairePage_EDT_bloodDonationAgain.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_MotherCountry.getEditText().getText().length()==0){ questionnairePage_EDT_MotherCountry.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_fatherCountry.getEditText().getText().length()==0){ questionnairePage_EDT_fatherCountry.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_yearImmigration.getEditText().getText().length()==0){ questionnairePage_EDT_yearImmigration.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_fatherCountry.getEditText().getText().length()==0){ questionnairePage_EDT_fatherCountry.setError("Can't be null"); isData=false;}
       if(questionnairePage_EDT_patientName.getEditText().getText().length()==0) {   questionnairePage_EDT_patientName.setError("Can't be null"); isData=false;}
        return isData;
    }
}
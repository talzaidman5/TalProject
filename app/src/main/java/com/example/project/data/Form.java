package com.example.project.data;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Form {
    private User user;
    private String previous_family_name;
    private String city;
    private String postal;
    private String street;
    private String OfficePhone;
    private String homePhone;
    private String MotherCountry;
    private String fatherCountry;
    private String countryBirth;
    private String date;
    private boolean sign,research,research2;
    private String yearImmigration;
    private Map<String, Boolean> allDiseases;
    private Map<String, Boolean> fragmentC;
    private  boolean isSign;
    private boolean isBloodDonationAgain;
    public Form(User user, String previous_family_name, String city, String postal, String street, String officePhone,
                String homePhone, String motherCountry, String fatherCountry, String yearImmigration,
                boolean isBloodDonationAgain, String countryBirth) {
        this.user = user;
        this.previous_family_name = previous_family_name;
        this.city = city;
        this.postal = postal;
        this.street = street;
        this.OfficePhone = officePhone;
        this.homePhone = homePhone;
        this.MotherCountry = motherCountry;
        this.fatherCountry = fatherCountry;
        this.yearImmigration = yearImmigration;
        this.allDiseases = new HashMap<String, Boolean>();
        this.fragmentC = new HashMap<String, Boolean>();
        this.isBloodDonationAgain= isBloodDonationAgain;
        this.countryBirth = countryBirth;
    }


    public Form() {

    }

    public boolean isBloodDonationAgain() {
        return this.isBloodDonationAgain;
    }

    public void setBloodDonationAgain(boolean bloodDonationAgain) {
        isBloodDonationAgain = bloodDonationAgain;
    }

    public Form(String data) {
        this(createFormFromString(data));
    }

    public Form(Form other) {
        this.user = other.getUser();
        this.previous_family_name = other.getPrevious_family_name();
        this.city = other.getCity();
        this.postal = other.getPostal();
        this.street = other.getStreet();
        this.OfficePhone = other.getOfficePhone();
        this.homePhone = other.getHomePhone();
        this.MotherCountry = other.getMotherCountry();
        this.fatherCountry = other.getFatherCountry();
        this.yearImmigration = other.getYearImmigration();
        this.allDiseases = other.allDiseases;
        this.fragmentC = other.fragmentC;
        this.sign = other.sign;
        this.research = other.research;
        this.research2 = other.research2;
        this.date = other.date;
        this.countryBirth =other.countryBirth;
        this.isBloodDonationAgain = other.isBloodDonationAgain;
    }


    private static Form createFormFromString(String data) {
        Form temp;
        if (data == "NA" || data.equals("null"))
            temp = new Form();
        else
            temp = new Gson().fromJson(data, Form.class);
        return temp;
    }

    public String getCountryBirth() {
        return countryBirth;
    }

    public void setCountryBirth(String countryBirth) {
        this.countryBirth = countryBirth;
    }

    public User getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }


    public boolean isResearch() {
        return research;
    }

    public void setResearch(boolean research) {
        this.research = research;
    }

    public boolean isResearch2() {
        return research2;
    }

    public void setResearch2(boolean research2) {
        this.research2 = research2;
    }

    public Map<String, Boolean> getAllDiseases() {
        return allDiseases;
    }

    public void setAllDiseases(Map<String, Boolean> allDiseases) {
        this.allDiseases = allDiseases;
    }

    public Map<String, Boolean> getFragmentC() {
        return fragmentC;
    }

    public void setFragmentC(Map<String, Boolean> fragmentC) {
        this.fragmentC = fragmentC;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPrevious_family_name() {
        return previous_family_name;
    }

    public void setPrevious_family_name(String previous_family_name) {
        this.previous_family_name = previous_family_name;
    }

    public boolean checkForm(){
        for (Map.Entry<String, Boolean> entry : allDiseases.entrySet()) {
           if(entry.getValue()==true)
               return false;
        }

        for (Map.Entry<String, Boolean> entry : fragmentC.entrySet()) {
            if(entry.getValue()==true)
                return false;
        }
        return true;

    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getOfficePhone() {
        return OfficePhone;
    }

    public void setOfficePhone(String officePhone) {
        OfficePhone = officePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMotherCountry() {
        return MotherCountry;
    }

    public void setMotherCountry(String motherCountry) {
        MotherCountry = motherCountry;
    }

    public String getFatherCountry() {
        return fatherCountry;
    }

    public void setFatherCountry(String fatherCountry) {
        this.fatherCountry = fatherCountry;
    }

    public String getYearImmigration() {
        return yearImmigration;
    }

    public void setYearImmigration(String yearImmigration) {
        this.yearImmigration = yearImmigration;
    }
}

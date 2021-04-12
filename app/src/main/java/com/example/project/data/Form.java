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
    private String yearImmigration;
    private Map<String, Boolean> allDiseases;

    public Form(User user, String previous_family_name, String city, String postal, String street, String officePhone, String homePhone, String motherCountry, String fatherCountry, String yearImmigration) {
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
        allDiseases = new HashMap<String, Boolean>();
    }

    public Form() {

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
    }

    private static Form createFormFromString(String data) {
        Form temp;
        if (data == "NA" || data.equals("null"))
            temp = new Form();
        else
            temp = new Gson().fromJson(data, Form.class);
        return temp;
    }

    public User getUser() {
        return user;
    }

    public Map<String, Boolean> getAllDiseases() {
        return allDiseases;
    }

    public void setAllDiseases(Map<String, Boolean> allDiseases) {
        this.allDiseases = allDiseases;
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

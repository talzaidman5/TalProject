package com.example.project;

import android.net.Uri;

import com.google.gson.Gson;

import java.util.Date;

public class User {

    private String fullName;
    private String ID;
    private String email;
    private String phoneNumber;
    private String password;
    private String country;
    private String bloodType;
    private Date birthDate;
   // private String imageUser;

    public User(String fullName, String ID, String email, String phoneNumber, String password, String country, String bloodType, Date birthDate) {
        this.fullName = fullName;
        this.ID = ID;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.country = country;
        this.bloodType = bloodType;
        this.birthDate = birthDate;
    //    this.imageUser = image;
    }
    public User(String data)
    {
       this(createUserFromString(data));
    }

    public User(User other) {
        this.fullName = other.fullName;
        this.ID = other.ID;
        this.email = other.email;
        this.phoneNumber = other.phoneNumber;
        this.password = other.password;
        this.country = other.country;
        this.bloodType = other.bloodType;
        this.birthDate = other.birthDate;
     //   this.imageUser = other.imageUser;
    }

    private static User createUserFromString(String data) {
       User temp;
        if (data == "NA")
            temp = new User();
        else
            temp = new Gson().fromJson(data, User.class);
        return temp;
    }

    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

//    public String getImageUser() {
//        return imageUser;
//    }
//
//    public void setImageUser(Uri imageUser) {
//        this.imageUser = imageUser.toString();
//    }
}

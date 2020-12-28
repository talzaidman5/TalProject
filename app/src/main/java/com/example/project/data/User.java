package com.example.project.data;

import com.google.gson.Gson;

import java.util.Date;

public class User {
    public enum USER_TYPE
    {
        CLIENT,MANAGER;
    }

    private String fullName;
    private String ID;
    private String email;
    private String phoneNumber;
    private String password;
    private String bloodType;
    private Date birthDate;
    private String imageUser;
    private Boolean remember;
    private  USER_TYPE userType;


    public User(String fullName, String ID, String email, String phoneNumber, String password, String bloodType, Date birthDate, String image, Boolean isRemember) {
        this.fullName = fullName;
        this.ID = ID;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.bloodType = bloodType;
        this.birthDate = birthDate;
        this.imageUser = image;
        this.remember = isRemember;
        this.userType = USER_TYPE.CLIENT;
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
        this.bloodType = other.bloodType;
        this.birthDate = other.birthDate;
        this.imageUser = other.imageUser;
        this.remember = other.remember;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getImageUser() {
        return imageUser;
    }

    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }

    private static User createUserFromString(String data) {
       User temp;
        if (data == "NA" || data.equals("null"))
            temp = new User();
        else
            temp = new Gson().fromJson(data, User.class);
        return temp;
    }

    public User() {
        remember = false;
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


    public USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(USER_TYPE userType) {
        this.userType = userType;
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

}

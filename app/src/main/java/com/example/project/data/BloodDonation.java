package com.example.project.data;

import java.util.Date;

public class BloodDonation {
    private String city;
    private String date;
    private String userID;
    private String booldDonationId;

    public BloodDonation(){}

    public BloodDonation(String city, String date, String userID){
        this.city = city;
        this.date = date;
        this.userID = userID;

    }
    public String getCity() {
        return city;
    }

    public String getBooldDonationId() {
        return booldDonationId;
    }

    public void setBooldDonationId(String booldDonationId) {
        this.booldDonationId = booldDonationId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userId) {
        this.userID = userId;
    }
}

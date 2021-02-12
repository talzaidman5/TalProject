package com.example.project.data;

import java.util.Date;

public class BloodDonation {
    private String city;
    private Date date;

    public BloodDonation(){}

    public BloodDonation(String city, Date date){
        this.city = city;
        this.date = date;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

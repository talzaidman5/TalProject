package com.example.project.data;

import java.util.Date;

public class BloodDonation {
    private String city;
    private Date date;
    private User user;

    public BloodDonation(){}

    public BloodDonation(String city, Date date, User user){
        this.city = city;
        this.date = date;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }
}

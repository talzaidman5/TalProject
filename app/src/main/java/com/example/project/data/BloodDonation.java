package com.example.project.data;

import android.location.Address;

import java.util.Date;

public class BloodDonation {
    private Address address;
    private Date date;

    public BloodDonation(){}

    public BloodDonation(Address address, Date date){
        this.address = address;
        this.date = date;
    }
    public Address getCity() {
        return address;
    }

    public void setCity(Address city) {
        this.address = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

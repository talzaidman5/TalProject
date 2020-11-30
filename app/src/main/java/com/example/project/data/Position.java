package com.example.project.data;

import java.util.Date;

public class Position {
    private String cityName;
    private String streetName;
    private int number;
    private Date date;
    private String startHour;
    private String endHour;

    public Position(){}

    public Position(String cityName, String streetName, int number, Date date, String startHour, String endHour) {
        this.cityName = cityName;
        this.streetName = streetName;
        this.number = number;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndtHour(String endHour) {
        this.endHour = endHour;
    }
}

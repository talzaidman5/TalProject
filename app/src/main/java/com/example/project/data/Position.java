package com.example.project.data;

import java.util.Date;

public class Position {
    private String location;
    private int number;
    private Date date;
    private String startHour;
    private String endHour;
    private String mainImage = "";


    public Position(){}

    public Position(String location, int number, Date date, String startHour, String endHour, String mainImage) {
        this.location = location;
        this.number = number;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.mainImage = mainImage;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

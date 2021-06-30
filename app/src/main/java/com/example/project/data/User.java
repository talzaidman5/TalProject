package com.example.project.data;

import android.util.Log;

import com.example.project.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User {
    public enum USER_TYPE {
        CLIENT, MANAGER;
    }

    public enum GENDER {
        FEMALE, MALE;
    }

    private String firstName;
    private String lastName;
    private String ID;
    private String email;
    private String phoneNumber;
    private String password;
    private String bloodType;
    private Date birthDate;
    private String city;
    private Boolean remember;
    private Boolean canDonateBlood;
    private USER_TYPE userType;
    private GENDER gender;
 //   private ArrayList<BloodDonation> allBloodDonations;
    private String uuID;
    private int age;
    private Date lastBloodDonation;
    private String token;
    private  int countBloodDonations;

    public User(String firstName, String lastName, String ID, String email, String phoneNumber, String password, String bloodType, Date birthDate, Boolean isRemember, String uuid, String city, GENDER gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.bloodType = bloodType;
        this.birthDate = birthDate;
        this.remember = isRemember;
        this.city = city;
        this.countBloodDonations = 0;
        if (ID.equals(Constants.MANAGER_ID))
            this.userType = USER_TYPE.MANAGER;
        else
            this.userType = USER_TYPE.CLIENT;

       // this.allBloodDonations = new ArrayList<>();
        this.uuID = uuid;
        this.age = Calendar.getInstance().get(Calendar.YEAR) - birthDate.getYear();
        this.canDonateBlood = false;
        this.gender = gender;
    }



    public void updateCountBlood(){
        this.countBloodDonations ++;
    }
    public int getCountBloodDonations() {
        return countBloodDonations;
    }

    public void setCountBloodDonations(int countBloodDonations) {
        this.countBloodDonations = countBloodDonations;
    }

    public GENDER getGender() {
        return gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public User(String data) {
        this(createUserFromString(data));
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getCanDonateBlood() {
        return canDonateBlood;
    }

    public void setCanDonateBlood(Boolean canDonateBlood) {
        this.canDonateBlood = canDonateBlood;
    }

    public String getUuID() {
        return uuID;
    }

    public void setUuID(String uuID) {
        this.uuID = uuID;
    }

    public User(User other) {
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.ID = other.ID;
        this.email = other.email;
        this.phoneNumber = other.phoneNumber;
        this.password = other.password;
        this.bloodType = other.bloodType;
        this.birthDate = other.birthDate;
        this.remember = other.remember;
        this.gender = other.gender;
        this.uuID = other.uuID;
     //   this.allBloodDonations = other.allBloodDonations;
        this.userType = other.userType;
        this.lastBloodDonation = other.lastBloodDonation;
        this.city = other.city;
        this.canDonateBlood = other.canDonateBlood;
        this.countBloodDonations = other.countBloodDonations;
    }

    public Date getLastBloodDonation() {
        return lastBloodDonation;
    }

    public void setLastBloodDonation(Date lastBloodDonation) {

        if (this.lastBloodDonation == null) {
            this.lastBloodDonation = lastBloodDonation;
            return;
        }
        if( this.lastBloodDonation.before(lastBloodDonation))
            this.lastBloodDonation = lastBloodDonation;
    }



    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }


    public int getAge() {
        Calendar today = Calendar.getInstance();
        age = today.get(Calendar.YEAR) - birthDate.getYear() - 1900;
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private static User createUserFromString(String data) {
        User temp;
        if (data == "NA" || data.equals("null"))
            temp = new User();
        else
            temp = new Gson().fromJson(data, User.class);
        return temp;
    }

    /*public ArrayList<BloodDonation> addBloodDonation(BloodDonation bloodDonation) {
        Log.d("tal",bloodDonation.getCity());

        if (allBloodDonations == null)
            allBloodDonations = new ArrayList<>();
        allBloodDonations.add(bloodDonation);
        return allBloodDonations;
    }*/


    public User() {
        this.remember = false;
        this.userType = USER_TYPE.CLIENT;

    }

    public String getFirstName() {
        return firstName;
    }

   /* public ArrayList<BloodDonation> getAllBloodDonations() {
        if (allBloodDonations == null)
            allBloodDonations = new ArrayList<>();
        return allBloodDonations;
    }*/

 /*   public void setAllBloodDonations(ArrayList<BloodDonation> allBloodDonations) {
        this.allBloodDonations = allBloodDonations;
    }*/

    public void setFirstName(String fullName) {
        this.firstName = fullName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(String userId) {
        if (userId.equals(Constants.MANAGER_ID))
            this.userType = USER_TYPE.MANAGER;
        else
            this.userType = USER_TYPE.CLIENT;
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
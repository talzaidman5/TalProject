package com.example.project.data;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AllUsers {

    private List<User> allUser;

    public List<User> getAllUser() {
        return allUser;
    }

    public void setAllUser(List<User> allUser) {
        this.allUser = allUser;
    }


    public AllUsers(String data) {
        createUsersFromString(data);
    }

    public AllUsers() {
        this.allUser = new ArrayList<>();
    }

    private static AllUsers createUsersFromString(String data) {
        AllUsers tempUsers;
        if (data == "NA") {
            tempUsers = new AllUsers();
        }
        else {
            tempUsers = new Gson().fromJson(data, AllUsers.class);
        }
        return tempUsers;
    }

    public void addToList(User user){
        if(allUser!=null) {
            if (getUserByID(user.getID()) == null)
                allUser.add(user);
        }
        else {
            allUser = new ArrayList<User>();
            allUser.add(user);
        }
    }

    public ArrayList getAllReportsData(){
        ArrayList<BloodDonation> all = new ArrayList<>();
        for (int i = 0; i < allUser.size(); i++) {
            all.addAll(allUser.get(i).getAllBloodDonations());
        }
        return all;
    }
    public User getUserByID(String id){
        for (User tempUser: allUser) {
            if(tempUser.getID().equals(id))
                return  tempUser;
        }
        return null;
    }

    public User getUserByUUID(String uuid){
        for (User tempUser: allUser) {
            if(tempUser.getuuID().equals(uuid))
                return  tempUser;
        }
        return null;
    }
}

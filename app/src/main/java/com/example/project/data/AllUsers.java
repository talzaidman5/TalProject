package com.example.project.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AllUsers {

    private List<User> allUser;



    public AllUsers(String data) {
        allUser =  createUsersFromString(data).allUser;
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

    /*public ArrayList getAllReportsData(){
        ArrayList<BloodDonation> all = new ArrayList<>();
        for (int i = 0; i < allUser.size(); i++) {
            all.addAll(allUser.get(i).getAllBloodDonations());
        }
        return all;
    }*/
    public User getUserByID(String id){
        for (User tempUser: allUser) {
            if(tempUser.getID()!=null)
            if(tempUser.getID().equals(id))
                return  tempUser;
        }
        return null;
    }

    public User getUserByUUID(String uuid){
        for (User tempUser: allUser) {
            if(tempUser.getUuID().equals(uuid))
                return  tempUser;
        }
        return null;
    } public User getUserByEmail(String email) throws Exception {

        for (User tempUser: allUser) {
            if (tempUser.getEmail() != null)
                if (Encryption.decrypt(tempUser.getEmail()).equals(email))
                    return tempUser;
        }
        return null;
    }


}

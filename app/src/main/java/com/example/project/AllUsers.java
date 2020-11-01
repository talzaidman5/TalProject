package com.example.project;

import com.example.project.User;

import java.util.ArrayList;
import java.util.List;

public class AllUsers {

    private List<User> allUser;


    public AllUsers(){
        allUser= new ArrayList<User>();
    }
    public AllUsers(List<User> allUser) {
        this.allUser = allUser;
    }

    public List<User> getAllUser() {
        return allUser;
    }

    public void setAllUser(List<User> allUser) {
        this.allUser = allUser;
    }

    public User checkUser(String ID) {
        for (User user:allUser) {
            if(user.getID().equals(ID))
                return user;
        }
        return null;
    }
    public void addToList(User user){
        if(allUser!=null) {
            if (checkUser(user.getID()) == null)
                allUser.add(user);
        }
        else {
            allUser = new ArrayList<User>();
            allUser.add(user);
        }
    }
}

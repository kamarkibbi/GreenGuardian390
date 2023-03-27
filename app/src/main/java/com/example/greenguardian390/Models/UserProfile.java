package com.example.greenguardian390.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class UserProfile implements Serializable{

    public String username,password,email;

    public ArrayList<Plant> userPlants;

    public UserProfile() {
        userPlants=new ArrayList<>();
    }

    public UserProfile(String u, String p, String e) {
        this.username=u;
        this.email=e;
        this.password=p;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Plant> getUserPlants()
    {
        return userPlants;
    }

    public void addPlant(Plant p)
    {
        userPlants.add(p);
    }

    public Plant findPlant(Plant p)
    {
        int index=userPlants.indexOf(p);

        if(index!=-1)
        {
            return userPlants.get(index);
        }

        return null;
    }

    public void deletePlant(Plant p)
    {
        int index=userPlants.indexOf(p);

        if(index!=-1)
        {
            userPlants.remove(index);
        }
    }
}

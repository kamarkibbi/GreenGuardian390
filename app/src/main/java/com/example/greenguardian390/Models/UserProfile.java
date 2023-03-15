package com.example.greenguardian390.Models;

import java.util.ArrayList;

public class UserProfile {

    String username,password,email;

    ArrayList<Plant> userPlants;

    public UserProfile() {

    }

    public UserProfile(String u, String p, String e) {
        this.username=u;
        this.email=e;
        this.password=p;
        userPlants=new ArrayList<>();
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

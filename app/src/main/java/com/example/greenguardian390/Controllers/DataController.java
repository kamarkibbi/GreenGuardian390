package com.example.greenguardian390.Controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataController {

    FirebaseDatabase db;
    DatabaseReference userProfileRef,currDataRef,histDataRef;

    DataController()
    {
        db = FirebaseDatabase.getInstance();
        userProfileRef = db.getReference().child("userProfile");
        currDataRef = db.getReference().child("sensorData");
    }
}

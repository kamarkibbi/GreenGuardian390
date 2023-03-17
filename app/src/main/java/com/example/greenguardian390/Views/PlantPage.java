package com.example.greenguardian390.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.greenguardian390.R;

public class PlantPage extends AppCompatActivity {

    TextView plantName,tempSensor,tempInput,soilSensor,soilInput;

    Button deleteButton,helpButton,editButton;

    DatabaseReference mDatabase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_page);

        plantName=findViewById(R.id.plantName);
        tempSensor=findViewById(R.id.tempSensor);
        tempInput=findViewById(R.id.tempInput);
        soilSensor=findViewById(R.id.soilSensor);
        soilInput=findViewById(R.id.soilInput);

        mDatabase = FirebaseDatabase.getInstance().getReference("TestData").child("17-03-2023").child("17:26");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d :snapshot.getChildren())
                {
                    if (d.getKey().toLowerCase().contains("moisture"))
                        soilSensor.setText(d.getValue()+"");
                    if(d.getKey().toLowerCase().contains("temperature"))
                        tempSensor.setText(d.getValue()+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
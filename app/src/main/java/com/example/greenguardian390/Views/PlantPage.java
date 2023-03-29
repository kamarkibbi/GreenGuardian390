package com.example.greenguardian390.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.greenguardian390.Models.Plant;
import com.example.greenguardian390.Models.UserProfile;
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

        mDatabase = FirebaseDatabase.getInstance().getReference("SenData");

        UserProfile currentuser=(UserProfile) getIntent().getSerializableExtra("CurrentUser");
        Plant selectedPlant = (Plant) getIntent().getSerializableExtra("plantClicked");


        plantName.setText(selectedPlant.getPlantName());
        tempInput.setText(String.valueOf(selectedPlant.getActualTemp()));
        soilInput.setText(String.valueOf(selectedPlant.getActualSoilMoisture()));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d :snapshot.getChildren())
                {
                    if (d.getKey().toLowerCase().contains("moisture"))
                    {
                        soilSensor.setText(d.getValue()+"");
                    }


                    if(d.getKey().toLowerCase().contains("temperature"))
                    {
                        tempSensor.setText(d.getValue()+"");
                        if((Long)d.getValue()>21)
                        {
                            Intent intent = new Intent(PlantPage.this, sensorChangeNotifications.class);
                            startService(intent);
                        }
                        //stopService(new Intent(PlantPage.this, sensorChangeNotifications.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
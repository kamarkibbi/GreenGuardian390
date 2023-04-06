package com.example.greenguardian390.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.greenguardian390.Models.Plant;
import com.example.greenguardian390.Models.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.greenguardian390.R;

import java.util.ArrayList;

public class PlantPage extends AppCompatActivity {

    TextView plantName,tempSensor,tempInput,soilSensor,soilInput;
    ImageButton backArrow;
    Button deleteButton,helpButton,editButton;

    UserProfile currentuser;
    Plant selectedPlant;



    DatabaseReference mDatabase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_page);


         backArrow=findViewById(R.id.backButton);
        plantName=findViewById(R.id.plantName);
        tempSensor=findViewById(R.id.tempSensor);
        tempInput=findViewById(R.id.tempInput);
        soilSensor=findViewById(R.id.soilSensor);
        soilInput=findViewById(R.id.soilInput);
        editButton=findViewById(R.id.editButton);
        deleteButton=findViewById(R.id.deleteButton);
        helpButton=findViewById(R.id.helpButton);

        mDatabase = FirebaseDatabase.getInstance().getReference("SenData");

        currentuser=(UserProfile) getIntent().getSerializableExtra("CurrentUser");
        selectedPlant = (Plant) getIntent().getSerializableExtra("plantClicked");



        plantName.setText(selectedPlant.getPlantName());
        tempInput.setText(String.valueOf(selectedPlant.getActualTemp()));
        soilInput.setText(String.valueOf(selectedPlant.getActualSoilMoisture()));

        //stopService(new Intent(this, sensorChangeNotifications.class));


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
                            //Intent intent = new Intent(PlantPage.this, sensorChangeNotifications.class);
                            //startService(intent);

                        //stopService(new Intent(PlantPage.this, sensorChangeNotifications.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PlantPage.this,AddPlantPage.class);
                intent.putExtra("CurrentUser",currentuser);
                intent.putExtra("plantClicked", selectedPlant);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlant();
                Intent intent=new Intent(PlantPage.this,MainPage.class);
                intent.putExtra("currentProfile",currentuser);
                startActivity(intent);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PlantPage.this,MainPage.class);
                intent.putExtra("currentProfile",currentuser);
                startActivity(intent);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PlantPage.this,helpPage.class);
                intent.putExtra("CurrentUser",currentuser);
                intent.putExtra("plantClicked", selectedPlant);
                startActivity(intent);
            }
        });
    }

    public void deletePlant()
    {
        ArrayList<Plant> currentUserPlants=currentuser.getUserPlants();
        int indexOfPlant=0;
        for (int i=0; i< currentUserPlants.size(); i++)
        {
            if (currentUserPlants.get(i).getPlantName().equals(selectedPlant.getPlantName()))
            {
                indexOfPlant=i;
                break;
            }
        }

        currentUserPlants.remove(indexOfPlant);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        mDatabase.child("userProfile").child(currentuser.getUsername()).child("userPlants").setValue(currentUserPlants);
    }
}
package com.example.greenguardian390.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.greenguardian390.Models.Plant;
import com.example.greenguardian390.Models.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.greenguardian390.R;

import java.util.ArrayList;

public class AddPlantPage extends AppCompatActivity {
    ImageView selectedImage;
    Button CameraButton, GalleryButton;

    DatabaseReference mDatabase;

    private EditText name,temperature,moisture;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_page);

        selectedImage = findViewById(R.id.ImageView);
        CameraButton = findViewById(R.id.CameraButton);
        GalleryButton = findViewById(R.id.GalleryButton);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        save = findViewById(R.id.Savebutton);
        temperature = findViewById(R.id.AddTemperatureEditText);
        name = findViewById(R.id.AddNameEditText);
        moisture = findViewById(R.id.AddSoilEditText);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPlantToProfile();
                Intent intent = new Intent(AddPlantPage.this, MainPage.class);


                //intent= getIntent();




                /*intent.putExtra("namePlant",n);
                intent.putExtra("temperatureLevel",t);
                intent.putExtra("moistureLevel",m);*/

                startActivity(intent);

            }
        });


        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askCameraPermission();
                //Toast.makeText(AddPlantPage.this, "Camera button is clicked", Toast.LENGTH_SHORT).show();

            }
        });

        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddPlantPage.this, "Gallery button is clicked", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void addPlantToProfile()
    {
        String n = name.getText().toString().trim();
        String t = temperature.getText().toString().trim();
        String m = moisture.getText().toString().trim();

        Plant plantCreated=new Plant(Float.parseFloat(m),Float.parseFloat(t),n);
        //selectedImage

        UserProfile currentuser=(UserProfile) getIntent().getSerializableExtra("CurrentUser");

        System.out.println(currentuser.getUsername());

        ArrayList<Plant> plantList=currentuser.getUserPlants();

        plantList.add(plantCreated);

        mDatabase.child("userProfile/"+currentuser.getUsername()).setValue(currentuser);


    }

    private void askCameraPermission() {   //CHECKS IF PERMISSION IS GRANTED FROM USER OR NOT
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 111);
        } else {
            openCamera();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission is required to use the camera ", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 112);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 112) {
            Bitmap image  = (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(image);

        }


    }
}

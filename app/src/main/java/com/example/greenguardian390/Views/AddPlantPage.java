package com.example.greenguardian390.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.greenguardian390.Models.Plant;
import com.example.greenguardian390.Models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.greenguardian390.R;

import java.util.ArrayList;

public class AddPlantPage extends AppCompatActivity {


    DatabaseReference mDatabase;

    private EditText name,temperature,moisture;
    private Button save;

    private Button cancel;

    private UserProfile currentuser;

    private Plant selectedPlant;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_page);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        save = findViewById(R.id.Savebutton);
        save.setOnClickListener(this::onClick);
        cancel=findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this::onClick);
        temperature = findViewById(R.id.AddTemperatureEditText);
        name = findViewById(R.id.AddNameEditText);
        moisture = findViewById(R.id.AddSoilEditText);
        progressBar=findViewById(R.id.progressBarRegister);

        selectedPlant = (Plant) getIntent().getSerializableExtra("plantClicked");
        if(selectedPlant!=null)
        {
            name.setText(selectedPlant.getPlantName());
            temperature.setText(String.valueOf(selectedPlant.getActualTemp()));
            moisture.setText(String.valueOf(selectedPlant.getActualSoilMoisture()));
        }

        /*selectedImage = findViewById(R.id.ImageView);
        CameraButton = findViewById(R.id.CameraButton);
        GalleryButton = findViewById(R.id.GalleryButton);*/



//        System.out.println(selectedPlant.getPlantName());
   //     System.out.println(selectedPlant.getActualSoilMoisture());
   //     System.out.println(selectedPlant.getActualTemp());

      //  System.out.println("I am in addplantpage coming from plant page");






    }

    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.Savebutton:
                selectedPlant = (Plant) getIntent().getSerializableExtra("plantClicked");
                if(selectedPlant!=null)
                {
                    editPlant();
                }
                else
                {
                    addPlantToProfile();
                }
                break;
        }

        switch (view.getId()){
            case R.id.cancel_button:
                Intent intent = new Intent(AddPlantPage.this, MainPage.class);
                UserProfile currentuser=(UserProfile) getIntent().getSerializableExtra("CurrentUser");
                intent.putExtra("currentProfile", currentuser);
                startActivity(intent);
                break;
        }
    }

    public void editPlant()
    {
        currentuser=(UserProfile) getIntent().getSerializableExtra("CurrentUser");
        selectedPlant = (Plant) getIntent().getSerializableExtra("plantClicked");

        if(selectedPlant !=null)
        {
            Plant newPlant =new Plant();
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

            newPlant.setPlantName(name.getText().toString());
            newPlant.setActualSoilMoisture(Float.parseFloat(moisture.getText().toString()));
            newPlant.setActualTemp(Float.parseFloat(temperature.getText().toString()));

            currentUserPlants.add(indexOfPlant,newPlant);

            mDatabase=FirebaseDatabase.getInstance().getReference();
            mDatabase.child("userProfile/"+currentuser.getUsername()+"/userPlants").setValue(currentUserPlants);


        }
    }

    public void addPlantToProfile()
    {
        String n = name.getText().toString().trim();
        String t = temperature.getText().toString().trim();
        String m = moisture.getText().toString().trim();

        if(n.isEmpty()){
            name.setError("Plant name is required!");
            name.requestFocus();
            return;
        }
        if(t.isEmpty()){
            temperature.setError("Plant temperature is required!");
            temperature.requestFocus();
            return;
        }
        if(m.isEmpty()){
            moisture.setError("Plant soil moisture level is required!");
            moisture.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        Plant plantCreated=new Plant(Float.parseFloat(m),Float.parseFloat(t),n);
            //selectedImage

        UserProfile currentuser=(UserProfile) getIntent().getSerializableExtra("CurrentUser");

        System.out.println(currentuser.getUsername());

        ArrayList<Plant> plantList=currentuser.getUserPlants();

        plantList.add(plantCreated);

        mDatabase.child("userProfile/"+currentuser.getUsername()).setValue(currentuser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(AddPlantPage.this,"New plant has been added successfully",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(AddPlantPage.this, MainPage.class);
                    UserProfile currentuser=(UserProfile) getIntent().getSerializableExtra("CurrentUser");
                    intent.putExtra("currentProfile", currentuser);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddPlantPage.this,"New plant could not be added! Please try again.",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });











    }

    /*
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


    }*/
}

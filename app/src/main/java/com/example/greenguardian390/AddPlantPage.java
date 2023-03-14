package com.example.greenguardian390;

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

public class AddPlantPage extends AppCompatActivity {
    ImageView selectedImage;
    Button CameraButton, GalleryButton;

    private EditText name,temperature,moisture;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_page);

        selectedImage = findViewById(R.id.ImageView);
        CameraButton = findViewById(R.id.CameraButton);
        GalleryButton = findViewById(R.id.GalleryButton);

        save = findViewById(R.id.Savebutton);
        temperature = findViewById(R.id.AddTemperatureEditText);
        name = findViewById(R.id.AddNameEditText);
        moisture = findViewById(R.id.AddSoilEditText);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n = name.getText().toString();
                String t = temperature.getText().toString();
                String m = moisture.getText().toString();

                Intent intent = new Intent(AddPlantPage.this,MainPage.class);

                intent.putExtra("namePlant",n);
                intent.putExtra("temperatureLevel",t);
                intent.putExtra("moistureLevel",m);

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

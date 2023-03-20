package com.example.greenguardian390.Models;

import android.media.Image;
import android.widget.ImageView;

import java.io.Serializable;

public class Plant implements Serializable {

    public float actualSoilMoisture,actualTemp;

    public String plantName;

    public ImageView plantImage;

    public Plant()
    {

    }

    public Plant(float s, float t, String n, ImageView i) {
        this.actualSoilMoisture=s;
        this.actualTemp=t;
        this.plantName=n;
        this.plantImage=i;
    }

    public float getActualSoilMoisture() {
        return actualSoilMoisture;
    }

    public void setActualSoilMoisture(float actualSoilMoisture) {
        this.actualSoilMoisture = actualSoilMoisture;
    }

    public float getActualTemp() {
        return actualTemp;
    }

    public void setActualTemp(float actualTemp) {
        this.actualTemp = actualTemp;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public ImageView getPlantImage() {
        return plantImage;
    }

    public void setPlantImage(ImageView plantImage) {
        this.plantImage = plantImage;
    }
}

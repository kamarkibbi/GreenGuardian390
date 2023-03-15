package com.example.greenguardian390.Models;

import android.media.Image;

public class Plant {

    public float actualSoilMoisture,actualTemp;

    public String plantName;

    public Image plantImage;

    public Plant(float s, float t, String n, Image i) {
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

    public Image getPlantImage() {
        return plantImage;
    }

    public void setPlantImage(Image plantImage) {
        this.plantImage = plantImage;
    }
}

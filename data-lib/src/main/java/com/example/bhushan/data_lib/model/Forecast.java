package com.example.bhushan.data_lib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Forecast implements Serializable{

    @SerializedName("precipitation")
    @Expose
    private Precipitation precipitation;

    @SerializedName("temperature")
    @Expose
    private Temperature temperature;

    public Forecast(Precipitation precipitation, Temperature temperature) {
        this.precipitation = precipitation;
        this.temperature = temperature;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Precipitation precipitation) {
        this.precipitation = precipitation;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }
}

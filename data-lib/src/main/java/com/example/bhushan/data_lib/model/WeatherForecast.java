package com.example.bhushan.data_lib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast implements Serializable {

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("forecast")
    @Expose
    private List<Forecast> forecast = new ArrayList<Forecast>();

    @SerializedName("cnt")
    @Expose
    private Integer cnt;

    public WeatherForecast(Location location, List<Forecast> forecast, Integer cnt) {
        this.location = location;
        this.forecast = forecast;
        this.cnt = cnt;
    }

    public WeatherForecast() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}

package com.example.bhushan.data_lib.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class WeatherOverall implements Serializable {
    @Expose
    private CurrentWeather mCurrentWeather;

    @Expose
    private WeatherForecast mWeatherForecast;

    @Expose
    private long dt;

    public WeatherOverall(CurrentWeather mCurrentWeather, WeatherForecast mWeatherForecast) {
        this.mCurrentWeather = mCurrentWeather;
        this.mWeatherForecast = mWeatherForecast;
    }

    public CurrentWeather getmCurrentWeather() {
        return mCurrentWeather;
    }

    public void setmCurrentWeather(CurrentWeather mCurrentWeather) {
        this.mCurrentWeather = mCurrentWeather;
    }

    public WeatherForecast getmWeatherForecast() {
        return mWeatherForecast;
    }

    public void setmWeatherForecast(WeatherForecast mWeatherForecast) {
        this.mWeatherForecast = mWeatherForecast;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }
}

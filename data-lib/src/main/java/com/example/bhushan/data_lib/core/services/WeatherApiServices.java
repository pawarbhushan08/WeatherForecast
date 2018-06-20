package com.example.bhushan.data_lib.core.services;

import com.example.bhushan.data_lib.model.WeatherOverall;

import rx.Observable;

public interface WeatherApiServices {
    abstract Observable<WeatherOverall> loadWeather(String city);
}

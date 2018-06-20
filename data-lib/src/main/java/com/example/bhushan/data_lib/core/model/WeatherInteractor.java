package com.example.bhushan.data_lib.core.model;

import com.example.bhushan.data_lib.model.WeatherOverall;

import rx.Observable;

public interface WeatherInteractor {
    Observable<WeatherOverall> loadWeather(String city);

    void clearMemoryAndDiskCache();

    void clearMemoryCache();
}

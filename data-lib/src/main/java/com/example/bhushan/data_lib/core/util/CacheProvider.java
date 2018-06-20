package com.example.bhushan.data_lib.core.util;

import com.example.bhushan.data_lib.model.WeatherOverall;

import rx.Observable;

public interface CacheProvider {
    Observable<WeatherOverall> getWeather();

    Observable<Boolean> saveWeather(WeatherOverall value);

    void clear();
}

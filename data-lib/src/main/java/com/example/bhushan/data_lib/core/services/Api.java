package com.example.bhushan.data_lib.core.services;

import com.example.bhushan.data_lib.model.CurrentWeather;
import com.example.bhushan.data_lib.model.WeatherForecast;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    // http://api.openweathermap.org/data/2.5/weather?q=Tehran&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("weather")
    Observable<CurrentWeather> getWeather(@Query("q") String city, @Query("APPID") String apiKey, @Query("units") String units);


    // http://api.openweathermap.org/data/2.5/forecast?q=Tehran&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("forecast/daily")
    Observable<WeatherForecast> getWeatherForecast(@Query("q") String city, @Query("APPID") String apiKey, @Query("units") String units, @Query("cnt") int count);
}

package com.example.bhushan.data_lib.core.services;

import com.example.bhushan.data_lib.core.util.Constants;
import com.example.bhushan.data_lib.model.WeatherOverall;

import rx.Observable;

public class WeatherApiServiceImpl implements WeatherApiServices {
    private final Api api;

    public WeatherApiServiceImpl(Api api) {
        this.api = api;
    }

    @Override
    public Observable<WeatherOverall> loadWeather(String city) {
        return Observable
                .combineLatest(api.getWeather(city,Constants.API_KEY,Constants.WEATHER_UNITS),
                        api.getWeatherForecast(city, Constants.API_KEY, Constants.WEATHER_UNITS, Constants.FORECAST_DAY_COUNT),
                        WeatherOverall::new);
    }
}

package com.example.bhushan.data_lib.core.presenters;

import com.example.bhushan.data_lib.core.view.MainView;

public interface WeatherPresenters {
    void setView(MainView view);

    void loadWeather(String city, boolean isConnected);
}

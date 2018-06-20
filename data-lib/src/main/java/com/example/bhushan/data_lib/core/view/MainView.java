package com.example.bhushan.data_lib.core.view;

import com.example.bhushan.data_lib.model.WeatherOverall;

public interface MainView {
    void showProgress();

    void hideProgress();

    void showToastMessage(String message);

    void updateProgressMessage(String newMessage);

    void showConnectionError();

    void showRetryMessage();

    void setWeatherValues(WeatherOverall weatherOverall);
}

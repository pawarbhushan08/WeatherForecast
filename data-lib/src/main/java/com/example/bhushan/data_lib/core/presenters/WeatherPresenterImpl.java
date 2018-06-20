package com.example.bhushan.data_lib.core.presenters;

import com.example.bhushan.data_lib.core.model.WeatherInteractor;
import com.example.bhushan.data_lib.core.view.MainView;

import java.util.NoSuchElementException;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class WeatherPresenterImpl implements WeatherPresenters {

    WeatherInteractor interactor;
    private MainView view;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public WeatherPresenterImpl(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
    }


    @Override
    public void loadWeather(String city, boolean isConnected) {
        if (view != null) {
            view.showProgress();
            view.updateProgressMessage("Loading City Weather...");
        }

        subscription = interactor.loadWeather(city).subscribe(
                weatherOverall -> {
                    if (view != null) {
                        view.setWeatherValues(weatherOverall);
                    }
                },
                throwable -> {
                    if (isConnected) {
                        if (view != null) {
                            if (throwable.getClass().equals(NoSuchElementException.class)) {
                                view.showToastMessage("City not found!!!");
                            } else {
//                                view.showToastMessage(throwable.getMessage());
                                view.showRetryMessage();
                            }
                        }
                    } else {
                        if (view != null) {
                            view.showConnectionError();
                        }
                    }

                    if (view != null) {
                        view.hideProgress();
                    }
                },
                () -> {
                    if (view != null) {
                        view.hideProgress();
                    }
                });
    }
}

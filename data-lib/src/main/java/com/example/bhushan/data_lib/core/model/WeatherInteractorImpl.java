package com.example.bhushan.data_lib.core.model;

import com.example.bhushan.data_lib.core.services.WeatherApiServices;
import com.example.bhushan.data_lib.core.util.CacheProvider;
import com.example.bhushan.data_lib.core.util.Constants;
import com.example.bhushan.data_lib.core.util.SchedulerProvider;
import com.example.bhushan.data_lib.model.WeatherOverall;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;

public class WeatherInteractorImpl implements WeatherInteractor{

    private CacheProvider diskCache;
    private WeatherApiServices weatherApiService;
    private SchedulerProvider scheduler;
    private Clock clock;

    private WeatherOverall memoryCacheWeather;

    private ReplaySubject<WeatherOverall> weatherSubject;
    private Subscription weatherSubscription;
    
    public WeatherInteractorImpl(CacheProvider diskCache, WeatherApiServices weatherApiService, SchedulerProvider scheduler, Clock clock) {
        this.diskCache = diskCache;
        this.weatherApiService = weatherApiService;
        this.scheduler = scheduler;
        this.clock = clock;
    }

    @Override
    public Observable<WeatherOverall> loadWeather(String city) {
        if (weatherSubscription == null || weatherSubscription.isUnsubscribed()) {
            weatherSubject = ReplaySubject.create();

            weatherSubscription = Observable.concat(memoryWeather(), diskWeather(), networkWeather(city))
                    .first(entity -> entity != null && isSameCity(city, entity) && isUpToDate(entity))
                    .subscribe(weatherSubject);
        }

        return weatherSubject.asObservable();
    }
    private Observable<WeatherOverall> networkWeather(String city) {
        return weatherApiService.loadWeather(city)
                .doOnNext(entity -> {
                    entity.setDt(clock.millis());
                    memoryCacheWeather = entity;
                })
                .flatMap(entity -> diskCache.saveWeather(entity).map(__ -> entity))
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }
    private Observable<WeatherOverall> diskWeather() {
        return diskCache.getWeather()
                .doOnNext(entity -> memoryCacheWeather = entity)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }
    private Observable<WeatherOverall> memoryWeather() {
        return Observable.just(memoryCacheWeather);
    }

    @Override
    public void clearMemoryAndDiskCache() {
        diskCache.clear();
        clearMemoryCache();
    }

    @Override
    public void clearMemoryCache() {
        memoryCacheWeather = null;
    }

    private boolean isUpToDate(WeatherOverall entity) {
        return clock.millis() - entity.getDt() < Constants.STALE_MS;
    }

    private boolean isSameCity(String city, WeatherOverall entity) {
        return entity.getmCurrentWeather().getName().equalsIgnoreCase(city);
    }

}

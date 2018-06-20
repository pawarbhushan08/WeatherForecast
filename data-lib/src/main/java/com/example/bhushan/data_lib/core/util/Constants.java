package com.example.bhushan.data_lib.core.util;

public class Constants {
    // all Constant values are here
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "0a9c8b845f566dd97bf703fcc664778b";
    public static final String WEATHER_UNITS = "metric";
    public static final int FORECAST_DAY_COUNT = 5;
    //sharePreferences Keys
    public static final String KEY_LAST_CITY = "last_city";
    // default city for first run
    public static final String CITY_DEFAULT_VALUE = "Cologne";
    //cache validation time
    public static final long STALE_MS = 60 * 1000;
}

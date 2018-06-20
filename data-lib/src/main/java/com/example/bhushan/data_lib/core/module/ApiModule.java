package com.example.bhushan.data_lib.core.module;

import com.example.bhushan.data_lib.core.services.Api;
import com.example.bhushan.data_lib.core.services.WeatherApiServiceImpl;
import com.example.bhushan.data_lib.core.services.WeatherApiServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.BaseUrl;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public WeatherApiServices provideWeatherApiService(Retrofit retrofit) {
        return new WeatherApiServiceImpl(retrofit.create(Api.class));
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(BaseUrl baseUrl, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
    }
}

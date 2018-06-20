package com.example.bhushan.data_lib.core.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {
    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}

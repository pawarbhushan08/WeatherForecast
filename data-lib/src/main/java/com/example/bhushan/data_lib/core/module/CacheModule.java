package com.example.bhushan.data_lib.core.module;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;

@Module
public class CacheModule {

    @Provides
    @Singleton
    public Cache provideCache(@Named("cacheDir") File cacheDir, @Named("cacheSize") long cacheSize) {
        Cache cache = null;

        try {
            cache = new Cache(new File(cacheDir.getPath(), "http-cache"), cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cache;
    }
}
package com.example.bhushan.data_lib.core.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

@Module
public class HttpClientModule {
    @Module
    public class CallAdapterModule {
        @Provides
        @Singleton
        public CallAdapter.Factory provideRxJavaCallAdapterFactory() {
            return RxJavaCallAdapterFactory.create();
        }
    }
}

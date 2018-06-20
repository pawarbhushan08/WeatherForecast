package com.example.bhushan.data_lib.core.model;

public interface Clock {
    Clock REAL = System::currentTimeMillis;

    long millis();
}

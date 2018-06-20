package com.example.bhushan.data_lib.core.util;

import rx.Scheduler;

public interface SchedulerProvider {
    Scheduler mainThread();

    Scheduler backgroundThread();
}

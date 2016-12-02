package com.kozhurov.project294.core;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class ProjectApplication extends Application {

    private Executor executor;
    private DeviceHelper deviceHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newCachedThreadPool();
        deviceHelper = new DeviceHelper(this, executor);
    }

    public DeviceHelper getDeviceHelper() {
        return deviceHelper;
    }
}

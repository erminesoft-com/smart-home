package com.kozhurov.core;

import com.kozhurov.controller.ControlsManager;
import com.kozhurov.store.H2Store;
import com.kozhurov.store.StoreBridge;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Base {

    private final Executor executor;
    private final ControlsManager controlsManager;
    private final StoreBridge storeBridge;

    public Base() {
        System.out.println("Base started");

        executor = Executors.newFixedThreadPool(2);

        controlsManager = new ControlsManager(executor);
        storeBridge = new H2Store();
    }
}

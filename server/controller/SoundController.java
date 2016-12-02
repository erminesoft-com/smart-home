package com.kozhurov.controller;

import com.kozhurov.gate.SoundGate;

class SoundController {
    private final SoundGate soundGate;

    public SoundController() {

        soundGate = new SoundGate();
        soundGate.stageOne();
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(SoundGate.RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                soundGate.finish();
            }
        });

        stopper.start();
    }
}

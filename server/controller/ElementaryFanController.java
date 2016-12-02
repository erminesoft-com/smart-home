package com.kozhurov.controller;

import com.kozhurov.gate.GateManager;
import com.kozhurov.gate.GpioGate;
import com.kozhurov.model.GeneralModel;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

class ElementaryFanController {

    private static final Pin ELEMENTARY_FAN_MAIN = RaspiPin.GPIO_06;
    private static final int FAN_ON = 0;
    private static final int FAN_OFF = 1;

    void setNewFanState(GateManager gateManager, GeneralModel newFanState) {
        switch (newFanState.getState()) {
            case FAN_ON:
                turnOnFan(gateManager, ELEMENTARY_FAN_MAIN);
                break;

            case FAN_OFF:
                turnOffFan(gateManager, ELEMENTARY_FAN_MAIN);
                break;
        }

    }

    private void turnOffFan(GateManager gateManager, Pin pin) {
        GpioGate gpioGate = gateManager.getGpioGate();
        gpioGate.switchPortState(pin, PinState.LOW);
    }

    private void turnOnFan(GateManager gateManager, Pin pin) {
        GpioGate gpioGate = gateManager.getGpioGate();
        gpioGate.switchPortState(pin, PinState.HIGH);
    }
}

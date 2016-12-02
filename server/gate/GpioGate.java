package com.kozhurov.gate;

import com.pi4j.io.gpio.*;

public final class GpioGate {

    private final GpioController gpioController;
    private GpioPinDigitalOutput output;

    GpioGate() {
        System.out.println("GPIO gate started");
        gpioController = GpioFactory.getInstance();
    }

    public void switchPortState(Pin pin, PinState pinState) {

        if (output == null) {
            System.out.println("GPIO gate. Init pin");
            output = gpioController.provisionDigitalOutputPin(pin);
        }

        System.out.println("GPIO gate. Exist pin");
        output.setState(pinState);

    }
}

package com.kozhurov.gate;

import com.kozhurov.gate.socket.SocketGate;

import java.util.concurrent.Executor;

public class GateManager {

    private final IrdaGate irdaGate;
    private final SocketGate socketGate;
    private final GpioGate gpioGate;

    public GateManager(Executor executor, MessageListener messageListener) {
        System.out.println("Gate manager start");

        gpioGate = new GpioGate();
        irdaGate = new IrdaGate();

        socketGate = new SocketGate(messageListener);
        executor.execute(socketGate);

        System.out.println("Gate manager started");
    }

    public IrdaGate getIrdaGate() {
        return irdaGate;
    }

    public SocketGate getSocketGate() {
        return socketGate;
    }

    public GpioGate getGpioGate() {
        return gpioGate;
    }
}

package com.kozhurov.gate.socket;

import com.kozhurov.gate.MessageListener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public final class SocketGate implements Runnable{

    private static final int PORT_NUMBER = 7878;

    private final StateListener stateListener;
    private final MessageListener messageListener;

    private ServerSocket serverSocket;
    private ConcurrentHashMap<String, Connection> mConnections;

    private boolean mRunning = false;

    public SocketGate(MessageListener messageListener) {

        this.messageListener = messageListener;
        stateListener = new ConnectionStateListener();

        mConnections = new ConcurrentHashMap<>();

        System.out.println("Socket gate started");
    }

    @Override
    public void run() {
        mRunning = true;
        waitConnection();
    }

    private void waitConnection() {
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            while (mRunning) {
                Socket client = serverSocket.accept();
                client.setOOBInline(true);

                Connection connection = new Connection(client, stateListener, messageListener);
                mConnections.put(connection.getSocketName(), connection);
                connection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeConnection(String name) {
        mConnections.remove(name);
    }

    public void closeGate() {
        mRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private final class ConnectionStateListener implements StateListener {

        @Override
        public void onRemove(String nameOfConnection) {
            System.out.println("remove connection");
            removeConnection(nameOfConnection);
        }
    }
}

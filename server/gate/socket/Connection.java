package com.kozhurov.gate.socket;

import com.kozhurov.gate.MessageListener;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Time;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public final class Connection extends Thread {

    private static final String PING = "ping";
    private static final String PONG = "pong";

    private final String name;
    private final Socket socket;
    private final StateListener listener;
    private final MessageListener messageListener;

    private PrintWriter mOut;
    private BufferedReader in;

    private boolean isRun = false;
    private Timer pingTimer;
    private Timer stopTimer;
    private Queue<String> pongAnswers;

    public Connection(Socket pSocket, StateListener pListener, MessageListener messageListener) {
        System.out.println("Start connection!");
        pongAnswers = new ArrayDeque<>();

        name = pSocket.getInetAddress().getHostAddress();
        socket = pSocket;

        listener = pListener;
        this.messageListener = messageListener;

        initSocketWatchers();
        initPingTimer();
        initStopTimer();
    }

    private void initSocketWatchers() {
        try {
            mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRun = true;
        readMessage();
        close();
    }

    private void readMessage() {
        String serverMessage;
        while (isRun) {
            try {
                serverMessage = in.readLine();
                if (serverMessage == null || serverMessage.isEmpty() || listener == null) {
                    continue;
                }

                if (serverMessage.equals(PONG)) {
                    System.out.println("Pong income!");
                    pongAnswers.add(PONG);
                    continue;
                }

                System.out.println("Transport = ");
                messageListener.onMessageReceive(serverMessage);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getSocketName() {
        return name;
    }

    public void sendMessage(String pMessage) {
        if (mOut != null && !mOut.checkError()) {
            mOut.println(pMessage);
            mOut.flush();
        } else {
            close();

        }
    }

    public void close() {
        System.out.println("Close connection");
        isRun = false;
        pingTimer.cancel();
        stopTimer.cancel();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener.onRemove(name);
    }

    private void initStopTimer() {
        stopTimer = new Timer();
        stopTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (pongAnswers.isEmpty()) {
                    System.out.println("no PONG!");
                    close();
                }
                pongAnswers.clear();
            }
        }, 15000, 15000);
    }

    private void initPingTimer() {
        pingTimer = new Timer();
        pingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Send ping!");
                sendMessage(PING);
            }
        }, 5000, 5000);
    }

}

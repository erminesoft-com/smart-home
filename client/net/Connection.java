package com.kozhurov.project294.net;

import android.util.Log;

import com.google.gson.Gson;
import com.kozhurov.project294.model.GeneralTransport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

final class Connection implements Runnable {

    private static final String PING = "ping";
    private static final String PONG = "pong";
    private static final String DELIVERY_STATE = "delivery_state";

    private final String serverUrl;
    private final int serverPort;

    private final StateListener stateListener;

    private PrintWriter out;
    private BufferedReader in;

    private Socket socket;
    private boolean isRun = false;
    private boolean isConnected = false;

    private Timer stopTimer;
    private Queue<String> pingAnswers;


    Connection(StateListener pSocketListener, String serverUrl, int serverPort) {
        this.serverUrl = serverUrl;
        this.serverPort = serverPort;

        pingAnswers = new ArrayDeque<>();
        stateListener = pSocketListener;
    }

    public void run() {
        isRun = true;
        try {
            initStopTimer();
            openSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (!isRun) {
                closeSocket();
            }
        }
    }

    private void openSocket() throws IOException {
        socket = new Socket(serverUrl, serverPort);
        socket.setOOBInline(true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        readSocket();
    }

    private void readSocket() throws IOException {
        String serverMessage;
        while (isRun) {
            serverMessage = in.readLine();

            if (serverMessage == null) {
                continue;
            }

            if (serverMessage.equals(PING)) {
                pingAnswers.add(PING);
                sendMessage(PONG);
                continue;
            }

            stateListener.onNewMessage(serverMessage);
        }
    }

    private void initStopTimer() {
        stopTimer = new Timer();
        stopTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (pingAnswers.isEmpty()) {
                    Log.w("Connection", "no ping. Connection was close");
                    closeSocket();
                    return;
                }

                pingAnswers.clear();

                isConnected = true;
                stateListener.onConnected();

            }
        }, 15000, 15000);
    }


    boolean sendMessage(String data) {
        if (socket == null) {
            return false;
        }

        if (socket.isClosed()) {
            stateListener.onReconnect(data);
            return false;
        }

        if (out != null && !out.checkError()) {
            out.println(data);
            out.flush();
            return true;
        } else {
            closeSocket();
        }
        return false;
    }

    void closeSocket() {
        isRun = false;
        isConnected = false;

        stateListener.onDisconnect();

        if (stopTimer != null) {
            stopTimer.cancel();
        }

        try {
            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    boolean isConnectionAlive() {
        return isConnected;
    }
}

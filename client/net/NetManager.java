package com.kozhurov.project294.net;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.kozhurov.project294.core.SharedHelper;
import com.kozhurov.project294.db.DbManager;
import com.kozhurov.project294.model.GeneralModel;
import com.kozhurov.project294.model.GeneralTransport;
import com.kozhurov.project294.util.ConnectivityUtils;

import java.util.concurrent.Executor;

public class NetManager {

    private final int PORT = 7878;
    private final Executor service;
    private final DbManager dbManager;
    private final LocalBroadcastManager broadcastManager;
    private final Context context;
    private Connection connection;
    private String serverUrl;

    public NetManager(Executor executor, SharedHelper sharedHelper, DbManager dbManager, Context context) {
        service = executor;
        broadcastManager = LocalBroadcastManager.getInstance(context);
        this.context = context;
        this.dbManager = dbManager;

        serverUrl = sharedHelper.getServerUrl();
        connectToServer();
    }

    public void connectToServer() {
        if (connection != null) {
            connection.closeSocket();
            connection = null;
        }
        connection = new Connection(new SocketListener(), serverUrl, PORT);
        service.execute(connection);
    }

    public void sendMessage(final GeneralModel model) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                if (connection != null) {
                    GeneralTransport transport = new GeneralTransport();
                    transport.setElementaryFanModel(model);
                    connection.sendMessage(transport.convertSelfToJson());
                }
            }
        });
    }

    public boolean isConnected() {
        return connection != null && connection.isConnectionAlive();
    }

    private void processIncomeTransport(GeneralTransport generalTransport) {
        if (generalTransport.getTimestamp() > 0) {
            dbManager.unpackWaitAnswer(generalTransport);
        }
    }

    private final class SocketListener implements StateListener {

        @Override
        public void onReconnect(String unsentData) {
            onReconnect(unsentData);
            if (connection != null) {
                connection.sendMessage(unsentData);
            }
        }

        @Override
        public void onConnected() {
            Log.wtf("NM", "onConnected");
            Intent intent = ConnectivityUtils.createServerConnectionIntent(true);
            broadcastManager.sendBroadcast(intent);
            //WidgetUtils.sendUpdateBroadcast(context, true);
        }

        @Override
        public void onDisconnect() {
            Log.wtf("NM", "onDisconnected");
            Intent intent = ConnectivityUtils.createServerConnectionIntent(false);
            broadcastManager.sendBroadcast(intent);
            //WidgetUtils.sendUpdateBroadcast(context, false);
        }

        @Override
        public void onNewMessage(String serverMessage) {
            GeneralTransport transport = new Gson().fromJson(serverMessage, GeneralTransport.class);
            processIncomeTransport(transport);
        }
    }
}
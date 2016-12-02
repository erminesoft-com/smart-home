package com.kozhurov.project294.net;

import com.kozhurov.project294.model.GeneralModel;

interface StateListener {

    void onReconnect(String unsentData);
    void onConnected();
    void onDisconnect();
    void onNewMessage(String serverMessage);

}

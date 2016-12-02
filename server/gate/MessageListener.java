package com.kozhurov.gate;

public interface MessageListener {

    void onMessageReceive(String serializedData);
}

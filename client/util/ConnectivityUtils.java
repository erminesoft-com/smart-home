package com.kozhurov.project294.util;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public final class ConnectivityUtils {

    private static final String FILTER_KEY = "connectivity_broadcast_action";
    private static final String STATE_KEY = "state_key";
    private static final int STATE_CONNECTED = 1;
    private static final int STATE_DISCONNECTED = 0;

    public static Intent createServerConnectionIntent(boolean isConnected) {
        Intent intent = new Intent(FILTER_KEY);
        Bundle bundle = new Bundle();
        bundle.putInt(STATE_KEY, isConnected ? STATE_CONNECTED : STATE_DISCONNECTED);
        intent.putExtras(bundle);
        return intent;
    }

    public static IntentFilter buildIntentFilter() {
        return new IntentFilter(FILTER_KEY);
    }

    public static boolean isConnected(Intent intent) {
        int state = intent.getExtras().getInt(STATE_KEY, -1);
        return state > 0;
    }
}

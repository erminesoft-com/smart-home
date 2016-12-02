package com.kozhurov.project294.core;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;

public final class SharedHelper {

    private static final String NAME = "Project_294";
    private static final String IS_FIRST_START = "is_first_start";
    private static final String SERVER_URL = "server_url";
    private static final String OFFLINE_MODE = "offline_mode";
    private static final String VERIFY_PACKAGE = "verify_package";
    private static final String WIDGET_ID = "WIDGET_ID";

    private final SharedPreferences mSharedPreferences;

    public SharedHelper(Context pContext) {
        mSharedPreferences = pContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setIsFirstStart(boolean isFirstStart) {
        mSharedPreferences.edit().putBoolean(IS_FIRST_START, isFirstStart).apply();
    }

    public boolean isFirstStart() {
        return mSharedPreferences.getBoolean(IS_FIRST_START, true);
    }

    public String getServerUrl() {
        return mSharedPreferences.getString(SERVER_URL, "192.168.0.10");
    }

    public void setServerUrl(String serverUrl) {
        mSharedPreferences.edit().putString(SERVER_URL, serverUrl).apply();
    }

    public boolean isOfflineMode() {
        return mSharedPreferences.getBoolean(OFFLINE_MODE, false);
    }

    public void setOfflineMode(boolean isOfflineMode) {
        mSharedPreferences.edit().putBoolean(OFFLINE_MODE, isOfflineMode).apply();
    }

    public boolean isVerifyPackage() {
        return mSharedPreferences.getBoolean(VERIFY_PACKAGE, false);
    }

    public void setVerifyPackage(boolean isVerifyPackage) {
        mSharedPreferences.edit().putBoolean(VERIFY_PACKAGE, isVerifyPackage).apply();
    }

    public int getWidgetId() {
        return mSharedPreferences.getInt(WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void setWidgetId(int widgetId) {
        mSharedPreferences.edit().putInt(WIDGET_ID, widgetId).apply();
    }
}

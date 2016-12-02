package com.kozhurov.project294.util;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.kozhurov.project294.R;
import com.kozhurov.project294.core.ProjectApplication;
import com.kozhurov.project294.model.GeneralModel;
import com.kozhurov.project294.ui.widget.ToggleWidget;

public final class WidgetUtils {

    private final static String ACTION_CHANGE = "Project_294_ACTION_CHANGE";
    private final static String KEY_ENABLE_STATE = "key_enable_state";

    public static RemoteViews fillViewsIfConnected(ProjectApplication application, int widgetId) {

        RemoteViews widgetView = new RemoteViews(application.getPackageName(), R.layout.widget);

        String name = application.getDbManager().getModelByType(GeneralModel.TYPE_ELEMENTARY_FAN).getName();
        widgetView.setTextViewText(R.id.widget_device_name, name);

        String statusText;
        if (application.getNetManager().isConnected()) {
            statusText = application.getString(R.string.connected);
        } else {
            statusText = application.getString(R.string.disconnected);
        }

        widgetView.setTextViewText(R.id.widget_device_state, statusText);

        Intent countIntent = new Intent(application, ToggleWidget.class);
        countIntent.setAction(ACTION_CHANGE);
        countIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent pIntent = PendingIntent.getBroadcast(application, widgetId, countIntent, 0);

        widgetView.setOnClickPendingIntent(R.id.widget_device_toggle_button, pIntent);

        return widgetView;
    }

    public static RemoteViews fillViewsIfDisconnected(ProjectApplication application, int widgetId) {

        RemoteViews widgetView = new RemoteViews(application.getPackageName(), R.layout.widget);

        String disconnectedString = application.getString(R.string.disconnected);
        widgetView.setTextViewText(R.id.widget_device_name, disconnectedString);

        String statusText = application.getString(R.string.connect_to_server);

        widgetView.setTextViewText(R.id.widget_device_state, statusText);

        Intent countIntent = new Intent(application, ToggleWidget.class);
        countIntent.setAction(ACTION_CHANGE);
        countIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        countIntent.putExtra(KEY_ENABLE_STATE, false);

        PendingIntent pIntent = PendingIntent.getBroadcast(application, widgetId, countIntent, 0);
        widgetView.setOnClickPendingIntent(R.id.widget_device_toggle_button, pIntent);

        return widgetView;
    }

    public static boolean isAcceptedAction(Intent intent) {
        String action = intent.getAction();
        Log.wtf("WU", "action = " + action);
        return ACTION_CHANGE.equalsIgnoreCase(action) || AppWidgetManager.ACTION_APPWIDGET_UPDATE.equalsIgnoreCase(action);
    }

    public static int extractWidgetIds(Intent intent) {
        Log.wtf("WU", "extractor = " + intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1));
        return intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
    }

    public static void extractAction(final ProjectApplication application, Intent intent) {

        if (!intent.getExtras().getBoolean(KEY_ENABLE_STATE)) {
            Log.wtf("WU", "try to connect to server");
            application.getNetManager().connectToServer();
        }
        application.getDeviceHelper().switchFanState();
    }

    public static void sendUpdateBroadcast(Context context, boolean isEnable) {
        Intent intent = new Intent(context, ToggleWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        ProjectApplication application = (ProjectApplication) context.getApplicationContext();

        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_ENABLE_STATE, isEnable);

        intent.putExtras(bundle);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, application.getSharedHelper().getWidgetId());

        context.sendBroadcast(intent);
    }
}

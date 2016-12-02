package com.kozhurov.project294.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.kozhurov.project294.core.ProjectApplication;
import com.kozhurov.project294.util.WidgetUtils;

public final class ToggleWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.wtf("Widget", "onUpdate");
        ProjectApplication application = (ProjectApplication) context.getApplicationContext();
        int widgetId = appWidgetIds[0];

        application.getSharedHelper().setWidgetId(widgetId);

        RemoteViews widgetView;
        if (application.getNetManager().isConnected()) {
            Log.wtf("Widget", "onUpdate-connected");
            widgetView = WidgetUtils.fillViewsIfConnected(application, widgetId);
        } else {
            Log.wtf("Widget", "onUpdate-disconnected");
            widgetView = WidgetUtils.fillViewsIfDisconnected(application, widgetId);
        }

        appWidgetManager.updateAppWidget(widgetId, widgetView);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


        if (!WidgetUtils.isAcceptedAction(intent) || intent.getExtras() == null) {
            return;
        }

        int widgetIds = WidgetUtils.extractWidgetIds(intent);

        if (widgetIds == AppWidgetManager.INVALID_APPWIDGET_ID) {
            Log.wtf("TW", "bad widget id");
            return;
        }


        ProjectApplication application = (ProjectApplication) context.getApplicationContext();
        WidgetUtils.extractAction(application, intent);

        Log.wtf("TW", "onProcess broadcast");
        onUpdate(context, AppWidgetManager.getInstance(context), new int[]{widgetIds});
    }
}

package com.ctanas.android.applaunch.widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ctanas.android.applaunch.HomeActivity;
import com.ctanas.android.applaunch.R;

public class AppQueryWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        int widgetsToUpdate = appWidgetIds.length;
        for ( int i =0; i < widgetsToUpdate; i++ ) {

            int widgetId = appWidgetIds[i];

            Intent startHomeActivity = new Intent(context, HomeActivity.class);
            PendingIntent homeActivityPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    startHomeActivity,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_app_search);
            widgetView.setOnClickPendingIntent(R.id.btn_search, homeActivityPendingIntent);
            widgetView.setOnClickPendingIntent(R.id.edt_query_text, homeActivityPendingIntent);

            appWidgetManager.updateAppWidget( widgetId, widgetView );

        }
    }
}

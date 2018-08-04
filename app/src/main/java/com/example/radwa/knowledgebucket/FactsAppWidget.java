package com.example.radwa.knowledgebucket;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import static com.example.radwa.knowledgebucket.MainActivity.Facts_key;
import static java.security.AccessController.getContext;

/**
 * Implementation of App Widget functionality.
 */
public class FactsAppWidget extends AppWidgetProvider {
    public static String YOUR_AWESOME_ACTION = "YourAwesomeAction";
    ShareDialog shareDialog;
    public static String WIDGET_BUTTON = "MY_PACKAGE_NAME.WIDGET_BUTTON";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String fact = SharedPreferencesMethods.loadSavedPreferencesString(context, Facts_key);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.facts_app_widget);
        views.setTextViewText(R.id.appwidget_text, fact);
        Intent shareIntent = new Intent(context, FacebookShare.class);
        PendingIntent sharePendingIntent = PendingIntent.getActivity(context, 0, shareIntent, 0);
        views.setOnClickPendingIntent(R.id.share, sharePendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    public Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}


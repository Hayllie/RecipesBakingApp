package com.example.udrecipesbakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.udrecipesbakingapp.Activities.RecipeDetailsActivity;
import com.example.udrecipesbakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        SharedPreferences sharedPreferences = context.getSharedPreferences("recipeData", Context.MODE_PRIVATE);


        CharSequence widgetText = sharedPreferences.getString("recipeName", "No Recipe Available");
        CharSequence widgetIngredientsText = sharedPreferences.getString("ingredientsText", "");

        // Construct the RemoteViews object
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);

        remoteViews.setOnClickPendingIntent(R.id.textView, pendingIntent);


        remoteViews.setTextViewText(R.id.recipe_name_text_view, widgetText);
        remoteViews.setTextViewText(R.id.ingredients_text_view, widgetIngredientsText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
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
}


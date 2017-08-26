package com.example.android.movies.helpers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.movies.R;
import com.example.android.movies.activities.DetailsActivity;
import com.example.android.movies.adapters.ImageAdapter;
import com.example.android.movies.data.DatabaseHelper;
import com.example.android.movies.fragments.PostersFragment;
import com.example.android.movies.sync.MoviesSyncAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static android.R.attr.name;
import static com.example.android.movies.fragments.PostersFragment.db;


/**
 * Created by Fatma on 25-Jul-17.
 */

public class WidgetProvider extends AppWidgetProvider{
    private DatabaseHelper databaseHelper;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        databaseHelper = new DatabaseHelper(context);

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget);

            MoviesSyncAdapter.syncImmediately(context);
            HashMap<String,String>[] movies = databaseHelper.getMovies();
            Collections.sort(Arrays.asList(movies), new Comparator<HashMap<String,String>>() {
                @Override
                public int compare(HashMap<String,String>a ,HashMap<String,String>b)
                {
                    return a.get("popularity").compareTo(b.get("popularity"));
                }
            });
            HashMap<String,String> most_popular_movie = movies[0];
            remoteViews.setTextViewText(R.id.widget_movie_name,
                    most_popular_movie.get("original_title"));
            remoteViews.setTextViewText(R.id.widget_movie_rating,
                    most_popular_movie.get("vote_average"));

            Intent onclick_intent = new Intent(context,DetailsActivity.class).
                    putExtra("widget_movie_name", most_popular_movie.get("original_title"));
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    onclick_intent,0);
            remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

}

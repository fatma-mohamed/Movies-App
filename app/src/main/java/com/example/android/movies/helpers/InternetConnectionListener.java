package com.example.android.movies.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by fatma on 12/3/2016.
 */

public class InternetConnectionListener extends BroadcastReceiver{
    private static final String LOG_TAG = InternetConnectionListener.class.getSimpleName();



    @Override
    public void onReceive(Context context, Intent intent )
    {
        if(!isOnline(context)){
            Log.v(LOG_TAG,"No network");
        }
        else
        {
            Log.v(LOG_TAG,"Active Network");
          //  context.startActivity(intent);
        }

    }

    //check if connected to internet
    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}

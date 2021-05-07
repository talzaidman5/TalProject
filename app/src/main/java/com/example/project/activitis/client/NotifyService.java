package com.example.project.activitis.client;
import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.app.PendingIntent ;
import android.app.Service ;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent ;
import android.os.Bundle;
import android.os.IBinder ;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.project.R;

    public class NotifyService extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //breakpoint here that doesn't get triggered
            Log.d("log","log");
        }
    }
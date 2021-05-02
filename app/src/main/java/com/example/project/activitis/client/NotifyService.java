package com.example.project.activitis.client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.example.project.R;

public class NotifyService extends BroadcastReceiver {
    private PendingIntent pendingIntent;
  /*  @Override
    public void onCreate() {
        Log.d("tal","goodddd");
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager  mNM =  (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this.getApplicationContext(),ActivityMyProfile.class);
        pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
        Notification manager = new Notification.Builder(this)
                .setSmallIcon(R.drawable. notification_template_icon_bg)
                .setContentTitle("Music player")
                .setContentIntent(pendingIntent)
                .setContentText("text")
                .setSound(sound)
                .addAction(0,"load",pendingIntent)
                .build();

        mNM.notify(1, manager);

    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("tal","goodddd");
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For exampleRECEIVE_BOOT_COMPLETED
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example

        wl.release();

    }
}
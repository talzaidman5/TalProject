package com.example.project.activitis.client;
import android.app.NotificationChannel ;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager ;
import android.app.PendingIntent ;
import android.app.Service ;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent ;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder ;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.project.R;
import com.example.project.data.AllUsers;
import com.example.project.data.User;
import com.example.project.utils.Constants;
import com.example.project.utils.MySheredP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotifyService extends BroadcastReceiver {
    private MySheredP msp;
    private User currentUser = new User();
    private AllUsers allUsers;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("FB");

        @Override
        public void onReceive(Context context, Intent intent) {
            msp = new MySheredP(context);

            getFromMSP();
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.logo);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit")
                    .setSmallIcon(R.drawable.bell)
                    .setContentTitle("תרומת דם")
                    .setContentText("עברו שלושה חודשים מהתרומה האחרונה שלך, נשמח לראותך בעמדות התרומה :)")
                    .setSmallIcon(R.drawable.logo)
                    .setLargeIcon(icon)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
                notificationCompat.notify(200,builder.build());
                currentUser.setCanDonateBlood(true);
            myRef.child("Users").child(currentUser.getID()).setValue(currentUser);


        }
    private AllUsers getFromMSP() {
        String data = msp.getString(Constants.KEY_MSP, "NA");
        String dataAll = msp.getString(Constants.KEY_MSP_ALL, "NA");
        currentUser = new User(data);
        allUsers = new AllUsers(dataAll);
        return allUsers;
    }


}
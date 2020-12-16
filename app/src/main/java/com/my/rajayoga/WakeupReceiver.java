package com.my.rajayoga;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;


import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WakeupReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
       Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String format = sdf.format(date);
        SharedPreferences storage = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        String string = storage.getString("last", "last");
        boolean notfiy = storage.getBoolean("notify",true);
      if(notfiy && (string.equals("last") || !string.equals(format))) {
            Intent openIntent = new Intent(context.getApplicationContext(), FullscreenActivity.class);
            openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),0,openIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder= new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_trailanga_swami) //set icon for notification
                .setContentTitle(context.getString(R.string.content_title)) //set title of notification
                .setContentText(context.getString(R.string.content_text))//this is notification message
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // makes auto cancel of notification

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                builder.setChannelId("YamNiamWakeup");
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel("YamNiamWakeup", "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                manager.createNotificationChannel(notificationChannel);
            }
           manager.notify(0, builder.build());
        }
    }
}
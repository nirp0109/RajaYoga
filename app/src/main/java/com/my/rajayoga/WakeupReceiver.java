package com.my.rajayoga;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class WakeupReceiver extends BroadcastReceiver {


    public static final String CHANNEL_ID = "YamNiamWakeup";

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("WakeupReceiver", "onReceive: " + intent.getAction());
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("WakeupReceiver", "in onReceive >CODE.S: " + alarmManager.canScheduleExactAlarms());
            if (!alarmManager.canScheduleExactAlarms()) {
                //ask user to allow exact alarm
                context.startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));
            }
        }
        // an Intent broadcast.
        SharedPreferences storage = context.getSharedPreferences("storage", Context.MODE_PRIVATE);

        boolean notify = storage.getBoolean("notify", true);
        String hour = storage.getString("hour", "7");
        Log.d("WakeupReceiver", "onReceive: notify: " + notify + " hour: " + hour);
        if (notify ) {
            //schedule again
//            FullscreenActivity.scheduleAlaramSpecficHourInEveryDay(context, Integer.parseInt(hour));
            //CREATE NOTIFICATION
            Intent openIntent = new Intent(context.getApplicationContext(), WelcomeActivity.class);
            openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_trailanga_swami)
                    .setContentTitle(context.getString(R.string.content_title))
                    .setContentText(context.getString(R.string.content_text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{0, 1000, 1000, 1000})
                    .setAutoCancel(true);

            Notification notification = builder.build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            Log.d("WakeupReceiver", "onReceive: notify: " + notify + " hour: " + hour + " notificationManager: " + notificationManager);
            notificationManager.notify(1, notification);
            Log.d("WakeupReceiver", "onReceive: after notify: " + notify + " hour: " + hour + " notificationManager: " + notificationManager);
        }
    }


}
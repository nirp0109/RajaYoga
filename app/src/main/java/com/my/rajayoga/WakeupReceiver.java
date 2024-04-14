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

import java.text.SimpleDateFormat;


public class WakeupReceiver extends BroadcastReceiver {


    public static final String CHANNEL_ID = "YamNiamWakeup";

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("WakeupReceiver", "onReceive: " + intent.getAction());
        //find if already got a notification for this day and hour
        //get the hour in the intent
        int notify_hour = intent.getIntExtra("hour", 0);
        //get current date and convert to string
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        //concatenate date and hour
        String key = date + "-" + notify_hour;
        //get the storage
        SharedPreferences storage = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        //get the value of the last notification
        boolean notified = storage.getString("LAST_NOTIFICATION", "").equalsIgnoreCase(key);
        //if already notified return
        if (notified) {
            return;
        } else {
            //set the value of the key to true
            storage.edit().putString("LAST_NOTIFICATION", key).apply();
        }


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("WakeupReceiver", "in onReceive >CODE.S: " + alarmManager.canScheduleExactAlarms());
            if (!alarmManager.canScheduleExactAlarms()) {
                //ask user to allow exact alarm
                context.startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));
            }
        }

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
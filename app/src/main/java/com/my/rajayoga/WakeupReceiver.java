package com.my.rajayoga;



import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WakeupReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(!alarmManager.canScheduleExactAlarms()) {
                //ask user to allow exact alarm
                context.startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));
            }
        }
        // an Intent broadcast.
       Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SharedPreferences storage = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        String string = storage.getString("last", "last");
        boolean notfiy = storage.getBoolean("notify",true);
        String hour = storage.getString("hour", "7");
        /* Setting the alarm here */
        Date lastDate = null;
        try {
            lastDate = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(notfiy && (string.equals("last") || lastDate!=null && date.after(lastDate))){
            updateTimeStamp(context, string);
            //schedule again
            WelcomeActivity.scheduleAlaramSpecficHourInEveryDay(context, Integer.parseInt(hour));
            Intent openIntent = new Intent(context.getApplicationContext(), WelcomeActivity.class);
            openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),0,openIntent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "YamNiamWakeup")
                    .setSmallIcon(R.drawable.ic_trailanga_swami)
                    .setContentTitle(context.getString(R.string.content_title))
                    .setContentText(context.getString(R.string.content_text))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{0, 1000, 1000, 1000})
                    .setAutoCancel(true);


            NotificationManagerCompat  notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(this.hashCode(), builder.build());
        }
    }

    private void updateTimeStamp(Context context, String string) {
        SharedPreferences storage;
        storage = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = storage.edit();
        if ("last".equalsIgnoreCase(string)) {
            edit.putBoolean("notify", true);
        }
        edit.putString("last", string);
        edit.commit();
    }
}
package com.my.rajayoga;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences storage = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
            String hour = storage.getString("hour", "7");
            /* Setting the alarm here */
            FullscreenActivity.scheduleAlaramSpecficHourInEveryDay(context, Integer.parseInt(hour));
  //      }
    }
}
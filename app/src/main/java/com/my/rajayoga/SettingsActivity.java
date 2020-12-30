package com.my.rajayoga;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        setTitle(getString(R.string.title_activity_settings));
    }

    public  static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            getPreferenceManager().setSharedPreferencesName("storage");
            getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(SettingsFragment.this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            try {
                switch (key) {
                    case "notify":
                        boolean notfiy = sharedPreferences.getBoolean(key,true);
                        if(notfiy) {
                            schedule();
                        } else {
                            unschedule();
                        }
                        break;
                    case "hour":
                        String hourStr = sharedPreferences.getString(key, "7");
                        int hour = Integer.parseInt(hourStr);
                        schedule(hour);
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Log.e(e.getClass().getName(),e.getMessage());
            }  catch (Throwable e) {
                e.printStackTrace();
                Log.e(e.getClass().getName(),e.getMessage());
            }finally {
            }
        }

        private void schedule() {

            SharedPreferences storage = getActivity().getSharedPreferences("storage", Context.MODE_PRIVATE);
            String hourStr = storage.getString("hour", "7");
            schedule(Integer.parseInt(hourStr));

        }

        private void unschedule() {
            Context context = getContext();
            if(context!=null) {
                Intent alarmIntent = new Intent(context.getApplicationContext(), WakeupReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);
            }
        }

        private void schedule(int hour) {
            FullscreenActivity.scheduleAlaramSpecficHourInEveryDay(getActivity(), hour);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
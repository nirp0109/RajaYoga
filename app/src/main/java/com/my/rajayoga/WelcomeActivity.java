package com.my.rajayoga;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private WebView mContentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        } else {
            setTheme(R.style.FullscreenTheme);
        }

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_welcome);
        mContentView = findViewById(R.id.welcome_content);
        WebSettings settings = mContentView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mContentView.addJavascriptInterface(new CallBackFormJS(this),"AndroidCallback");
        mContentView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("WebView", consoleMessage.message());
                return true;
            }
        });
        mContentView.setWebViewClient(new WebViewClient() {
                                          @Override
                                          public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                                              return false;
                                          }

                                          @Override
                                          public void onPageFinished(WebView view, String url) {
                                          }

                                          @Override
                                          public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                              super.onPageStarted(view, url, favicon);
                                          }


                                      }
        );
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String format = sdf.format(date);
        int day = Integer.parseInt(format.substring(0, 2));
        registerNotification();
        updateCurrentDateAsVisted(format);

        SharedPreferences storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
        boolean welcome = storage.getBoolean("welcome", true);

        // Create the NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("YamNiamWakeup", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        //remove notification if any
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);

        if(welcome) {
            ((WebView) mContentView).loadUrl("file:///android_asset/html/welcome.html?page=day" + day + ".html&wait=10000");
        }
        else {
            Intent intent = new Intent(this, FullscreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            startActivity(intent);
        }


    }

    private void registerNotification() {
        SharedPreferences storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
        boolean notify = storage.getBoolean("notify", true);
        //since register before update date see if created otherwise result is last
        if (notify) {
            Context context = this;
            int hour = Integer.parseInt(storage.getString("hour", "7"));
            FullscreenActivity.scheduleAlaramSpecficHourInEveryDay(context, hour);
        }
    }


    private void updateCurrentDateAsVisted(String format) {
        SharedPreferences storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
        String string = storage.getString("last", "last");
        SharedPreferences.Editor edit = storage.edit();
        if ("last".equalsIgnoreCase(string)) {// first time
            edit.putBoolean("notify", storage.getBoolean("notify", true));
            edit.putString("hour", storage.getString("hour", "7"));
            edit.putBoolean("welcome", storage.getBoolean("welcome", true));
            edit.commit();
        }

    }

    /**
     * Use for callback from JS t JAVA android
     */
    class CallBackFormJS {
        WelcomeActivity activity = null;
        public CallBackFormJS(WelcomeActivity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void showDailyInstruction() {
            Intent intent = new Intent(activity, FullscreenActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
               finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package com.my.rajayoga;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.palette.graphics.Palette;

import android.app.AlarmManager;
import android.app.PendingIntent;
//import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private WebView mContentView;
    private GestureDetectorCompat mDetector;
    private int day;
    private String urlStr = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        final ProgressDialog pd = ProgressDialog.show(FullscreenActivity.this, "", getString(R.string.loading_indicator),true);
        setContentView(R.layout.activity_fullscreen);
        mContentView = (WebView)findViewById(R.id.fullscreen_content);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
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
//                                                        if(FullscreenActivity.this!=null && !FullscreenActivity.this.isFinishing()) {
//                                                            urlStr = request.getUrl().toString();
//                                                            pd.show();
//                                                            view.loadUrl(request.getUrl().toString());
//                                                        }
                                                          return false;
                                                      }

                                                      @Override
                                                      public void onPageFinished(WebView view, String url) {
//                                                         if(pd!=null && pd.isShowing() && FullscreenActivity.this!=null && !FullscreenActivity.this.isFinishing()) {
//                                                             pd.dismiss();
//                                                         }
//                                                         super.onPageFinished(view, url);
                                                      }

                                                      @Override
                                                      public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                                          urlStr = url;
                                                          super.onPageStarted(view, url, favicon);
                                                      }


                                                  }
        );
        ((WebView) mContentView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //analyze gesture
                FullscreenActivity.this.mDetector.onTouchEvent(event);
                return FullscreenActivity.this.onTouchEvent(event);

            }
        });
        //((WebView)mContentView).setWebChromeClient(new WebChromeClient());
        //find the day number of today and set the right url

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String format = sdf.format(date);
        day = Integer.parseInt(format.substring(0, 2));
        registerNotification();
        updateCurrentDateAsVisted(format);

        ((WebView) mContentView).loadUrl("file:///android_asset/html/welcome.html?page=day" + day + ".html&wait=10000");
//        SharedPreferences storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
//        Toast toast = Toast.makeText(this, storage.getString("last", "l") + storage.getBoolean("notify", false) + storage.getString("signature", "s"), Toast.LENGTH_LONG);
//        toast.show();
    }

    private void registerNotification() {
        SharedPreferences storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
        boolean notify = storage.getBoolean("notify", true);
        //since register before update date see if created otherwise result is last
        if (notify) {
            Context context = this;
            int hour = Integer.parseInt(storage.getString("hour", "7"));
            scheduleAlaramSpecficHourInEveryDay(context, hour);
        }
    }

    public static void scheduleAlaramSpecficHourInEveryDay(Context context, int hour) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 60 * 24;
        /* Set the alarm to start at 7 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);

        Intent alarmIntent = new Intent(context.getApplicationContext(), WakeupReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /* Repeating on every day interval */
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                interval, pendingIntent);
    }

    private void updateCurrentDateAsVisted(String format) {
        SharedPreferences storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
        String string = storage.getString("last", "last");
        SharedPreferences.Editor edit = storage.edit();
        if ("last".equalsIgnoreCase(string)) {
            edit.putBoolean("notify", true);
            edit.putString("hour", "7");
        }
        edit.putString("last", format);
        edit.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_shutdown:
                finish();
                return true;
            case R.id.action_setting:
                openSettingActivity();
                return true;
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * open screen that let the user make the settings
     */
    private void openSettingActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /***
         * in case for back press go back if can otherwise finish app
         * in any other case deligate handle to the super
         **/
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (((WebView) mContentView).canGoBack()) {
                    ((WebView) mContentView).goBack();
                } else {
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    /**
     * Dealing with guesture like fling
     */
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            /**
             * we want to change page only when there is swap to the side:
             * velocity of x is more than of y and distance is at least quarter of screen
             * width
             **/
            if (urlStr != null && urlStr.indexOf("android_asset/html/day") == -1) {//external link don't change page on swap
                return true;
            }
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                float width;
                width = Math.abs(event2.getX() - event1.getX());
                int viewWidth = mContentView.getWidth();
                if (width > viewWidth / 4) {
                    //according to direction change day
                    if (event2.getX() - event1.getX() > 0) {
                        day = day + 1;
                    } else {
                        day = day - 1;
                    }
                    day = (31 + day) % 31;
                    if (day == 0) {
                        day = 31;
                    }
                    //load with new page
                    ((WebView) mContentView).loadUrl("file:///android_asset/html/day" + day + ".html");
                }
            }
            return true;
        }
    }

    public void createPaletteAsync(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                Palette.Swatch darkVibrantSwatch = p.getDarkVibrantSwatch();
                if(darkVibrantSwatch!=null) {
                    float[] rr = darkVibrantSwatch.getHsl();
                    mContentView.evaluateJavascript("javascript:updateHeaders("+rr[0]+","+ rr[1] +","+rr[2]+");",null);
                }
            }
        });
    }

    /**
     * Get Bitmap from resource. convert image to bitmap type
      * @param filePath
     * @return
     */
    public  Bitmap getBitmapFromAsset(String filePath) {
        AssetManager assetManager = getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            Log.e("getBitmapFromAsset",e.getLocalizedMessage());
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }

        return bitmap;
    }

    /**
     * Use for callback from JS t JAVA android
     */
    class CallBackFormJS {
        FullscreenActivity activity = null;
        public CallBackFormJS(FullscreenActivity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void getImageName(String name) {
            int index = name.lastIndexOf("/");
            String fileName = "html/App_Pic"+name.substring(index);
            Bitmap bitmapFromAsset = getBitmapFromAsset(fileName);
            createPaletteAsync(bitmapFromAsset);
        }
    }

}
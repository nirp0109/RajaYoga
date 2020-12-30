package com.my.rajayoga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        } else {
            setTheme(R.style.FullscreenTheme);
        }
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_about);
        WebView mContentView = (WebView)findViewById(R.id.about_content);
        ((WebView)mContentView).loadUrl("file:///android_asset/html/About.html");
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
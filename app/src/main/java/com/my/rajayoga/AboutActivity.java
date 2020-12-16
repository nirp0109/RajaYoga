package com.my.rajayoga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        WebView mContentView = (WebView)findViewById(R.id.about_content);
        ((WebView)mContentView).loadUrl("file:///android_asset/html/About.html");
    }
}
package com.my.rajayoga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        WebView mContentView = (WebView)findViewById(R.id.help_content);
        ((WebView)mContentView).loadUrl("file:///android_asset/html/help.html");
    }
}
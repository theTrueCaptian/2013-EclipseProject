package com.example;

import android.app.Activity;
import android.os.Bundle;

import com.tapfortap.TapForTap;
import com.tapfortap.AdView;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TapForTap.setDefaultAppId("<Your TapForTap App ID>");
        TapForTap.checkIn(this);

        setContentView(R.layout.main);
        AdView adView = (AdView) findViewById(R.id.ad_view);
        adView.loadAds();
    }

}

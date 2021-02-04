package com.android.walmart;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        //cfg.useGL20 = false;
        
       // initialize(new WalmartGame(), cfg);
        
        DisplayMetrics displayMetrics = new DisplayMetrics();
		displayMetrics = getResources().getDisplayMetrics();

		initialize(new WalmartGame(displayMetrics.widthPixels,
				displayMetrics.heightPixels), false);
    }
}
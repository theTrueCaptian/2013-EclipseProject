package com.movement;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
	
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
	
		  super.onCreate(savedInstanceState);
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
		  setContentView(new GameController(this));
	
	}
	
	@Override
	
	protected void onDestroy() {
		super.onDestroy();
	
	}
	
	@Override
	
	protected void onStop() {
		super.onStop();
	
	}

}

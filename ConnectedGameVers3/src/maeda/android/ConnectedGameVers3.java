package maeda.android;


import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;
import android.content.Context;
import android.view.View;

public class ConnectedGameVers3 extends Activity {
	ConnectedApp connected = new ConnectedApp(this);
	WakeLock wakeLock;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    	
    	
    	setContentView(R.layout.connectedlayout);
        
        connected.OnCreate((DataObject) getLastNonConfigurationInstance());
        
        
    }
    
    @Override
    public Object onRetainNonConfigurationInstance(){
    	return connected.onRetainNonConfigurationInstance();
    }

    
    
    public void onClickAction(View V)
    {
    	connected.onClickAction(V);
    }
    
    

}

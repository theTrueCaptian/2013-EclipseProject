package tracing.android;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class TracingVers2Activity extends Activity {
  
	View dview;                    //creating the reference
	/*private String arabic[] = { 
			"\u0627", "\u0628", "\u062A", "\u062B", "\u062C",
			"\u062D", "\u062E", "\u062F", "\u0630", "\u0631",
			"\u0632", "\u0633", "\u0634", "\u0635", "\u0636",
			"\u0637", "\u0638", "\u0639", "\u063A", "\u0641",
			"\u0642", "\u0643", "\u0644", "\u0645", "\u0646",
			"\u0648", "\u0647", "\uFE80", "\u064A"
		  };*/
	
	@Override
		    public void onCreate(Bundle savedInstanceState)
		   {
		        super.onCreate(savedInstanceState);
		        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		        
		        
		       
				
		        
		        this.setContentView(new DrawingView(this, this.findViewById(R.id.view1)));
		        
		        
		        
		        //dview=this.findViewById(R.id.view1); //creating the instance
		        
		       // dview.setBackgroundDrawable(new DrawingView(this));
		        
		    
		    }
}
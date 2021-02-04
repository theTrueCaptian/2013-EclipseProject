package maeda.android;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class TracingGameVers1Activity extends Activity {
	
	private String arabic[] = { 
			"\u0627", "\u0628", "\u062A", "\u062B", "\u062C",
			"\u062D", "\u062E", "\u062F", "\u0630", "\u0631",
			"\u0632", "\u0633", "\u0634", "\u0635", "\u0636",
			"\u0637", "\u0638", "\u0639", "\u063A", "\u0641",
			"\u0642", "\u0643", "\u0644", "\u0645", "\u0646",
			"\u0648", "\u0647", "\uFE80", "\u064A"
		  };
	DrawingView dview;                    //creating the reference
	@Override
		    public void onCreate(Bundle savedInstanceState)
		   {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.main);
		        Typeface.createFromAsset(this.getAssets(),"DejaVuSans.ttf");
		        TextView tx=(TextView)this.findViewById(R.id.view);
		        if(tx!=null){
		        tx.setTypeface(Typeface.createFromAsset(this.getAssets(),"DejaVuSans.ttf"));
		        tx.setTextSize(200);
				tx.setText(/*ArabicUtilities.reshape(""+arabic[0]+arabic[3]+arabic[4])*/arabic[28]);
		        }else{
		        	//tx.setText("Cant disply");
		        }
		        
		        //dview=new DrawingView(this); //creating the instance
		        //setContentView(dview);         //adding to the activity
		    }
}
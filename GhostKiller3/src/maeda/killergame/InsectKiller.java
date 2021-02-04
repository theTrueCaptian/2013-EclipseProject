package maeda.killergame;

import com.tapfortap.AdView;
import com.tapfortap.Interstitial;
import com.tapfortap.TapForTap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class InsectKiller extends Activity implements OnTouchListener{
    /** Called when the activity is first created. */
	
	public enum States{ LOADING, MENU, HELP, HIGHSCORE, GAME}
	States currState = States.LOADING;
	States prevState;
	Context context;
	boolean gameloop = true;
	WakeLock wakeLock;
	MenuScreen menu;
	LoadingScreen load;
	HelpScreen help;
	HighscoreScreen highscore;
	GameController game;
	long time1 = System.currentTimeMillis();
	LinearLayout myLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
        
        //Ad network init
        TapForTap.initialize(this, "6317ef63b6618b99c272b1758b3efe46");
        Interstitial.prepare(this);
               
         load = new LoadingScreen(this);
        setContentView(load);
        System.out.println("all set");
        
        try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        prevState=States.LOADING;
        currState=States.MENU;
        menu = new MenuScreen(this);
        setContentView(menu);
        menu.setOnTouchListener(this);

        
    }
    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        if(this.currState==States.GAME){
        	game.resume();
        }
        
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        if(this.currState==States.GAME){
        	game.pause();
        }
        
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    }
  
    protected void onStop() {
    	super.onStop();
    }


	@Override
	public boolean onTouch(View v, MotionEvent event) {
	
		if(this.currState==States.MENU){
			//change screens
			States k = menu.handleTouches(event);
			long now = System.currentTimeMillis();
			if(k!=null && now-time1>1000)	{
				time1 = System.currentTimeMillis();
				currState=k;
				changeStateTo(currState);
			}
		}else if(this.currState==States.HELP){
			States k = help.handleTouches(event);
			long now = System.currentTimeMillis();
			if(k!=null && now-time1>1000)	{
				time1 = System.currentTimeMillis();
				currState=k;
				changeStateTo(currState);
			}
		}else if(this.currState==States.HIGHSCORE){
			States k = highscore.handleTouches(event);
			long now = System.currentTimeMillis();
			if(k!=null && now-time1>1000)	{
				time1 = System.currentTimeMillis();
				currState=k;
				changeStateTo(currState);
			}
		}else if(this.currState==States.GAME){
			States k = game.handleTouches(event);
			if(k!=null)	{
				currState=k;
				changeStateTo(currState);
			}
		}
		return true;
	}
	
	public void changeStateTo(States instate){
		if(instate==States.HELP){
			help = new HelpScreen(this);
			setContentView(help);
	        help.setOnTouchListener(this);
	        time1 = System.currentTimeMillis();
		}else if(instate==States.MENU){
			menu = new MenuScreen(this);
			setContentView(menu);
	        menu.setOnTouchListener(this);
	        time1 = System.currentTimeMillis();
		}else if(instate==States.HIGHSCORE){
			highscore = new HighscoreScreen(this);
			setContentView(highscore);
			System.out.println("highscorescreen created");
			
			/*myLayout = new LinearLayout(this);
			myLayout.setOrientation(LinearLayout.VERTICAL);
			System.out.println("new layout created..");
			
			AdView adView = new AdView(this);
			// Optionally specify layout params.
			DisplayMetrics metrics = getResources().getDisplayMetrics();
			int width = metrics.widthPixels;
			int height = (int)(50 * (width / 320.0));
			LinearLayout.LayoutParams myLayoutParams = new LinearLayout.LayoutParams(width, height);
			adView.setLayoutParams(myLayoutParams);
			// Add the AdView to your layout.
			myLayout.addView(adView);
			try{
		        // Substitute your real app ID here
		        TapForTap.setDefaultAppId("cursedhome");
		        TapForTap.checkIn(this);
		        System.out.println("checked in");
		        //setContentView(R.layout.main);
		        setContentView(myLayout);
	        }catch(RuntimeException ex){
	        	System.out.println("aww tap tap thing didnt work out");
	        	setContentView(highscore);
	        }			
			
			*/
	        highscore.setOnTouchListener(this);
	        time1 = System.currentTimeMillis();
		}else if(instate==States.GAME){
			game = new GameController(this);
			setContentView(game);
	        game.setOnTouchListener(this);
		}
	}

	
}
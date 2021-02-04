package maeda.killergame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
        
        //setContentView(R.layout.main);
        //System.out.println("WORKS!!!!");
        //Game game = new Game(this);
        
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
		/*try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
	        highscore.setOnTouchListener(this);
	        time1 = System.currentTimeMillis();
		}else if(instate==States.GAME){
			game = new GameController(this);
			setContentView(game);
	        game.setOnTouchListener(this);
		}
	}

	
}